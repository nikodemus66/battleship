/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.view.GUI2dView;

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

        Grid model = new Grid( 10, 10 ); // model
        View view = new GUI2dView( ); // view
        Engine engine = new Engine( view, model ); // controller
        engine.start( );
    }
}
