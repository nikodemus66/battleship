
package battleship.communication;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient implements Runnable
{
  private Socket echoSocket = null;
  private BufferedReader in = null;

  private final IClientListener listener;

  public TCPClient( IClientListener l )
  {
    listener = l;
  }

  public void connect( String server ) throws IOException
  {
    connect( server, 5001 ); // FIXME: use static variable for port
  }

  public void connect( String server, int port ) throws IOException
  {
    Logger.getGlobal( ).info ("Connect to host " + server + " on port " + port );

    try {
      echoSocket = new Socket( server, port );
      in = new BufferedReader( new InputStreamReader( echoSocket.getInputStream( )));
    } catch (UnknownHostException e) {
      System.err.println( "Don't know about host: " + server );
    } catch ( IOException e ) {
      System.err.println( "Couldn't get I/O for " + "the connection to: " + server );
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
    BufferedInputStream bis = null;
    try {
      bis = new BufferedInputStream(echoSocket.getInputStream());
    } catch (IOException ex) {
      Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
    }

    while (true)
    {
      byte[] data = new byte[8];
      int bytesRead = 0;
      try {
        bytesRead = bis.read(data);
      } catch (IOException ex) {
        Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
      }
      if( bytesRead != data.length )
      {
        Logger.getGlobal().log( Level.SEVERE, "TCPClient: short read {0}/{1}", new Object[]{bytesRead, data.length});
        continue;
      }
      final Message m = Message.createMessage(data);
      if( m == null )
        continue;

      data = new byte[m.getPayloadSize()];
      try {
        bytesRead = bis.read(data);
      } catch (IOException ex) {
        Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
      }
      if( bytesRead != data.length )
      {
        Logger.getGlobal().log( Level.SEVERE, "TCPClient: short read {0}/{1}", new Object[]{bytesRead, data.length});
        continue;
      }
      m.setPayload(data );

      new Thread( new Runnable( ){
          @Override
          public void run()
          {
            listener.receive( m );
          }
      }).start();
    }
  }

  public void send( Message m )
  {
    try {
      //Logger.getGlobal().info( "TCPClient: sending " + m.getByteArray( ).length );
      echoSocket.getOutputStream().write(m.getByteArray( ));
    } catch (IOException ex) {
      Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void disconnect( ) throws IOException
  {
    in.close();
    echoSocket.close();
  }
}
