///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package battleship.view;
//
//import battleship.controller.*;
//import battleship.model.*;
//import battleship.view.*;
//
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.*;
//
//
///**
// * Hier wird das Grafical User Interface implementiert.
// * Dieses enthält eine Map, welche für das Plazieren der Schiffe und das
// * Spielen benötigt wird.
// * Das GUI kommuniziert dabei mit den Klassen Map und Engine.
// * @author brian
// */
//public class GUI2dView extends JFrame implements View {
//
//    private HumanPlayer player;
//
//    private JFrame frame;
//    private JPanel north;
//    private JPanel center;
//    private JPanel south;
//    private JPanel east;
//
//    private Map myMap = new Map();
//    private Map enemyMap = new Map();
//
//    private JButton searchButton;
//    private JTextField ipAddressTextField;
//    private JButton schlachtschiffButton;
//    private JButton kreuzerButton;
//    private JButton zerstörerButton;
//    private JButton uBootButton;
//    private JLabel schlachtschiffLabel;
//    private JLabel kreuzerLabel;
//    private JLabel zerstörerLabel;
//    private JLabel uBootLabel;
//
//    private Ship ship;
//    private int schlachtNumb = 1;     //1 x Schlachtschiff: 5 Kästchen
//    private int kreuzerNumb = 2;      //2 x Kreuzer : 4 Kästchen
//    private int zerstörerNumb = 3  ;  //3 x Zerstörer: 3 Kästchen
//    private int uBootNumb = 4;        //4 x U-boot : 2 Kästchen
//    private int shipNumb = schlachtNumb + kreuzerNumb + zerstörerNumb + uBootNumb;
//
//    /**
//     * Initialisiert das erste GUI beim Öffnen der Datei Battleship.exe
//     */
//    public GUI2dView( HumanPlayer player ){
//        super("Battleship");
//        this.player = player;
//    }
//    
//    public void do_start()
//    {
//        // JFrame initialisieren
//        setSize(350,400);
//        setMinimumSize(new Dimension(350,350));
//        // Layout-Manager setzen
//        setLayout(new BorderLayout());
//        makeInitialFrame();
//        setVisible(true);
//
//        do_setup( );
//    }
//
//    public void do_setup( )
//    {
//        player.setOpponendAI( );
//    }
//
//    @Override
//    public void changingPlayer( )
//    {
//
//    }
//
//    @Override
//    public void youLost( )
//    {
//    }
//
//    @Override
//    public void youWon( )
//    {
//
//    }
//
//    /**
//     * Update das Spielfeld
//     */
//    @Override
//    public void do_update( ){
//        //myMap.update(engine.getGrid());
//    }
//
//    /**
//     * erzeuge alle Elemente im ersten Frame
//     */
//    private void makeInitialFrame(){
//
//        //GUI Elemente ereugen
//        north = new JPanel(new FlowLayout());
//        center = new JPanel(new FlowLayout());
//        south = new JPanel(new FlowLayout());
//
//        //JPanels
//        //JPanel opponent zusammensetzen
//        north.add(new JLabel("enter opponent IP"));
//        ipAddressTextField = new JTextField(10);
//        north.add(ipAddressTextField);
//        //JButton search
//        searchButton = new JButton("search");
//        searchButton.setSize(10,20);
//        searchButton.addActionListener(new ButtonActionListener());
//        north.add(searchButton);
//
//        //JPanel invite zusammensetzen
//        center.add(new JLabel("found player")); //name soll später hinzugefügt werden
//        JButton invitePlayer = new JButton("invite");
//        invitePlayer.setSize(10,20);
//        center.add(invitePlayer);
//
//        //JPanel invited zusammensetzen
//        south.add(new JLabel("you got invited"));
//        //JButton accept
//        JButton accept = new JButton("accept invite");
//        accept.setSize(10,20);
//        south.add(accept);
//
//        //GUI zusammensetzen
//        add(north,BorderLayout.NORTH);
//        add(center, BorderLayout.CENTER);
//        add(south, BorderLayout.SOUTH);
//    }
//
//    /**
//     * erstelle das zweite Frame wo die Schiffe plaziert werden
//     */
//    private void makeShipPlacingFrame(){
//                //alles entfernen
//        north.removeAll();
//        center.removeAll();
//        south.removeAll();
//        east = new JPanel(new GridLayout(0,2));
//        north.add(new JLabel("Schiffe plazieren"),BorderLayout.NORTH);
//        updateShipNumbers();
//        //myMap = new Map(...);
//        center.add(myMap);
//        //1 x Schlachtschiff: 5 Kästchen
//        //2 x Kreuzer : 4 Kästchen
//        //3 x Zerstörer: 3 Kästchen
//        //4 x U-boot : 2 Kästchen
//        schlachtschiffButton = new JButton("Schlachtschiff(5)");
//        kreuzerButton = new JButton("Kreuzer (4)");
//        zerstörerButton = new JButton("Zerstörer(3)");
//        uBootButton = new JButton("U-bot (2)");
//        east.add(schlachtschiffButton);
//        schlachtschiffLabel = new JLabel("1/1");
//        east.add(schlachtschiffLabel);
//        east.add(kreuzerButton);
//        kreuzerLabel = new JLabel("2/2");
//        east.add(kreuzerLabel);
//        east.add(zerstörerButton);
//        zerstörerLabel = new JLabel("3/3");
//        east.add(zerstörerLabel);
//        east.add(uBootButton);
//        uBootLabel = new JLabel("4/4");
//        east.add(uBootLabel);
//
//        south.add(new JButton("ready"));
//        south.add(new JButton("start"));
//        add(east, BorderLayout.EAST);
//    }
//
//
//    /**
//     * Hier wird das Spielfeld aufgebaut, welches für das eigentliche Spielen
//     * verwendet wird.
//     */
//    public void do_startGame(){
//                north.removeAll();
//        center.removeAll();
//        south.removeAll();
//
//        setSize(600,400);
//        north.add(new JLabel("Your turn"));
//        north.add(new JLabel("Waiting for the opponent"));
//        center.add(myMap);
//        center.add(enemyMap);
//        south.add(new JButton("give up"));
//        //Nun wird gespielt
//
//    }
//
//    /**
//     * Hier wird der Spieler aufgerufen, das feindliche Feld anzugreifen.
//     */
//    public void do_shoot(){
//        int[] coords = myMap.getCoords();
//        player.shoot( coords[0], coords[1]);
//    }
//
//    @Override
//    public void startingGame() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void yourTurn() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    /**
//     * Dies ist eine Innere Klasse, die als ActionListener für die JButtons
//     * verwendet wird.
//     */
//    private class ButtonActionListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e){
//            if (e.getSource() == searchButton){
//                try {
//                    //get IP
//                    String ipAddress = ipAddressTextField.getText();
//                    InetAddress inetAdd = InetAddress.getByName(ipAddress);
////                    engine.getOpponent(inetAdd);
//                    String ip = inetAdd.toString();
//                    System.out.println(ip);
//                } catch (UnknownHostException ex) {
//                    Logger.getLogger(GUI2dView.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            //JButton schlachtschiffButton;
//            //JButton kreuzerButton;
//            //JButton zerstörerButton;
//            //JButton uBootButton;
//            //1 x Schlachtschiff: 5 Kästchen
//            //2 x Kreuzer : 4 Kästchen
//            //3 x Zerstörer: 3 Kästchen
//            //4 x U-boot : 2 Kästchen
//            if (e.getSource()== schlachtschiffButton){
//            ship = new Ship("Schlachtschiff", 5);
//            }
//            if(e.getSource()== kreuzerButton){
//            ship = new Ship("Kreuzer", 4);
//            }
//            if(e.getSource()== zerstörerButton){
//            ship = new Ship("Zerstörer", 3);
//            }
//            if(e.getSource()== uBootButton){
//            ship = new Ship("U-Boot", 2);
//            }
//        }
//    }
//    private void updateShipNumbers(){
//            JLabel shipNumbersLabel = new JLabel("Schlachtschiffe: "+schlachtNumb + "   Kreuzer: "+kreuzerNumb+
//            "   Zerstörer: "+zerstörerNumb+"   U-Boote: "+uBootNumb);
//            north.add(shipNumbersLabel,BorderLayout.NORTH);
//    }
//}
