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
  private boolean attacked = false;
  private ShootState state;
  private Ship ship;

  public Point( )
  {
    state = ShootState.WATER;
    ship = null;
  }

  public void setShip( Ship s )
  {
    state = ShootState.SHIP;
    ship = s;
  }

  public Ship getShip( )
  {
    return ship;
  }

  public boolean shoot( )
  {
    if( attacked )
      return false;

    switch( state )
    {
      case SHIP:
        ship.hit( );
        if( !ship.isAlive( ))
          state = ShootState.HIT_SUNKEN;
        else
          state = ShootState.HIT;
        return true;
      case WATER:
        state = ShootState.MISS;
        return true;
    }
    return false;
  }

  public ShootState getState( )
  {
    return state;
  }
}
