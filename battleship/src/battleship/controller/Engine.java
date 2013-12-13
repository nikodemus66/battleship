/*
 * Diese Klasse ist für die Spiellogik verantwortlich
 *
 */
package battleship.controller;

import battleship.model.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author nikodemus
 */
public class Engine
{
  private final static Logger LOGGER = Logger.getGlobal();
  //public final static enum EngineState { SELECTING_OPPONEND, PREPARING_GRID, PLAY, FINISHED }

  //private EngineState state = EngineState.SELECTING_OPPONEND;

  public static final int MAX_SHIPS = 2;
  private static final ArrayList<Ship> ships = new ArrayList<Ship>() {{ // TODO: use array
    add(new Ship( "Killer", 2));
    add(new Ship( "Terminator" , 4));
  }};

  private Player[] players = new Player[2];
  private int activePlayer = 0;

  public Engine( )
  {
    LOGGER.info( this + ":Engine:Constructor" );
  }

  public void setPlayer( Player one )
  {
    LOGGER.info( this + ":Engine:setPlayer" );
    players[0] = one;
    one.init( this );
  }

  public boolean setOpponendAI( )
  {
    LOGGER.info( this + ":Engine:setOponendAI" );
    players[1] = new AIPlayer( );
    players[1].init( this );
    return true;
  }

  // server
  public boolean setOpponendNetworkServer( )
  {
    LOGGER.info( this + ":Engine:setOponendNetworkServer" );
    try {
      players[1] = new NetworkPlayerServer( );
      players[1].init( this );
    } catch ( Exception e ) {}
    return true;
  }

  // client
  public boolean setOpponendNetworkClient( String ip )
  {
    LOGGER.info( this + ":Engine:setOponendNetworkClient" );
    try {
      players[1] = new NetworkPlayerClient( ip );
      players[1].init( this );
    } catch ( Exception e ) {}
    return true;
  }

  public synchronized void playerReady( Player player ) throws Exception // waits until game starts
  {
    LOGGER.info( this + ":Engine:playerReady: " + player );
    if( player.getShipCount( ) < MAX_SHIPS )
    {
      throw new Exception( "Not all ships has been placed" );
    }

    if( players[0] == null )
      LOGGER.info( "Engine: XXXXXXXXXXX player null: 0" );

    if( players[1] == null )
      LOGGER.info( "Engine: XXXXXXXXXXX player null: 0" );


    player.setReady( );
    if( players[0].isReady( ) &&  players[1].isReady( ))
    {
      LOGGER.info( "Engine: all players are ready" );
      notifyAll( );
      startGame( );
    }
    else
    {
      try{
        LOGGER.info( "Waiting for other player to get ready" );
        waitForOtherPlayer( );
      }
      catch( InterruptedException e )
      {
        throw new Exception( "Error: waitForotherPlayer was interrupted: " + e );
      }
    }
  }

  private void waitForOtherPlayer( ) throws InterruptedException
  {
    while( ! players[0].isReady( ) ||  ! players[1].isReady( ))
    {
      LOGGER.info( "wait for notify" );
      wait( );
      LOGGER.info( "got Notified" );
    }
  }

  private void startGame()
  {
    LOGGER.info( this + ":Engine:startGame" );
    players[0].startingGame( );
    players[1].startingGame( );
    activePlayer = 0;
    players[activePlayer].yourTurn( );
  }

  private Player getOpponend( )
  {
    LOGGER.info( this + ":Engine:getOpponend" );
    if( activePlayer == 0 )
      return players[1];
    else
      return players[0];
  }

  public boolean shoot( Player player, int x, int y)
  {
    LOGGER.info( this + ":Engine:shoot" );
    if( player != players[activePlayer] )
    {
      LOGGER.info( "Engine:shoot: error cannot shoot, its up to player: " + activePlayer );
      return false; // the player can not shoot if its not his turn
    }

    // TODO: What happens if shoots at same location as before
    Player opponend = getOpponend( );
    opponend.getGrid().getPoint(x, y).shot();

    player.do_update( );
    opponend.do_update( );

    // check if hit
    // TODO: check if ship sank
    boolean hit = false;
    try {
      if (opponend.getGrid().getPoint(x, y).getType() == Point.Type.SHIP)
      {
        Ship s = opponend.getGrid().getPoint(x, y).getShip();
        //                shipDestroyed(s, x, y);
        hit = true;
      }
      hit = false;
    }
    catch (Exception e) { }

    if( isGameover( ))
    {
      player.youWon( );
      opponend.youLost( );
      return true;
    }

    if( ! hit )
      changePlayer( );

    return false;
  }

  private void changePlayer( )
  {
    LOGGER.info( this + ":Engine:changePlayer" );
    if( activePlayer == 0 )
      activePlayer = 1;
    else
      activePlayer = 0;
    players[activePlayer].yourTurn( );
  }

  public boolean isMyTurn( Player p )
  {
    LOGGER.info( this + ":Engine:isMyTurn" );
    return players[activePlayer] == p;
  }

  public boolean isGameover( ) // TODO: check if over in Grid
  {
    LOGGER.info( this + ":Engine:isGameover" );
    Player player = null;
    if ( activePlayer == 0 )
      player = players[1];
    else
      player = players[0];

    boolean attacked = true;
    int i;
    Point[][] arr = player.getGrid( ).getPointArray( );
    for( i = 0; i < arr.length; i++ )
    {
      for( Point p : arr[i] )
      {
        if( p.getType( ) == Point.Type.SHIP )
        {
          attacked &= p.isAttacked( );
        }
      }
    }

    if( attacked )
    {
      return true;
    }
    return false;
  }

  public Grid getGrid( Player player )
  {
    LOGGER.info( this + ":Engine:getGrid" );
    // TODO: return copy
    if( players[0] == player)
      return players[0].getGrid( );
    else
      return players[1].getGrid( );
  }

  public Grid getGridOpponend( Player player )
  {
    LOGGER.info( this + ":Engine:getGridOpponend" );
    // TODO: return copy

    if( players[0] == player)
      return players[1].getGrid( );
    else
      return players[0].getGrid( );
  }

  public ArrayList<Ship> getShips( )
  {
    LOGGER.info( this + ":Engine:getShips" );
    // return copy
    return new ArrayList<Ship>( ships );
  }


  public boolean placeShip( Player player, Ship ship, int x, int y ) // TODO: overgive shipId not whole ship
  {
    LOGGER.info( this + ":Engine:placeShip" );
    Point points[] = new Point[ship.getSize()];

    for( int i=0; i < ship.getSize( ); i++ )
    {
      Point p = null;

      try {
        switch( ship.getDirection( ))
        {
          case HORIZONTAL:
            p = player.getGrid( ).getPoint( x+i, y );
            break;
          case VERTICAL:
            p = player.getGrid( ).getPoint( x, y+i );
            break;
        }
      } catch (Exception e) {
        LOGGER.info( "Eninge: error ship cannot be placed: " + e );
        return false;
      }

      if( p.getType() == Point.Type.SHIP )
      {
        LOGGER.info( "Eninge: error ship cannot be placed: it collides with another ship" );
        return false; // cannot place ship because of other ship
      }
      points[i] = p;
    }

    for (Point p : points)
    {
      p.setType( Point.Type.SHIP );
      p.setShip(ship);
    }

    player.incrementShipCount();
    player.do_update();
    return true;
  }

  public void restart( )
  {
    LOGGER.info( this + ":Engine:restart" );
    players[0].getGrid( ).clear( );
    players[1].getGrid( ).clear( );
    startGame( );
  }

}
