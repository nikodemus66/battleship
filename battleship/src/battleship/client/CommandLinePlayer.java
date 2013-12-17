/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.client;

import battleship.server.Engine;
import battleship.model.EngineState;
import battleship.model.ShipType;
import battleship.model.Ship;
import battleship.model.ShootState;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kraeki
 */
public class CommandLinePlayer extends Player
{

  public CommandLinePlayer( String name )
  {
    super(name);
    start( );
  }

  public void start( )
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
        {
          Engine engine = new Engine( ); // controller
          engine.start();

          connect("127.0.0.1");

          AIPlayer player2 = new AIPlayer( "AIPlayer" );
          player2.connect("127.0.0.1");
        }
        break;
      case 1:
        {
          System.out.print( "Enter IP of network player: " );
          String ip = in.next( );
          connect( ip );
        }
        break;
      case 2:
        {
          Engine engine = new Engine( ); // controller
          engine.start();

          connect("127.0.0.1");
        }
        break;
      default:
        System.out.println( "error: opponend " + tmp + " does not exist" );
    }

    //System.out.println( "CommandLinePlayer: Terminated" );
  }

  @Override
  protected void stateChanged(EngineState state) {
    //System.out.println( "CommandLinePlayer: got informed about engine state: " + state.name());
    switch( state )
    {
      case SELECTING_OPPONEND:
        {

        }
        break;
      case PREPARING_GRID:
        {
          update( ); // draws baord
          ArrayList<ShipType> ships = Player.getShips();

          int count = ships.size( );
          while ( count > 0 )
          {
            for( int i = 0; i < ships.size( ); i++ )
            {
              String s = "";
              for( int m = 0; m < Ship.getShipLength( ships.get( i )); m++ )
                s += "x";
              System.out.println( i + ": " + ships.get( i ).name( ) + " " + s );
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

            ShipType toPlace = ships.get( tmp );

            if( placeShip( toPlace, x, y, true ))
            {
              System.out.println( "ship placed: "+ toPlace );
              ships.remove( tmp );
              count--;
            }
          }
          playerReady();
        }
        break;
      case PLAY:
        {
        }
        break;
      case YOUR_TURN:
        {
          System.out.print( "Where do you want to shoot [x y]?: " );
          Scanner in = new Scanner( System.in );
          int x = in.nextInt();
          int y = in.nextInt();
          ShootState result = shoot( x, y );
          System.out.println( "Player " + this.getName() + ": shoot: " + result );
        }
        break;
      case OPPONENDS_TURN:
        {

        }
        break;
      case YOU_LOST:
        {
          System.out.println( "Gameover: You lost!" );
        }
        break;
      case YOU_WON:
        {
          System.out.println( "Gameover: You won!" );
        }
        break;
      case FINISHED:
        {
        }
        break;
      default:
        throw new AssertionError(state.name());
    }
  }

  @Override
  protected void update( ShootState[][] myBoard, ShootState[][] opponendBoard )
  {
    System.out.print( "Opponend's grid:  ================== " );
    drawBoard( opponendBoard );

    System.out.println( );
    System.out.print( "Your grid         ================== " );
    drawBoard( myBoard );
    System.out.println( );
  }

  private void drawBoard( ShootState[][] board )
  {
    System.out.println( board );
    System.out.println( );
    System.out.print( "   " );
    for( int x = 0; x < board[0].length; x++ )
      System.out.print( x + " " );
    System.out.print( "\n" );

    for( int y = 0; y < board.length; y++ )
    {
      System.out.print( y + "|" );
      for( int x = 0; x < board[y].length; x++ )
      {
        switch( board[x][y] )
        {
          case WATER:
            System.out.print( " ~" );
            break;
          case SHIP:
            System.out.print( " S" );
            break;
          case MISS:
            System.out.print( " x" );
            break;
          case HIT:
            System.out.print( " *" );
            break;
          case HIT_SUNKEN:
            System.out.print( " O" );
            break;
          default:
            throw new AssertionError(board[x][y].name());
        }
      }
      System.out.print( "\n" );
    }
  }
}
