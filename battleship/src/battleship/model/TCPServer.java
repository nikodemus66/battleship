
package battleship.model;

import java.net.*;
import java.io.*;

public class TCPServer implements Runnable
{
  public final static int PORT = 5001;

  private ServerSocket serverSocket = null;
  private Socket clientSocket = null;
  private PrintWriter out   = null;
  private BufferedReader in = null;
  private Listener listener = null;

  public TCPServer( Listener l ) throws IOException
  {
    listener = l;
  }

  public void connect( ) throws IOException
  {
    try {
      serverSocket = new ServerSocket( PORT );
    }
    catch (IOException e)
    {
      System.err.println("Could not listen on port: " + PORT);
    }

    System.out.println ("Waiting for connection.....");

    try {
      clientSocket = serverSocket.accept();
    }
    catch (IOException e)
    {
      System.err.println("Accept failed.");
    }

    System.out.println ("Connection successful");
    System.out.println ("Waiting for input.....");

    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream()));

    send( "Hallo from Server");
  }

  public void start( )
  {
    Thread t = new Thread( this );
    t.start( ); // start listening
  }

  public void run( )
  {
    try {
      String message;
      while ((message = in.readLine()) != null)
      {
        listener.receive( message );
        if (message.equals("Bye."))
          break;
      }
    }
    catch ( Exception e ) {}
  }

  public void send( String message )
  {
    out.println( message );
  }

  public void disconnect( ) throws IOException
  {
    out.close();
    in.close();
    clientSocket.close();
    serverSocket.close();
  }
}
