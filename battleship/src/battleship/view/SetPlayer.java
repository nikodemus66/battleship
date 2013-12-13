/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.view;

import battleship.model.Ship;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/**
 *
 * @author nikodemus
 */
public class SetPlayer extends JDialog{
    private JFrame frame;
    private JPanel north;
    private JPanel center;
    private JPanel south;
    private JPanel east;
    private JButton searchButton;
    
    private JButton schlachtschiffButton;
    private JButton kreuzerButton;
    private JButton zerstörerButton;
    private JButton uBootButton;
    
    private JTextField ipAddressTextField;
    
    private Ship ship;
    

    public SetPlayer()
    {
//                super("Battleship");
        setSize(350,400);
        setMinimumSize(new Dimension(350,350));
        // Layout-Manager setzen
        setLayout(new BorderLayout());
        setModalityType(ModalityType.APPLICATION_MODAL);
        //        makeInitialFrame();

        
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
        
        setVisible(true);
    }
    
        private class ButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == searchButton){
                try {
                    //get IP
                    String ipAddress = ipAddressTextField.getText();
                    InetAddress inetAdd = InetAddress.getByName(ipAddress);
//                    engine.getOpponent(inetAdd);
                    String ip = inetAdd.toString();
                    System.out.println(ip);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(GUI2dView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //JButton schlachtschiffButton;
            //JButton kreuzerButton;
            //JButton zerstörerButton;
            //JButton uBootButton;
            //1 x Schlachtschiff: 5 Kästchen
            //2 x Kreuzer : 4 Kästchen
            //3 x Zerstörer: 3 Kästchen
            //4 x U-boot : 2 Kästchen
            if (e.getSource()== schlachtschiffButton){
            ship = new Ship("Schlachtschiff", 5);
            }
            if(e.getSource()== kreuzerButton){
            ship = new Ship("Kreuzer", 4);
            }
            if(e.getSource()== zerstörerButton){
            ship = new Ship("Zerstörer", 3);
            }
            if(e.getSource()== uBootButton){
            ship = new Ship("U-Boot", 2);
            }
        }
    }
}
