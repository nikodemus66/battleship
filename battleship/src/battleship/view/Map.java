/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.view;
import battleship.Ship;
    import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
/**
 *
 * @author Michael Zihlmann
 */
public class Map extends JPanel{
    
    private int posx;
    private int posy;
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLS = 10;    
    public static final int MAP_SIZE = 40;
    GridLayout layout;
    MouseListener listener;
    int[] coords;


    public Map(){
        
        super(new GridLayout(10,10));

        int preferredWidth = NUM_COLS * MAP_SIZE;
        int preferredHeight = NUM_ROWS * MAP_SIZE;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        
    }
    
    public int[] getCoords(){
        
        MouseEvent place = null;
        posx = place.getX();
        listener.mouseClicked(place);
        
        return coords;
    }
    
    public void update(){
             
    
      
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Game");
                Map map = new Map();
                frame.add(map);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
