
package battleship.model;

import java.net.*;
import java.io.*;

public class TCPClient
{
  private Socket echoSocket = null;
  private PrintWriter out   = null;
  private BufferedReader in = null;

  private Listener listener;

  public TCPClient( Listener l )
  {
    listener = l;
  }

  public void connect( String server ) throws IOException
  {
      connect( server, 5001 ); // FIXME: use static variable for port
  }

  public void connect( String server, int port ) throws IOException
  {
    System.out.println ("Attemping to connect to host " + server + " on port " + port );

    try {
      echoSocket = new Socket( server, port );
      out = new PrintWriter( echoSocket.getOutputStream( ), true );
      in = new BufferedReader( new InputStreamReader( echoSocket.getInputStream( )));
    } catch (UnknownHostException e) {
      System.err.println( "Don't know about host: " + server );
    } catch ( IOException e ) {
      System.err.println( "Couldn't get I/O for " + "the connection to: " + server );
    }

    String message;
    while ((message = in.readLine()) != null)
    {
      listener.receive( message );
    }
  }

  public void send( String message )
  {
    out.println( message );
  }

  public void disconnect( ) throws IOException
  {
    out.close();
    in.close();
    echoSocket.close();
  }
}
