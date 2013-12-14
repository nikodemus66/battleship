/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.model;

/**
 *
 * @author kraeki
 */
public class ExecutionState {
    public ExecutionState( )
    {
        success = null;
        shootState = null;
    }
    Boolean success;
    public void setSuccess( boolean success ) { this.success = success; }
    public boolean getSuccess( ) { return success; }

    ShootState shootState;
    public void setShootState( ShootState state ) { this.shootState = state; }
    public ShootState getShootState( ) { return shootState; }
}
