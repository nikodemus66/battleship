/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.model;

import java.util.ArrayList;

/**
 *
 * @author brian
 */
public class Grid
{
  private Point[][] points;
  private ArrayList<Ship> ships;
  private int size_x;
  private int size_y;

  public Grid( int size_x, int size_y )
  {
    this.size_x = size_x; // used for toString, FIXME: find alternative
    this.size_y = size_y;
    points = new Point[size_x][size_y];
    clear( );

    ships = new ArrayList<Ship>();
  }

  public boolean placeShip( ShipType s, int x, int y, boolean vertical )
  {
    Ship ship = new Ship( s, x, y, vertical );
    if( vertical )
    {
        if(( y + Ship.getShipLength(s)) > 10 )
            return false;

      for( int i = y; i < Ship.getShipLength(s) + y; i++ )
      {
        Point p = points[x][i];
        if( p.getState() != ShootState.WATER )
        {
          return false;
        }
        p.setShip( ship );
      }
    }
    else // horizontal
    {
        if(( x + Ship.getShipLength(s)) > 10 )
            return false;
      for( int i = x; i < Ship.getShipLength(s) + x; i++ )
      {
        Point p = points[i][y];
        if( p.getState()!= ShootState.WATER )
        {
          return false;
        }
        p.setShip( ship );
      }
    }

    ships.add( ship );
    return true;
  }

  public ShootState shoot( int x, int y )
  {
    Point p = points[x][y];
    if( p.shoot( ))
    {
      return p.getState( );
    }
    return ShootState.MISS;
  }

  public Ship getShip( int x, int y )
  {
    return points[x][y].getShip( );
  }

  public boolean allShipDead( )
  {
      boolean ret = true;
      for( Ship s: ships )
      {
          ret &= s.isAlive();
          System.out.println( "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ: ship state: " + s.isAlive( ));
      }
      return !ret;
  }

  public void clear( )
  {
    for( int x = 0; x < points.length; x++ )
    {
      for( int y = 0; y < points[x].length; y++ )
      {
        points[x][y] = new Point( );
      }
    }
  }

  @Override
  public String toString( )
  {
    return "Grid " + this.size_x + "x" + this.size_y;
  }
}
