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

    private String name;
    private int size;
    private boolean ship[];
    public enum Direction{ VERTICAL, HORIZONTAL };
    private Direction direction;
    
    public Ship( String name, int size )
    {
        direction = Direction.VERTICAL;
        this.name = name;
        this.size = size;
        ship = new boolean[size];
    }
    
    public Ship( String name, int size, Direction direction )
    {
        this.direction = direction;
        this.name = name;
        this.size = size;
        ship = new boolean[size];
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public boolean[] getShip() {
        return ship;
    }

    public Direction getDirection() {
        return direction;
    }


    // e.g. Ship Defender: xxxx
    public String toString( )
    {
      String s = "";
      for( int i = 0; i < size; i++ )
        s += "x";

      return "Ship " + name + ": " + s;
    }
}
