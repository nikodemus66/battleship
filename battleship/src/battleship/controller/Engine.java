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
  public enum PlayerType { HUMAN, AI, NETWORK }

  private Player player; // active player
  private Player opponend;

  private static final int MAX_SHIPS = 10;
  private int shipCount;

  public Engine( Player one )
  {
    player = one;
  }

  public void setOpponend( PlayerType type )
  {
    opponend = createPlayer( type );
  }

  private Player createPlayer( PlayerType type )
  {
    Player p = null;
    switch( type )
    {
      case AI:
        p = new AIPlayer( );
        break;
      case NETWORK:
        p = new NetworkPlayer( );
        break;
    }
    return p;
  }

  private void changePlayer( )
  {
    Player tmp = player;
    player = opponend;
    opponend = tmp;
  }

  public void start() throws Exception
  {
    if( player == null && opponend == null )
    {
      throw new Exception( "Engine: Players have to be set before start" );
    }

    // TODO: ask user if he wants to play on network
    player.do_setup( this );
    opponend.do_setup( this );

    player.do_start( );
    opponend.do_start( );
    startGame( );
  }

  private void startGame()
  {
    // TODO: if all players are ready we can start
    player.do_placeShip( ); // TODO: aslong as the player has ships left, we need to call this function
    changePlayer( );
    player.do_placeShip( ); // TODO: aslong as the player has ships left, we need to call this function

    while( ! gameover( ))
    {
      player.do_shoot( ); // TODO: check if shot
      changePlayer( );
    }
  }

  private boolean gameover( )
  {
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

    if( attacked ) // player lost
    {
      player.youLost( );
      opponend.youWon( );
      return true;
    }

    attacked = true;
    arr = opponend.getGrid( ).getPointArray( );
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

    if( attacked ) // player lost
    {
      player.youWon( );
      opponend.youLost( );
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

  public boolean playerReady()
  {
    if (shipCount < MAX_SHIPS)
    {
      return false;
    }
    else
    {
      return true;
    }
  }

  public boolean shoot(int x, int y)
  {
    // TODO: What happens if shoots at same location as before
    opponend.getGrid().getPoint(x, y).shot();
    if (opponend.getGrid().getPoint(x, y).getType() == Point.Type.SHIP)
    {
      return true;
    }
    return false;
  }

  public boolean placeShip( Ship ship, int x, int y )
  {
    //if( shipCount < MAX_SHIPS)
      //return false;

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
    }

    shipCount++;
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
