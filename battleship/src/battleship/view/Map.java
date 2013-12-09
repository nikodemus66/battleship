/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.view;

import battleship.model.*;

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
    public static final int MAP_SIZE = 400;
    GridLayout layout;
    MouseListener listener;
    MouseEvent me;
    int[] coords;


    public Map(){
        
        super(new GridLayout(10,10));
        setPreferredSize(new Dimension(MAP_SIZE, MAP_SIZE));
        
    }
    
    public int[] getCoords(){
        
        posy = me.getY();
        posx = me.getX();
        listener.mouseClicked(me);
        
        return coords;
    }
    
    public void update(Grid g){
             
    
      
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
