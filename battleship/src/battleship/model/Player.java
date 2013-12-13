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
  protected int shipCount;
  private boolean ready = false;
  private boolean shouldShoot = false;

  public Player( )
  {
    ships.add( new Ship( "Destroyer", 4 ));
    ships.add( new Ship( "Terminator", 2 ));
  }

  public void setReady( )
  {
    ready = true;
  }

  public void yourTurn( )
  {
    shouldShoot = true;
  }

  public boolean shouldShoot( )
  {
    return shouldShoot;
  }

  public boolean isReady( )
  {
    return ready;
  }

  public Grid getGrid( )
  {
    return board;
  }

  public ArrayList<Ship> getShips( )
  {
    return ships;
  }

  public int getShipCount()
  {
      return shipCount;
  }

  public void incrementShipCount()
  {
      this.shipCount++;
  }

  // engine calls these functions

  //public abstract void do_setup( Engine e );
  public abstract void startingGame( );
  public abstract void do_update( );
  public abstract void youLost( );
  public abstract void youWon( );
}
