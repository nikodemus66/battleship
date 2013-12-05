/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.view;

import battleship.Engine;
import battleship.View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
/**
 *
 * @author brian
 */
public class GUI2dView extends JFrame implements View {

    private JFrame frame;
    private JPanel north;
    private JPanel center;
    private JPanel south;
    /**
     * Initialisiert das erste GUI beim Öffnen der Datei Battleship.exe
     */
    public GUI2dView(){
        // JFrame initialisieren
        super("Battleship");
        setSize(350,400);
        setMinimumSize(new Dimension(350,350));
        // Layout-Manager setzen
        setLayout(new BorderLayout());
        makeInitialFrame();
        setVisible(true);
    }
    /**
     * Update das Spielfeld
     */

    public void update( ){
    
    }
    
    /**
     * erzeuge alle Elemente im ersten Frame
     */
    private void makeInitialFrame(){

        //GUI Elemente ereugen
        north = new JPanel(new FlowLayout());
        center = new JPanel(new FlowLayout());
        south = new JPanel(new FlowLayout());

        //JPanels
        //JPanel opponent zusammensetzen
        north.add(new JLabel("enter opponent IP"));
        north.add(new JTextField(10));
        //JButton search
        JButton search = new JButton("search");
        search.setSize(10,20);
        north.add(search);
        
        //JPanel invite zusammensetzen
        center.add(new JLabel("found player")); //name soll später hinzugefügt werden
        JButton invitePlayer = new JButton("invite");
        invitePlayer.setSize(10,20);
        center.add(invitePlayer);
        
        //JPanel invited zusammensetzen
        south.add(new JLabel("you got invited"));
        //JButton accept
        JButton accept = new JButton("accept invite");
        accept.setSize(10,20);
        south.add(accept);
        
        //GUI zusammensetzen
        add(north,BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
    }
    
    /**
     * erstelle das zweite Frame wo die Schiffe plaziert werden
     */
    private void makeShipPlacingFrame(){
        //alles entfernen
        north.removeAll();
        center.removeAll();
        south.removeAll();    
                
        north.add(new JLabel("Schiffe plazieren"),BorderLayout.NORTH);
        //center.add(new Playground...)
        south.add(new JButton("ready"));
        south.add(new JButton("start"));
    }
    
        /**
     * starte Spiel wenn alle Spieler bereit sind
     */
    public void start( Engine engine ){

        north.removeAll();
        center.removeAll();
        south.removeAll();     
        
        setSize(600,400);
        north.add(new JLabel("Your turn"));
        north.add(new JLabel("Waiting for the opponent"));
        //center.add(new Playground);
        south.add(new JButton("give up"));
    }
    
}