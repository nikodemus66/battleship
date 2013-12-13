/*
 * Diese Klasse ist für die Spiellogik verantwortlich
 *
 */
package battleship.controller;

import battleship.model.*;
import java.util.ArrayList;

/**
 *
 * @author nikodemus
 */
public class Engine
{
  public static final int MAX_SHIPS = 2;
  public enum PlayerType { HUMAN, AI, NETWORK }

  private Player player; // active player
  private Player opponend;

  public Engine( )
  {
  }

  public void setPlayer( Player one )
  {
    player = one;
  }

  public boolean setOpponendAI( )
  {
    opponend = new AIPlayer( );
    return true;
  }

  // server
  public boolean setOpponendNetwork( )
  {
    try {
      opponend = new NetworkPlayer( );
    } catch ( Exception e ) {}
    return true;
  }

  // client
  public boolean setOpponendNetwork( String ip )
  {
    try {
      opponend = new NetworkPlayer( ip );
    } catch ( Exception e ) {}
    return true;
  }

  public synchronized void playerReady( int playerId ) throws Exception
  {
    Player p = getPlayer( playerId );
    if( p.getShipCount( ) < MAX_SHIPS )
    {
      throw new Exception( "Not all ships has been placed" );
    }

    p.setReady( );
    notifyAll( );

    try{
      System.out.println( "Waiting for other player to get ready" );
      waitForOtherPlayer( );
    }
    catch( InterruptedException e )
    {
      throw new Exception( "Error: waitForotherPlayer was interrupted: " + e );
    }

    if( player.isReady( ) ||  opponend.isReady( ))
      startGame( );
  }

  private void waitForOtherPlayer( ) throws InterruptedException
  {
    while( ! player.isReady( ) ||  ! opponend.isReady( ))
    {
      System.out.println( "wait for notify" );
      wait( );
      System.out.println( "got Notified" );
    }
  }

  private void startGame()
  {
    player.startingGame( );
    opponend.startingGame( );
    changePlayer( );
  }

  public boolean shoot(int playerId, int x, int y)
  {
    Player p = getPlayer( playerId );
    if( ! p.shouldShoot( ))
    {
      return false; // the player can not shoot if its not his turn
    }

    // TODO: What happens if shoots at same location as before
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
    Player tmp = player;
    player = opponend;
    opponend = tmp;
    player.yourTurn( );
  }


  private Player getPlayer( int playerId )
  {
    Player p = null;
    switch( playerId )
    {
      case 0:
        p = player;
        break;
      case 1:
        p = opponend;
        break;
    }
    return p;
  }

  private boolean isGameover( )
  {
    boolean attacked = true;
    int i;
    Point[][] arr = opponend.getGrid( ).getPointArray( );
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

  public Grid getGrid()
  {
    // TODO: return copy
    return player.getGrid();
  }

  public Grid getGridOpponend()
  {
    // TODO: return copy
    return opponend.getGrid();
  }

  public ArrayList<Ship> getShips( )
  {
    // return copy
    return new ArrayList( player.getShips( ));
  }


  public boolean placeShip( Ship ship, int x, int y )
  {
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
        return false;
      }

      if( p.getType() == Point.Type.SHIP )
      {
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
    player.getGrid( ).clear( );
    opponend.getGrid( ).clear( );
    startGame( );
  }

}
