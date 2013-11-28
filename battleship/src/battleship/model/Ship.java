/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author nikodemus
 */
public class Ship {
    private String name;
    private int size;
    private boolean ship[];
    public enum Direction{VERTIVAL, HORIZONTAL};
    private Direction direction;
    
    public Ship(String name, int size)
    {
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
    
    
    
    
}
