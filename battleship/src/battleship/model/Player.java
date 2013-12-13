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
  protected int shipCount = 0;
  private boolean ready = false;
  private boolean shouldShoot = false;
  protected Engine engine;

  public Player( )
  {
  }

  public void init( Engine e )
  {
    engine = e;
    init( );
  }


  public void setReady( )
  {
    ready = true;
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
  public abstract void init( );
  public abstract void startingGame( );
  public abstract void do_update( );
  public abstract void yourTurn( );
  public abstract void youLost( );
  public abstract void youWon( );
}
