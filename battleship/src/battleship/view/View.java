/*
 *
 *
 *
 */

package battleship.view;

import battleship.controller.*;

/**
 *
 * @author txschmid
 */

public interface View
{
    void do_start( );
    void do_setup( Engine engine );
    void do_placeShip( );
    void do_shoot( );
    void changingPlayer( );
    void do_update( );
    void youLost( );
    void youWon( );
}
