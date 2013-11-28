/*
Die Klasse stellt das Spielfeld dar.
 */

package battleship;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Michael
 */
public class GUI {
    
    static List<Ship> ships = new LinkedList<Ship>();
    
    public GUI(){
        
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setUndecorated(true);
    frame.setResizable(false);
    
    DisplayMode dm = new DisplayMode(1024, 600, 16, 60);
    GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice device = environment.getDefaultScreenDevice();
		
    device.setFullScreenWindow(frame);
    device.setDisplayMode(dm);
    
    }
    

    
}
