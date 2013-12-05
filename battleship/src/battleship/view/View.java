/*
 *
 *
 *
 */

package battleship;

/**
 *
 * @author txschmid
 */

public interface View
{
    void start( Engine engine );
    void do_placeShip( );
    void do_shoot( );
    void update( );
}
