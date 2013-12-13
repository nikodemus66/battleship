/*
 *
 *
 *
 */

package battleship.view;

import battleship.controller.*;
import battleship.model.*;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

/**
 *
 * @author txschmid
 */
public class CommandLineView implements View
{
  private final static Logger LOGGER = Logger.getLogger("");
  private HumanPlayer player;

  public CommandLineView( HumanPlayer p )
  {
    this.player = p;
  }

  public void do_start( )
  {
    do_setup( );
    draw( );
    do_placeShip( );
    try{
      player.playerReady( );
    }
    catch( Exception e)
    {
        System.out.println( "Error: " + e );
    }

    while( !player.isGameover( ))
    {
      if( player.isMyTurn( ))
        do_shoot( );
      else
        try {
            Thread.sleep( 200 );
      } catch (InterruptedException ex) {
          LOGGER.severe( "View: sleep got interrupted: " + ex );
      }
    }
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
        player.setOpponendAI( );
        break;
      case 1:
        // TODO: read ip and port
        System.out.print( "Enter IP of network player: " );
        String ip = in.next( );
        player.setOpponendNetworkClient( ip );
        break;
      case 2:
        player.setOpponendNetworkServer( ); // server
        break;
      default:
        System.out.println( "error: opponend " + tmp + " does not exist" );
    }
  }

  public void do_placeShip( )
  {
    LOGGER.info( "View: placing ships" );
    ArrayList<Ship> ships = player.getShips( );
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

      if( player.placeShip( toPlace, x, y ))
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
    System.out.println( );
  }

  public void youLost( )
  {
    System.out.println( "Gameover: You lost!" );
    if( restart( ))
    {
      player.restart( );
    }
  }

  public void youWon( )
  {
    System.out.println( "Gameover: You won!" );
    if( restart( ))
    {
      player.restart( );
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
  }

  public void do_shoot( )
  {
    int count = 2;
    System.out.print( "Where do you want to shoot [x y]?: " );
    Scanner in = new Scanner( System.in );
    int x = in.nextInt();
    int y = in.nextInt();

    if( player.shoot( x, y ))
    {
      // successful
    }
    draw( );
  }

  public void do_update( )
  {
      LOGGER.info( "View: do_update called" );
  }

  private void draw( )
  {
    System.out.print( "Opponend's grid:  ================== " );
    drawBoard( player.getGridOpponend( ));

    System.out.println( );
    System.out.print( "Your grid         ================== " );
    drawBoard( player.getGrid( ));
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
