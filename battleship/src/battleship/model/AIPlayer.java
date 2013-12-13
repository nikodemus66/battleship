
package battleship.model;

import battleship.*;
import battleship.controller.*;
//import battleship.view.*;

public class AIPlayer extends Player
{
  private Engine engine;
  private int xShoot, yShoot; //xHitFirst, yHitFirst;
  private boolean hasHit;
  private Ship.Direction actualDirection;
  private boolean sign;
  
  public AIPlayer( )
  {
//      xHitFirst = 99;
//      yHitFirst = 99;
      xShoot = 0;
      yShoot = 0;
      sign = false;
  }

  public void do_setup( Engine engine )
  {
    this.engine = engine;
  }

  public void do_start( )
  {
  }

  public void do_placeShip( )
  {
        System.out.println( "AI: placeShip( )" );
    
        if (this.shipCount == 0)
        {
//          1 Schlachtschiff 5 Felder
            while (engine.placeShip( new Ship( "Battleship", 5, getRandomDirection()), getRandomValue(), getRandomValue()) == false)
            {

            }

//          2 Kreuzer 4 Felder
            for(int i=0; i<2; i++)
            {
                while (engine.placeShip( new Ship( "Cruiser", 4, getRandomDirection()), getRandomValue(), getRandomValue()) == false)
                {

                }
            }

//          3 Zerstörer 3 Felder
            for(int i=0; i<3; i++)
            {
                while (engine.placeShip( new Ship( "Destroyer", 3, getRandomDirection()), getRandomValue(), getRandomValue()) == false)
                {

                }            
            }

//          4 U-Boote 2 Felder
            for(int i=0; i<4; i++)
            {
                while (engine.placeShip( new Ship( "Submarine", 2, getRandomDirection()), getRandomValue(), getRandomValue()) == false)
                {

                }            
            }            
        }
  }

  public void changingPlayer( )
  {
  }

  public void do_shoot( )
  {      
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
      
      while (engine.shoot(xShoot, yShoot))
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
  }

  public void youLost( )
  {
  }

  public void youWon( )
  {
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
      return (int)(Math.random()*9);
  }
  
//  Könnte in die Klasse Engine kopiert und mit der Referenz des Spielers erweitert werden.
//  Dann könnte man mit dieser Methode überprüfen, ob bei einem Spieler auf gewissen Koordinaten ein Schiff steht,
//  welches zerstört wurde.
  private boolean ShipDestroyed(/*Player player,*/ int x, int y)
  {
    try
     {
        int hits = 0;
        Point pnt = engine.getGridOpponend().getPoint(x, y);
  //     Point pnt =  player.getGrid().getPoint(x, y);
        Ship ship = pnt.getShip();

        int i;
        Point[][] arr = engine.getGridOpponend().getPointArray();
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
}

