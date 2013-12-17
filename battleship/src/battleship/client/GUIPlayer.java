package battleship.client;

import battleship.model.*;

/**
 * Graphical User Interface
 * @author brian
 */
public class GUIPlayer extends Player
{
    
    public GUIPlayer( String name )
    {
        super( name );
        SelectOptionJFrame frame = new SelectOptionJFrame( this );
        frame.setVisible(true);
        
        PlacingShipJFrame pFrame = new PlacingShipJFrame( this );
        pFrame.setVisible(true);
        
        
        
        
    }

    @Override
    protected void stateChanged(EngineState state) {
        switch( state )
        {
            case SELECTING_OPPONEND:
                break;
            case PREPARING_GRID:
                break;
            case PLAY:
                break;
            case YOUR_TURN:
                break;
            case OPPONENDS_TURN:
                break;
            case YOU_LOST:
                break;
            case YOU_WON:
                break;
            case FINISHED:
                break;
            default:
                throw new AssertionError(state.name());
            
        }
    }

    @Override
    protected void update(ShootState[][] myBoard, ShootState[][] opponendBoard) {
        
    }
    
}
