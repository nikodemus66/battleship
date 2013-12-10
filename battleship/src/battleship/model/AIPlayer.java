
package battleship.model;

import battleship.controller.*;
import battleship.view.*;

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
    engine.placeShip( new Ship( "Killership", 5), 5, 5 );
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
}
