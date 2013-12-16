/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.client.CommandLinePlayer;
import battleship.client.GUIPlayer;
import battleship.client.Player;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author kraeki
 */
public class Battleship
{
  private final static Logger LOGGER = Logger.getLogger("");
  
  /**
   * @param args the command line arguments
     * @throws java.lang.Exception
   */
  public static void main( String[] args ) throws Exception
  {
    // setup logger
   LOGGER.setLevel(Level.INFO);
   LOGGER.removeHandler(LOGGER.getHandlers()[0]);
   Handler handler = new FileHandler("battleship.log");
   LOGGER.addHandler(handler);
   SimpleFormatter formatter = new SimpleFormatter();
   handler.setFormatter(formatter);


    Player player;
    if( args.length > 0 && args[0].equals( "--cli" ))
      player = new CommandLinePlayer( "HumanPlayer" );
    else
      player = new GUIPlayer( "HumanPlayer" );
    
  }
}
