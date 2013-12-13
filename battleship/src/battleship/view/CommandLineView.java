/*
 *
 *
 *
 */

package battleship.view;

import battleship.controller.*;
import battleship.model.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author txschmid
 */
public class CommandLineView implements View
{
  private Engine engine;

  public CommandLineView( Engine e )
  {
    this.engine = e;

    Player one = new HumanPlayer( this );
    engine.setPlayer( one );

    do_setup( );
    do_start( );
    do_placeShip( );
  }

  public void do_setup( )
  {
    System.out.println( "Possible opponends:" );
    System.out.println( "0: AI" );
    System.out.println( "1: Connect to network player" );
    System.out.println( "2: Create network server" );
    System.out.println( );

    Scanner in = new Scanner( System.in );
    System.out.print( "Choose opponend: " );
    int tmp = in.nextInt( );

    switch( tmp )
    {
      case 0:
        engine.setOpponendAI( );
        break;
      case 1:
        // TODO: read ip and port
        System.out.print( "Enter IP of network player: " );
        String ip = in.next( );
        engine.setOpponendNetwork( ip );
        break;
      case 2:
        engine.setOpponendNetwork( ); // server
        break;
      default:
        System.out.println( "error: opponend " + tmp + " does not exist" );
    }
  }

  public void do_start( )
  {
    draw( );
  }

  public void do_placeShip( )
  {
    ArrayList<Ship> ships = engine.getShips( );
    //int count = ships.list
    int count = ships.size( );
    while ( count > 0 )
    {
      for( int i = 0; i < ships.size( ); i++ )
      {
        System.out.println( i + ": " + ships.get( i ));
      }

      System.out.print( "Select ship to place: " );
      Scanner in = new Scanner( System.in );
      int tmp = in.nextInt();
      if( tmp >= ships.size( ) )
      {
        System.out.println( "Ship " + tmp + " not available, select another" );
        continue;
      }
      System.out.print( "where to place [x y]?: " );
      int x = in.nextInt();
      int y = in.nextInt();

      Ship toPlace = ships.get(tmp);

      if( engine.placeShip( toPlace, x, y ))
      {
        System.out.println( "ship placed: "+ toPlace );
        ships.remove( tmp );
        count--;
      }
      draw( );
    }
    try{
    engine.playerReady( 0 );
    }
    catch( Exception e)
    {
        System.out.println( "Error: " + e );
    }
  }

  public void changingPlayer( )
  {
    System.out.println( );
    System.out.println( "CommandLineView:changingPlayer( )" );
    System.out.println( );
  }

  public void youLost( )
  {
    System.out.println( "Gameover: You lost!" );
    if( restart( ))
    {
      engine.restart( );
    }
  }

  public void youWon( )
  {
    System.out.println( "Gameover: You won!" );
    if( restart( ))
    {
      engine.restart( );
    }
  }

  private boolean restart( )
  {
    System.out.print( "Do you want to start over? [y/n]: " );
    Scanner in = new Scanner( System.in );
    String ans = in.nextLine();
    if( "y".equals( ans.toLowerCase( )))
      return true;
    return false;
  }

  public void yourTurn( )
  {
    int count = 2;
    System.out.print( "Where do you want to shoot [x y]?: " );
    Scanner in = new Scanner( System.in );
    int x = in.nextInt();
    int y = in.nextInt();

    if( engine.shoot( 0, x, y ))
    {
      // successful
    }
    draw( );
  }

  public void do_update( )
  {
  }

  private void draw( )
  {
    System.out.print( "Opponend's grid:  ================== " );
    drawBoard( engine.getGridOpponend( ));

    System.out.println( );
    System.out.print( "Your grid         ================== " );
    drawBoard( engine.getGrid( ));
    System.out.println( );
  }

  private void drawBoard( Grid board )
  {
    System.out.println( board );
    System.out.println( );
    Point[][] points = board.getPointArray( );

    System.out.print( "   " );
    for( int x = 0; x < points[0].length; x++ )
      System.out.print( x + " " );
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

    @Override
    public void startingGame() {
      System.out.println( "View: got startingGame( )" );

    }

}
