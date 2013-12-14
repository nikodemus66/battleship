///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package battleship.view;
//
//import battleship.controller.Engine;
//import battleship.model.*;
//import battleship.model.Point;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//
///**
// *
// * @author Michael Zihlmann
// */
//public class Map extends JPanel implements MouseListener {
//
//    private int posx;
//    private int posy;
//    public static final int MAP_SIZE = 400;
//    private MouseListener listener;
//    private MouseEvent me;
//    private int[] coords;
//    private JPanel field;
//    private HumanPlayer player;
//    private Ship ship;
//
//    public Map() {
//
//        super(new GridLayout(10, 10));
//        setPreferredSize(new Dimension(MAP_SIZE, MAP_SIZE));
//        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
//        for (int i = 0; i < (10 * 10); i++) {
//            field = new JPanel();
//            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//            field.setBackground(Color.BLUE);
//            field.addMouseListener(new MouseListener() {
//
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    posx = 100 + e.getXOnScreen();
//                    posy = 100 + e.getYOnScreen();
//                    paint(posx, posy);
//                    System.out.println("Es wurde geklickt..");
//                }
//                @Override
//                public void mousePressed(MouseEvent e) {}
//                @Override
//                public void mouseReleased(MouseEvent e) {}
//                @Override
//                public void mouseEntered(MouseEvent e) {}
//                @Override
//                public void mouseExited(MouseEvent e) {}
//            });
//            field.setVisible(true);
//            this.add(field);
//
//        }
//
//    }
//
//    public int[] getCoords() {
//
//        listener.mouseClicked(me);
//        posy = (int) me.getY();
//        posx = (int) me.getX();
//        coords = new int[]{posx, posy};
//        System.out.println(coords);
//
//        return coords;
//    }
//
//    public void paint(int posx, int posy) {
//        for (int i = 0; i < (10 * 10); i++) {
//            field = new JPanel();
//            ship = (Ship) me.getSource();
//            field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//            field.addMouseListener(listener);
//            this.add(field);
//            getCoords();
//            if (player.placeShip(ship, posx, posy) == true) {
//                field.setBackground(Color.GRAY);
//            } else if (true == player.shoot(posx, posy)) {
//                field.setBackground(Color.RED);
//            } else if (false == player.shoot(posx, posy)) {
//                field.setBackground(Color.GREEN);
//            } else {
//                field.setBackground(Color.BLUE);
//            }
//
//        }
//
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        posx = 100 + e.getXOnScreen();
//                    posy = 100 + e.getYOnScreen();
//                    paint(posx, posy);
//                    System.out.println("Es wurde geklickt..");
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//}
