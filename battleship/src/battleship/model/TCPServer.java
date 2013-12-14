
package battleship.model;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer implements Runnable
{
  public final static int PORT = 5001;

  private ServerSocket serverSocket = null;
  private IServerListener listener = null;

  public TCPServer( IServerListener l ) throws IOException
  {
    listener = l;
  }

  public void bind( ) throws IOException
  {
    try {
      serverSocket = new ServerSocket( PORT );
    }
    catch (IOException e)
    {
      System.err.println("Could not listen on port: " + PORT);
    }
  }

  public void start( )
  {
    Thread t = new Thread( this );
    t.start( ); // start listening
  }


  @Override
  public void run( )
  {
    Logger.getGlobal( ).info ("Waiting for connection ...");
    while( true )
    {
      try {
        Socket clientSocket = serverSocket.accept();
        ClientConnection cc = new ClientConnection( clientSocket, listener );
        cc.start( );
        Logger.getGlobal( ).info ("Connection successful");
        listener.newConnection( cc );
      }
      catch (IOException e)
      {
        System.err.println("Accept failed.");
      }
    }
  }

  public void disconnect( ) throws IOException
  {
    serverSocket.close();
  }
}
