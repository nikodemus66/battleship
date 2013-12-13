
package battleship.model;

import battleship.controller.*;
import battleship.model.*;
import battleship.view.*;
import java.io.IOException;

public class NetworkPlayer extends Player implements Listener
{
  private Engine engine;
  private TCPClient client = null;
  private TCPServer server = null;

  public NetworkPlayer( ) throws IOException // I am server
  {
    server = new TCPServer( this ); // overgive as listener
    server.connect( );
    server.start( ); // start listening
  }

  public NetworkPlayer( String ip ) throws IOException // I am client
  {
    client = new TCPClient( this );
    client.connect( ip );
    client.start( ); // start listening
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
}
