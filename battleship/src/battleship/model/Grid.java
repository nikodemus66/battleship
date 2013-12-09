/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.model;

/**
 *
 * @author brian
 */
public class Grid
{
  private Point[][] points;
  private int size_x;
  private int size_y;

  public Grid( int size_x, int size_y )
  {
    this.size_x = size_x; // used for toString, FIXME: find alternative
    this.size_y = size_y;
    points = new Point[size_x][size_y];

    // initialize field
    clear( );
  }

  public Point[][] getPointArray( )
  {
    //FIXME: return a copy
    return points;
  }

  public Point getPoint( int x, int y )
  {
    //FIXME: return a copy
    return points[x][y];
  }

  public void clear( )
  {
    for( int x = 0; x < points.length; x++ )
    {
      for( int y = 0; y < points[x].length; y++ )
      {
        points[x][y] = new Point( Point.Type.WATER );
      }
    }
  }

  @Override
  public String toString( )
  {
    return "Grid " + this.size_x + "x" + this.size_y;
  }
}
