/*
 * Diese Klasse ist für die Spiellogik verantwortlich
 * 
 */
package battleship;

/**
 *
 * @author nikodemus
 */
public class Engine {
    private boolean fields[][];
    private Ship ships[][];
    
    private void startGame()
    {
        
    }
    
    public boolean playerReady()
    {
        return false;
    }
    
    public boolean shoot(int x, int y)
    {
        return false;
    }
    
    public void placeShip(Ship ship, int x, int y)
    {
        if (ship != null)
        {
            ships[x][y] = ship;
        }
    }
}
