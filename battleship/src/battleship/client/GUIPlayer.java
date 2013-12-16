package battleship.client;

import battleship.model.*;


public class GUIPlayer extends Player
{
    
    public GUIPlayer( String name )
    {
        super( name );
    }

    @Override
    protected void stateChanged(EngineState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void update(ShootState[][] myBoard, ShootState[][] opponendBoard) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
