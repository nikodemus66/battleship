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

  public CommandLineView( )
  {
    System.out.println( "CommandLineView: initialized" );
  }

  public void do_setup( Engine engine )
  {
    System.out.println( "CommandLineView:do_setup( )" );
    this.engine = engine;
    engine.setOpponend( Engine.PlayerType.KI );
  }

  public void do_start( )
  {
    System.out.println( "CommandLineView:do_start( )" );
    draw( );
  }

  public void do_placeShip( )
  {
    System.out.println( "CommandLineView:do_placeShip( )" );
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
  }

  public void changingPlayer( )
  {
    System.out.println( );
    System.out.println( "CommandLineView:changingPlayer( )" );
    System.out.println( "look away!" );
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

  public void do_shoot( )
  {
    System.out.println( "CommandLineView:do_shoot( )" );
    int count = 2;
    System.out.print( "Where do you want to shoot [x y]?: " );
    Scanner in = new Scanner( System.in );
    int x = in.nextInt();
    int y = in.nextInt();

    if( engine.shoot( x, y ))
    {
      System.out.println( "view has shot" );
    }
    draw( );
  }

  public void do_update( )
  {
    System.out.println( "CommandLineView:do_update( )" );
  }

  private void draw( )
  {
    System.out.print( "Your grid         ================== " );
    drawBoard( engine.getGrid( ));

    System.out.println( );
    System.out.print( "Opponend's grid:  ================== " );
    drawBoard( engine.getGridOpponend( ));
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
