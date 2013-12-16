/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.server;

import battleship.model.EngineState;
import battleship.communication.ClientConnection;
import battleship.model.Grid;

/**
 *
 * @author kraeki
 */
public class PlayerInformation
{
    private String name;
    private short connection;
    private ClientConnection client;
    private Grid grid;
    private boolean ready;
    private EngineState state;

    public String getName() {  return name; }
    public short getConnection() { return connection; }
    public ClientConnection getClient() { return client; }
    public Grid getGrid( ) { return grid; }
    public void setReady() { ready = true; }
    public boolean isReady() { return ready; }
    public void setState( EngineState s ) { state = s; }
    public EngineState getState( ) { return state; }

    PlayerInformation( String name, short connection, ClientConnection client )
    {
        this.name = name;
        this.connection = connection;
        this.client = client;
        this.grid = new Grid( 10, 10 );
        this.ready = false;
    }

}
