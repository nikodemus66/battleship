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
    private static final int MAX_SHIPS = 10;
    
    private View view;
    private Grid board; // Model
    private int shipCount; 

    public Engine( View v, Grid m )
    {
        this.view = v;
        this.board = m;
        System.out.println( "Engine: initialized" );
    }

    public void start( )
    {
      // TODO: ask user if he wants to play on network
        view.start( this );
        startGame( );
    }

    private void startGame()
    {
      // TODO: if all players are ready we can start
        System.out.println( "Engine: game started" );
        view.do_placeShip( ); // TODO: aslong as the player has ships left, we need to call this function
      // TODO: opponent's turn

        view.do_shoot( ); // TODO: check if shot
    }

    public Grid getGrid( )
    {
        return board;
    }

    public boolean playerReady()
    {
        if (shipCount < MAX_SHIPS)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean shoot( int x, int y )
    {
        if (board.getPoint(x, y).isAttacked() == false)
        {
            board.getPoint(x, y).shot();
            
            if (board.getPoint(x, y).getType() == Point.Type.SHIP)
            {
                view.update( );
                return true;
            }
            else
            {
                view.update( );
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean placeShip( Ship ship, int x, int y )
//    {
        Point points[] = new Point[ship.getSize()];
      
        if (ship.getDirection() == Ship.Direction.HORIZONTAL)
        {
            for (int i=0; i<ship.getSize(); i++)
            {
                if (board.getPoint(i, y).getType() == Point.Type.SHIP)
                {
                    return false;
                }
                else
                {
                    points[i] = board.getPoint(i, y);
                }
            }
        }
      
        else if (ship.getDirection() == Ship.Direction.VERTIVAL)
        {
            for (int i=0; i<ship.getSize(); i++)
            {
                if (board.getPoint(x, i).getType() == Point.Type.SHIP)
                {
                    return false;
                }
                else
                {
                    points[i] = board.getPoint(x, i);
                }
            }
        }
      
        for (int i=0; i<points.length; i++)
        {
            points[i].setType(Point.Type.SHIP);
        }

        shipCount++;
        
        return true;
    }
}
