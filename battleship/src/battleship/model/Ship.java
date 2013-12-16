/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.model;

/**
 *
 * @author nikodemus
 */
public class Ship {

    public static final int LENGTH_TERMINATOR = 5;
    public static final int LENGTH_KILLER = 3;
    public static final int LENGTH_UBOOT = 2;
//    public static final int MAX_TERMINATOR = 1;
//    public static final int MAX_KILLER = 1;

    private ShipType type;
    private String name;
    private int x;
    private int y;

    private boolean vertical;

    public Ship( ShipType type, int x, int y, boolean vertical ) {
      this.type = type;
      this.name = type.name();
      this.x = x;
      this.y = y;
      this.vertical = vertical;
    }

    private int hits = 0;
    public void hit( )
    {
      hits++;
    }

    public boolean isAlive( )
    {
      return hits < getSize( );
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return Ship.getShipLength(this.type);
    }

    public boolean IsVertical() {
        return vertical;
    }

        public static int getShipLength(ShipType ship) {
        switch (ship) {
            case TERMINATOR:
                return LENGTH_TERMINATOR;
            case KILLER:
                return LENGTH_KILLER;
            case UBOOT:
                return LENGTH_UBOOT;
            default:
                assert (false);
                return 0;
        }
    }
//
//    public static int getMaxShips(ShipType ship) {
//        switch (ship) {
//            case TERMINATOR:
//                return MAX_TERMINATOR;
//            case KILLER:
//                return MAX_KILLER;
//            default:
//                assert (false);
//                return 0;
//        }
//    }


    // e.g. Ship Defender: xxxx
    @Override
    public String toString( )
    {
      String s = "";
      for( int i = 0; i < getSize(); i++ )
        s += "x";

      return "Ship " + name + ": " + s;
    }
}
