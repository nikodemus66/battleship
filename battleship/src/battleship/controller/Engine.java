/*
 * Diese Klasse ist für die Spiellogik verantwortlich
 *
 */
package battleship;

/**
 *
 * @author nikodemus
 */
public class Engine {

  private View view;
  private Grid board; // Model

  public Engine( View v, Grid m )
  {
    this.view = v;
    this.board = m;
    System.out.println( "Engine: initialized" );
  }

  public void start( )
  {
    // TODO: ask user if he wants to play on network
    view.start( this );
    startGame( );
  }

  private void startGame()
  {
    // TODO: if all players are ready we can start
    System.out.println( "Engine: game started" );
    view.do_placeShip( ); // TODO: aslong as the player has ships left, we need to call this function
    // TODO: opponent's turn

    view.do_shoot( ); // TODO: check if shot
  }

  public Grid getGrid( )
  {
    return board;
  }

  public boolean playerReady()
  {
    // TODO: wait for opponent to come ready
    return false;
  }

  public boolean shoot( int x, int y )
  {
    // TODO: check if hit and update board
    Point p = board.getPoint( x, y );
    p.shot( );
    view.update( );
    return false;
  }

  public boolean placeShip( Ship ship, int x, int y )
  {
      if (ship.getDirection() == Ship.Direction.HORIZONTAL)
      {
          for (int i=0; i<ship.getSize(); i++)
          {
              if (board.getPoint(i, y).getType() == Point.Type.SHIP)
              {
                  return false;
              }
          }
      }
      
      else if (ship.getDirection() == Ship.Direction.VERTIVAL)
      {
          for (int i=0; i<ship.getSize(); i++)
          {
              if (board.getPoint(x, i).getType() == Point.Type.SHIP)
              {
                  return false;
              }
          }
      }
    // TODO: check if ship can be placed
    //Point p = board.getPoint( x, y );
    //p.setType( Point.Type.SHIP );
    return true;
  }
}
