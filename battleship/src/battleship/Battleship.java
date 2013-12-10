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
  public static void main( String[] args ) throws Exception
  {
    View v = null;
    if( args.length > 0 && args[0].equals( "--cli" ))
      v = new CommandLineView( );
    else
      v = new GUI2dView( );

    Player one = new HumanPlayer( v );
    Engine engine = new Engine( one ); // controller
    engine.start( );
  }
}
