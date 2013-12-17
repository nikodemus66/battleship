package battleship.client;

import battleship.model.*;

/**
 * Graphical User Interface
 * @author brian
 */
public class GUIPlayer extends Player
{
    SelectOptionJFrame optionFrame;
    PlacingShipJFrame placingShipFrame;
    
    public GUIPlayer( String name )
    {
        super( name );
        optionFrame = new SelectOptionJFrame( this );
        optionFrame.setVisible(true);   
    }

    @Override
    protected void stateChanged(EngineState state) {
        switch( state )
        {
            case SELECTING_OPPONEND:
                break;
            case PREPARING_GRID:
                optionFrame.setVisible(false);   
                placingShipFrame = new PlacingShipJFrame( this );
                placingShipFrame.setVisible(true);     
                break;
            case PLAY:
                placingShipFrame.setVisible(false);     
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
