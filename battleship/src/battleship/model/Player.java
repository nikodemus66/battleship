/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.model;

import battleship.controller.*;
import java.util.ArrayList;

/**
 *
 * @author nikodemus
 */
public abstract class Player
{
  private Grid board = new Grid( 10,10 );
  private ArrayList<Ship> ships = new ArrayList<Ship>( );

  public Player( )
  {
    ships.add( new Ship( "Destroyer", 4 ));
    ships.add( new Ship( "Terminator", 2 ));
  }

  public Grid getGrid( )
  {
    return board;
  }

  public ArrayList<Ship> getShips( )
  {
    return ships;
  }

  // engine calls these functions
  public abstract void do_start( );
  public abstract void do_setup( Engine engine );
  public abstract void do_placeShip( );
  public abstract void do_shoot( );
  public abstract void do_update( );
  public abstract void youLost( );
  public abstract void youWon( );
}
