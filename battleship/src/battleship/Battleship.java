/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.controller.*;
import battleship.model.*;
import battleship.view.*;

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
        View v = new CommandLineView( );
        Player one = new HumanPlayer( v );
        Engine engine = new Engine( one ); // controller
        engine.start( );
    }
}
