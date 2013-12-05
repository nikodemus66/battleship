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
    //System.out.println( "Please place you ship ..." );
    //byte[] tmp = new byte[255];
    //try {
      //System.in.read( tmp );
    //} catch(IOException e) {
      //System.out.println( "CommandLineView:start( ): reading failded" );
    //}
    //if( engine.placeShip( new Ship("Ship1", 2 ), 0, 5 ))
    //{
      //System.out.println( "CommandLineView: Ship placed!" );
    //}
    //else
    //{
      //System.out.println( "CommandLineView: Ship could not been placed!" );
    //}

    // ready -> startGame
    //engine.playerReady( ); // returns when opponent is ready
    //engine.shoot( 5, 5 );
  }

  public void do_placeShip( )
  {
    //List<Ships> ships = engine.getShips( );
    //TODO: print ships
    Ship ship1 = new Ship( "Destroyer", 3 );
    Ship ship2 = new Ship( "Offender", 5 );
    System.out.println( "0: " + ship1 );
    System.out.println( "1: " + ship2 );

    //int count = ships.list
    int count = 2;
    while ( count > 0 )
    {
      System.out.print( "Select ship to place: " );
      byte[] tmp = new byte[255]; // FIXME: use streams for reading int
      try {
        System.in.read( tmp );
      } catch(IOException e) {
        System.out.println( "CommandLineView:start( ): reading failded" );
      }

      Ship toPlace = null;
      switch( 0 )
      {
        case 0:
          toPlace = ship1;
          break;
        case 1:
          toPlace = ship2;
          break;
        default:
          System.out.println( "not a valid ship id: " + tmp );
      }

      if( engine.placeShip( toPlace, 0, 5 ))
      {
        System.out.println( "ship placed: "+ toPlace );
        count--;
      }
      drawBoard( );
    }
  }

  public void do_shoot( )
  {
    int count = 2;
    System.out.print( "Where do you want to shoot? [x y]" );
    byte[] tmp = new byte[255]; // FIXME: use streams for reading int
    try {
      System.in.read( tmp );
    } catch(IOException e) {
      System.out.println( "CommandLineView:start( ): reading failded" );
    }

    // TODO: parse where to shoot, validate

    if( engine.shoot( 4, 5 ))
    {
      System.out.println( "view has shot" );
    }
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
