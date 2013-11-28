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
    startGame( );
    view.start( this );
  }

  private void startGame()
  {
    // TODO: if all players are ready we can start
    System.out.println( "Engine: game started" );
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
    // TODO: check if ship can be placed
    return true;
  }
}
