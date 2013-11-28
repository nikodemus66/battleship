/*
 *
 *
 *
 */

package battleship;
import java.io.IOException;

/**
 *
 * @author txschmid
 */
public class CommandLineView implements View
{
  private Engine engine;

  public CommandLineView( )
  {
    System.out.println( "CommandLineView: initialized" );
  }

  public void start( Engine engine )
  {
    this.engine = engine;
    System.out.println( "CommandLineView started" );
    drawBoard( );

    // place ships
    System.out.println( "Please place you ship ..." );
    byte[] tmp = new byte[255];
    try {
      System.in.read( tmp );
    } catch(IOException e) {
      System.out.println( "CommandLineView:start( ): reading failded" );
    }
    if( engine.placeShip( new Ship("Ship1", 2 ), 0, 5 ))
    {
      System.out.println( "CommandLineView: Ship placed!" );
    }
    else
    {
      System.out.println( "CommandLineView: Ship could not been placed!" );
    }

    // ready -> startGame
    engine.playerReady( ); // returns when opponent is ready
    engine.shoot( 5, 5 );
    drawBoard( );
  }

  public void update( )
  {
  }

  private void drawBoard( )
  {
    Grid board = engine.getGrid( );
    System.out.println( board );
    Point[][] points = board.getPointArray( );

    System.out.print( "   " );
    for( int x = 0; x < points[0].length; x++ )
      System.out.print( x + " " );
    System.out.print( "\n   " );
    for( int x = 0; x < points[0].length; x++ )
      System.out.print( "- " );
    System.out.print( "\n" );

    for( int y = 0; y < points.length; y++ )
    {
      System.out.print( y + "|" );
      for( int x = 0; x < points[y].length; x++ )
      {
        switch( points[x][y].getType( ))
        {
          case WATER:
            if( points[x][y].isAttacked( ))
              System.out.print( " x" );
            else
              System.out.print( " ~" );

            break;
          case SHIP:
            if( points[x][y].isAttacked( ))
              System.out.print( " *" );
            else
              System.out.print( " S" );
            break;
        }
      }
      System.out.print( "\n" );
    }
  }
}
