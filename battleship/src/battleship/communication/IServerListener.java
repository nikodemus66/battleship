/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.communication;

import java.net.Socket;

/**
 *
 * @author kraeki
 */
public interface IServerListener 
{
    void receive( ClientConnection client, Message message );
    void newConnection( ClientConnection client );
}
