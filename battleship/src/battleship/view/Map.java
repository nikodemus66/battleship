/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.view;

import battleship.controller.Engine;
import battleship.model.*;
import battleship.model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 *
 * @author Michael Zihlmann
 */
public class Map extends JPanel implements MouseListener{
    
    private int posx;
    private int posy;   
    public static final int MAP_SIZE = 400;
    private MouseListener listener;
    private MouseEvent me;
    private int[] coords;
    private JPanel field;



    public Map(){
        
        super(new GridLayout(10,10));
        setPreferredSize(new Dimension(MAP_SIZE, MAP_SIZE));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        for (int i =0; i<(10*10); i++){
            field = new JPanel();
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            field.setBackground(Color.BLUE);
            field.addMouseListener(listener);
            this.add(field);
            
        }
        
        
    }
    
    
    public int[] getCoords(){
        
        listener.mouseClicked(me);
        posy = (int) me.getY() * 100;
        posx = (int) me.getX() * 100;
        
        System.out.println(posy);
        
        return coords;
    }
    
    public int[] getField(){
                
        listener.mouseClicked(me);
        posy = (int) me.getY() * 100;
        posx = (int) me.getX() * 100;
        return coords;
    }
    
    
    public void paint(){
        for (int i =0; i<(10*10); i++){
           
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            field.setBackground(Color.BLUE);
            
            this.add(field);
            
            
        
        }
                    
              
                
      
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Map");
                Map map = new Map();
                frame.add(map);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.getCoords();
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    
