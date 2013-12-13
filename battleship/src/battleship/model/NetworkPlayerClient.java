
package battleship.model;

import battleship.controller.*;
import battleship.model.*;
import battleship.view.*;
import java.io.IOException;

public class NetworkPlayerClient extends Player implements Listener
{
  private Engine engine;
  private TCPClient client = null;
  private TCPServer server = null;
  private String ip;


  public NetworkPlayerClient( String ip ) throws IOException // I am client
  {
    this.ip = ip;
  }
  
  public void init( )
  {
      try
      {
    client = new TCPClient( this );
    client.connect( ip );
    client.start( ); // start listening
      }
      catch( Exception e)
      {
          System.out.println( "NetworkPlayerClient: init error:" + e );
      }
  }

  public void do_setup( Engine engine )
  {
    this.engine = engine;
  }

  public void receive( String message )
  {
    System.out.println( "NetworkPlayer: receive: " + message );
  }

  public void send( String message )
  {
    if( client == null )
      server.send( message );
    else
      client.send( message );
  }

  public void do_start( )
  {
    this.send( "NetworkPlayer:do_start" );
  }

  public void do_placeShip( )
  {
    this.send( "NetworkPlayer:do_placeShip" );
  }

  public void changingPlayer( )
  {
  }

  public void do_shoot( )
  {
  }

  public void do_update( )
  {
  }

  public void youLost( )
  {
  }

  public void youWon( )
  {
  }
  public void yourTurn( )
  {
      
  }

    @Override
    public void startingGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
