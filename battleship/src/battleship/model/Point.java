/*
 *
 *
 *
 */

package battleship.model;

/**
 *
 * @author txschmid
 */
public class Point
{
  public enum Type { WATER, SHIP }
  private Type type;
  private boolean attacked = false;

  public Point( Type t )
  {
    type = t;
  }

  public void shot( )
  {
    attacked = true;
  }

  public boolean isAttacked( )
  {
    return attacked;
  }

  public Type getType( )
  {
    return type;
  }

  public void setType( Type t )
  {
    type = t;
  }
}
