
package battleship.model;

import battleship.controller.*;
import battleship.view.*;

public class HumanPlayer extends Player
{
  private View view;

  public HumanPlayer( View view )
  {
    this.view = view;
  }

  public void do_setup( Engine engine )
  {
    view.do_setup( engine );
  }

  public void do_start( )
  {
    view.do_start( );
  }

  public void do_placeShip( )
  {
    view.do_placeShip( );
  }

  public void do_shoot( )
  {
    view.do_shoot( );
  }

  public void changingPlayer( )
  {
    view.changingPlayer( );
  }

  public void do_update( )
  {
    view.do_update( );
  }

  public void youLost( )
  {
    view.youLost( );
  }

  public void youWon( )
  {
    view.youWon( );
  }
}
