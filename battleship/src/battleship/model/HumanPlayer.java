
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

  @Override
  public void yourTurn( )
  {
    view.yourTurn( );
  }

    @Override
    public void startingGame() {
        view.startingGame( );
    }
}
