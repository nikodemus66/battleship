
package battleship.model;

import battleship.*;
import battleship.controller.*;
//import battleship.view.*;

public class AIPlayer extends Player
{
  private Engine engine;
  public AIPlayer( )
  {
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
    
        if (this.shipCount <= Engine.MAX_SHIPS)
        {
//          1 Schlachtschiff 5 Felder
            while (engine.placeShip( new Ship( "Battleship", 5, getDirection()), (int)(Math.random()*9), (int)(Math.random()*9)) == false)
            {

            }

//          2 Kreuzer 4 Felder
            for(int i=0; i<2; i++)
            {
                while (engine.placeShip( new Ship( "Cruiser", 4, getDirection()), (int)(Math.random()*9), (int)(Math.random()*9) ) == false)
                {

                }
            }

//          3 Zerstörer 3 Felder
            for(int i=0; i<3; i++)
            {
                while (engine.placeShip( new Ship( "Destroyer", 3, getDirection()), (int)(Math.random()*9), (int)(Math.random()*9) ) == false)
                {

                }            
            }

//          4 U-Boote 2 Felder
            for(int i=0; i<4; i++)
            {
                while (engine.placeShip( new Ship( "Submarine", 2, getDirection()), (int)(Math.random()*9), (int)(Math.random()*9) ) == false)
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
  
  private Ship.Direction getDirection()
  {
      int i = (int)(Math.random()*2);
      switch (i){
          case 0:
              return Ship.Direction.VERTICAL;              
          default:
              return Ship.Direction.HORIZONTAL;
      }
  }
}
