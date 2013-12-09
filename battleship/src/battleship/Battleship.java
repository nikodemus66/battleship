/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.controller.*;
import battleship.controller.Engine.PlayerType;
import battleship.model.*;

/**
 *
 * @author nikodemus
 */
public class Battleship
{
    /**
     * @param args the command line arguments
     */
    public static void main( String[] args )
    {
        System.out.println( "battleship started" );
        //GUI gui = new GUI();

        Engine engine = new Engine( ); // controller
        engine.setPlayers( PlayerType.HUMAN, PlayerType.KI );
        engine.start( );
    }
}
