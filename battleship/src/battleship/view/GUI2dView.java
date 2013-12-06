/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.view;

import battleship.Engine;
import battleship.Ship;
import battleship.View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;
/**
 *
 * @author brian
 */
public class GUI2dView extends JFrame implements View {
    
    private Engine engine;
    
    private JFrame frame;
    private JPanel north;
    private JPanel center;
    private JPanel south;
    
    private Map myMap;
    private Map enemyMap;
    
    private JButton searchButton;
    private JTextField ipAddressTextField;
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
        //myMap.update(engine.getGrid());
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
        ipAddressTextField = new JTextField(10);
        north.add(ipAddressTextField);
        //JButton search
        searchButton = new JButton("search");
        searchButton.setSize(10,20);
        searchButton.addActionListener(new ButtonActionListener());
        north.add(searchButton);
        
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
        //myMap = new Map(...);
        center.add(myMap);
        south.add(new JButton("ready"));
        south.add(new JButton("start"));
    }
    
        /**
     * starte Spiel wenn alle Spieler bereit sind
     */
    public void start( Engine engine ){

        this.engine = engine;

    }
    
    
    public void do_placeShip(){
    makeShipPlacingFrame();
    // wird aufgerufen, wenn spieler Schiff auf Feld plaziert
    myMap.getLocation();
    //engine.placeShip(Ship, x, y);
    }
    public void do_startGame(){
                north.removeAll();
        center.removeAll();
        south.removeAll();     
        
        setSize(600,400);
        north.add(new JLabel("Your turn"));
        north.add(new JLabel("Waiting for the opponent"));
        center.add(myMap);
        center.add(enemyMap);
        south.add(new JButton("give up"));
        //Nun wird gespielt
        
    }
    public void do_shoot(){}
    
    private class ButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) throws UnknownHostException{
            if (e.getSource() == searchButton){
                //get IP
                String ipAddress = ipAddressTextField.getText();
                InetAddress inetAdd = InetAddress.getByName(ipAddress);
                engine.getOpponent(inetAdd);
                String ip = inetAdd.toString();
                System.out.println(ip);
            }
        }
    }
    
}