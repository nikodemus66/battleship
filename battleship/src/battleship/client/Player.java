/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.client;

import battleship.model.ExecutionState;
import battleship.model.EngineState;
import battleship.model.ShipType;
import battleship.communication.IClientListener;
import battleship.communication.Message;
import battleship.communication.MessageType;
import battleship.model.Ship;
import battleship.model.ShootState;
import battleship.communication.TCPClient;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikodemus
 */
public abstract class Player implements IClientListener
{
  private TCPClient client = null;

  private String name;
  public String getName() { return name; }

  private ShootState[][] myBoard;
  private ShootState[][] opponendBoard;
  
  public ShootState[][] getMyBoard( ){ return myBoard; }
  public ShootState[][] getOpponendBoard( ){ return opponendBoard; }

  private static ArrayList<ShipType> ships = new ArrayList<ShipType>( )
    {{
      //add( ShipType.TERMINATOR );
      //add( ShipType.KILLER );
      add( ShipType.UBOOT );
    }};

   public static ArrayList<ShipType> getShips( )
   {
       return new ArrayList( ships );
   }

  public Player( String name )
  {
    this.name = name;
    // FIXME: refactor
    myBoard = new ShootState[10][10];
    opponendBoard = new ShootState[10][10];
    for( int x = 0; x < myBoard.length; x++ )
      for( int y = 0 ; y < myBoard[x].length; y++ )
      {
        myBoard[x][y] = ShootState.WATER;
        opponendBoard[x][y] = ShootState.WATER;
      }
  }

  public void connect( String ip )
  {
    try
    {
      client = new TCPClient( this );
      client.connect( ip );
      client.start( ); // start listening

      Message m = new Message( MessageType.REGISTER );
      ByteArrayOutputStream baos = new ByteArrayOutputStream( );
      DataOutputStream payload = new DataOutputStream( baos );
      payload.writeByte(this.name.length( ));
      payload.write(this.name.getBytes( ));
      m.setPayload(baos.toByteArray( ));

      Logger.getGlobal( ).info( "Player: sending message: " + m.getType().name( ));
      client.send( m );
      Logger.getGlobal( ).info( "Player: sent message: " + m.getType().name( ));
    }
    catch( IOException e)
    {
      Logger.getGlobal( ).info( "NetworkPlayerClient: init error:" + e );
    }
  }

  private ExecutionState execution;
  protected boolean placeShip( ShipType type , int x, int y, boolean vertical )
  {
    execution = new ExecutionState( );
    try
    {
      Message m = new Message( MessageType.PLACE_SHIP );
      ByteArrayOutputStream baos = new ByteArrayOutputStream( );
      DataOutputStream payload = new DataOutputStream( baos );

      payload.writeByte( type.ordinal( ));
      payload.writeByte( x );
      payload.writeByte( y );
      payload.writeBoolean( vertical );

      m.setPayload(baos.toByteArray( ));

      client.send( m );
      synchronized( execution )
      {
          try {
            Logger.getGlobal( ).info( "Player "+this.name+": waiting for response" );
            execution.wait( );
            Logger.getGlobal( ).info( "Player "+this.name+": response received" );
              }
          catch (InterruptedException ex) {
              Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
    }
    catch( IOException e)
    {
      Logger.getGlobal( ).info( "Player: placeShip error:" + e );
    }
    if( execution.getSuccess( ))
    {
      if( vertical )
        for( int i = y; i < Ship.getShipLength( type ) + y; i++ )
          myBoard[x][i] = ShootState.SHIP;
      else
        for( int i = x; i < Ship.getShipLength( type ) + x; i++ )
          myBoard[i][y] = ShootState.SHIP;
      this.update(myBoard, opponendBoard);
    }
    return execution.getSuccess( );
  }

  protected void playerReady( )
  {
      Message m = new Message( MessageType.PLAYER_READY );
      client.send( m );
  }


  protected ShootState shoot( int x, int y )
  {
    try
    {
      Message m = new Message( MessageType.SHOOT );
      ByteArrayOutputStream baos = new ByteArrayOutputStream( );
      DataOutputStream payload = new DataOutputStream( baos );

      payload.writeByte( x );
      payload.writeByte( y );

      m.setPayload(baos.toByteArray( ));

      client.send( m );
      synchronized( execution )
      {
          try {
            execution.wait( );
              }
          catch (InterruptedException ex) {
              Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
    }
    catch( IOException e)
    {
      Logger.getGlobal( ).info( "Player: placeShip error:" + e );
    }
    ShootState st = execution.getShootState( );
    if( st != ShootState.NOT_POSSIBLE )
    {
      this.opponendBoard[x][y] = st;
      this.update(myBoard, opponendBoard);
    }
    return st;
  }

  @Override
  public final void receive(Message message)
  {
    Logger.getGlobal( ).info( "Player "+name+": received message " + message.getType().name() + ": " + message.printMessage());
    DataInputStream payload = message.getPayloadReader();
    switch( message.getType( ))
    {
      case STATE_CHANGE:
        try {
          this.stateChanged(EngineState.values()[payload.readByte()]);
        } catch (IOException ex) {
          Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        break;
      case PLACE_SHIP_RESPONSE:
        {
          synchronized( execution )
          {
              try {
                execution.setSuccess(payload.readBoolean( ));
              } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
              }
              execution.notify();
          }
        }
        break;
      case SHOOT_RESPONSE:
      {
        synchronized( execution )
        {
            try {
              execution.setSuccess(payload.readBoolean( ));
              execution.setShootState( ShootState.values()[payload.readUnsignedByte()]);
            } catch (IOException ex) {
              Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            execution.notify();
        }
      }
      break;
      case OPPONEND_SHOOT:
      {
          int x = 0;
          int y = 0;
          ShootState st = null;
            try {
                x = payload.readByte();
                y = payload.readByte();
                st = ShootState.values()[payload.readByte()];

            } catch (IOException ex) {
              Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);

            }

        this.myBoard[x][y] = st;
        this.update( myBoard, opponendBoard );
      }
      break;
    }
  }

  protected void update( )
  {
      update(myBoard, opponendBoard);
  }

  protected abstract void stateChanged( EngineState state );
  protected abstract void update( ShootState[][] myBoard, ShootState[][] opponendBoard  );










































  //  private Grid board = new Grid( 10,10 );
  //  protected int shipCount = 0;
  //  private boolean ready = false;
  //  private boolean shouldShoot = false;
  //  protected Engine engine;
  //
  //  public Player( )
  //  {
  //  }
  //
  //  public void init( Engine e )
  //  {
  //    engine = e;
  //    init( );
  //  }
  //
  //  public void event( EngineEvent e )
  //  {
  //  }
  //
  //  public void setReady( )
  //  {
  //    ready = true;
  //  }
  //
  //  public boolean shouldShoot( )
  //  {
  //    return shouldShoot;
  //  }
  //
  //  public boolean isReady( )
  //  {
  //    return ready;
  //  }
  //
  //  public Grid getGrid( )
  //  {
  //    return board;
  //  }
  //
  //  public int getShipCount()
  //  {
  //      return shipCount;
  //  }
  //
  //  public void incrementShipCount()
  //  {
  //      this.shipCount++;
  //  }
  //
  //  // engine calls these functions
  //
  //  //public abstract void do_setup( Engine e );
  //  public abstract void init( );
  //  public abstract void startingGame( );
  //  public abstract void do_update( );
  //  public abstract void yourTurn( );
  //  public abstract void youLost( );
  //  public abstract void youWon( );
}
