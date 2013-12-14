
package battleship.model;

import battleship.*;
import battleship.controller.*;
import java.util.ArrayList;

import java.util.logging.Logger;

public class AIPlayer extends Player implements Runnable
{
  private final static Logger LOGGER = Logger.getGlobal();
  private int xShoot, yShoot; //xHitFirst, yHitFirst;
  private boolean hasHit;
  private Ship.Direction actualDirection;
  private boolean sign;
  private Thread thread;

  public AIPlayer( )
  {
    LOGGER.info( this + ":AIPlayer:Constructor" );
  }

  public void init( )
  {
    LOGGER.info( this + ":AIPlayer:init" );
    thread = new Thread( this );
    thread.start( );
  }

  public void run( )
  {
    LOGGER.info( this + ":AIPlayer:run" );
    engine.getGrid( this );
    xShoot = 0;
    yShoot = 0;
    sign = false;

    do_placeShip( );
    try{
    engine.playerReady( this );
    }
    catch( Exception ex)
    {
        ex.printStackTrace();
    }
  }

  private void do_placeShip( )
  {
    LOGGER.info( this + ":AIPlayer:do_placeShip" );
      LOGGER.info( "AI: placeShip( )" );
      ArrayList<Ship> ships = engine.getShips( );
      for( Ship s : ships )
      {
          int x = getRandomValue( );
          int y = getRandomValue( );
        if( engine.placeShip( this, s, x, y))
        {
            LOGGER.info( "ship placed at [" + x + "," + y + "]: "+ s );
        }
        else
        {
            LOGGER.info( "AI: could not place ship at [" + x + "," + y + "]: " + s );
        }
      }
  }

  public void yourTurn( )
  {
    LOGGER.info( this + ":AIPlayer:yourTurn" );
    do_shoot( );
  }

  public void changingPlayer( )
  {
    LOGGER.info( this + ":AIPlayer:chaningPlayer" );
  }

  private void do_shoot( )
  {
    LOGGER.info( this + ":AIPlayer:do_shoot" );
      if (hasHit == false)
      {
          actualDirection = getRandomDirection();
          xShoot = getRandomValue();
          yShoot = getRandomValue();
      }
      else
      {
          hasHit = false;

          if (sign = false)
          {
              sign = true;
          }
          else
          {
              sign = false;
          }
      }

//      if(xHitFirst != 99)
//      {
//
//      }
//
//      if(xHitFirst != 99)
//      {
//
//      }

      while (engine.shoot(this, xShoot, yShoot))
      {
          if (ShipDestroyed(xShoot, yShoot))
          {
              hasHit = false;
              actualDirection = getRandomDirection();
              xShoot = getRandomValue();
              yShoot = getRandomValue();
          }
          else
          {
              hasHit = true;
          }
//          xHitFirst = xShoot;
//          yHitFirst = yShoot;


          switch(actualDirection){
              case HORIZONTAL:
                  if (sign == true)
                  {
                      xShoot++;
                  }
                  else
                  {
                      xShoot--;
                  }

                  break;

              case VERTICAL:
                  if (sign == true)
                  {
                      yShoot++;
                  }
                  else
                  {
                      yShoot--;
                  }
                  break;
          }
      }
  }

  public void do_update( )
  {
    LOGGER.info( this + ":AIPlayer:do_update" );
  }

  public void youLost( )
  {
    LOGGER.info( this + ":AIPlayer:youLost" );
  }

  public void youWon( )
  {
    LOGGER.info( this + ":AIPlayer:youWon" );
  }

  private Ship.Direction getRandomDirection()
  {
      int i = (int)(Math.random()*2);
      switch (i){
          case 0:
              return Ship.Direction.VERTICAL;
          default:
              return Ship.Direction.HORIZONTAL;
      }
  }

  private int getRandomValue()
  {
      return (int)(Math.random()*8); // FIXME: use static member for field size
  }

//  Könnte in die Klasse Engine kopiert und mit der Referenz des Spielers erweitert werden.
//  Dann könnte man mit dieser Methode überprüfen, ob bei einem Spieler auf gewissen Koordinaten ein Schiff steht,
//  welches zerstört wurde.
  private boolean ShipDestroyed(/*Player player,*/ int x, int y)
  {
    try
     {
        int hits = 0;
        Point pnt = engine.getGridOpponend( this ).getPoint(x, y);
  //     Point pnt =  player.getGrid().getPoint(x, y);
        Ship ship = pnt.getShip();

        int i;
        Point[][] arr = engine.getGridOpponend(this ).getPointArray();
  //      Point[][] arr = player.getGrid().getPointArray();
        for( i = 0; i < arr.length; i++ )
        {
          for( Point p : arr[i] )
          {
            if( p.getType( ) == Point.Type.SHIP || ship == p.getShip() || p.isAttacked())
            {
                hits++;
            }
          }
        }

        if (hits == ship.getSize())
        {
            return true;
        }
        else
        {
          return false;
        }
    }
    catch (Exception e) {
        return false;
    }
  }

  public void startingGame() {
    LOGGER.info( this + ":AIPlayer:startingGame" );
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}

