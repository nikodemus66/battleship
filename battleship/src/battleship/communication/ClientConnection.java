/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.communication;

import static battleship.util.Utils.bytesToHex;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kraeki
 */
public class ClientConnection implements Runnable
{
  private final Socket clientSocket;
  private Thread thread;
  private final IServerListener listener;
  private Queue<Message> queue;
  private boolean up = false;

  public ClientConnection( Socket clientSocket, IServerListener listener )
  {
    this.clientSocket = clientSocket;
    this.listener = listener;
    this.queue = new ArrayBlockingQueue<Message>( 1000, true );
  }

  public void start( )
  {
    up = true;
    thread = new Thread( this );
    thread.start( );

    new Thread( new Runnable( ){
      public void run( )
      {
         handleMessage( );
      }
    }).start( );
  }

  public void run( )
  {
    Logger.getGlobal( ).info ("ClientSocket: waiting for input ...");

    BufferedInputStream bis = null;
    try {
      bis = new BufferedInputStream(clientSocket.getInputStream());
    } catch (IOException ex) {
      Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
    }

    while (true)
    {
      byte[] data = new byte[8];
      int bytesRead = 0;
      try {
        bytesRead = bis.read(data);
        //Logger.getGlobal().info( "ClientConnection: read header: " + data );
      } catch (IOException ex) {
        Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
      }
      if( bytesRead != data.length )
      {
        Logger.getGlobal().log( Level.SEVERE, "TCPClient: short read {0}/{1}", new Object[]{bytesRead, data.length});
        continue;
      }

      //Logger.getGlobal().info( "ClientConnection: create Message ");
      Message m = Message.createMessage( data );
      if( m == null )
        continue;


      //Logger.getGlobal().info( "ClientConnection: payload size: " + m.getPayloadSize( ));
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

      //Logger.getGlobal().info( "ClientConnection: create message payload: " + Utils.bytesToHex( data ));
      m.setPayload( data );
      synchronized( this )
      {
        System.out.println( "ClientConnection: put message into queue" );
        queue.add( m );
        this.notify( );
      }
    }
  }

  public void handleMessage( ) // thread
  {
    while( up )
    {
      synchronized( this )
      {
        if( !queue.isEmpty( ))
        {
            System.out.println( "ClientConnection: receive message" );
            listener.receive( this, queue.poll( ));
        }
        else
        {
          try {
            System.out.println( "ClientConnection: going to sleep" );
            this.wait( );
            System.out.println( "ClientConnection: wake up" );
          } catch (InterruptedException ex) {
              Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      }
    }
  }

  public void send( Message m )
  {
    try {
      Logger.getGlobal().info( "Engine: sending message " + m.getType().name() + ": payload " + bytesToHex(m.getByteArray( )));
      clientSocket.getOutputStream().write(m.getByteArray( ));
    } catch (IOException ex) {
      Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  public void close( )
  {
    try {
      clientSocket.close( );
    } catch (IOException ex) {
      Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
