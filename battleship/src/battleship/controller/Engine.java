/*
 * Diese Klasse ist für die Spiellogik verantwortlich
 *
 */
package battleship.controller;

import battleship.model.*;
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
public class Engine implements Runnable, IServerListener
{
  private boolean up = false;
  private Thread thread;

  private EngineState state;

  private TCPServer server;

  public Engine() {
    this.state = EngineState.SELECTING_OPPONEND;
  }

  public void start( )
  {
    if( up )
      return;

    try {
      server = new TCPServer( this ); // overgive as listener
      server.bind();
    } catch (IOException ex) {
      Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
      Logger.getGlobal( ).info( "Engine: error:" + ex );
    }

    up = true;
    thread = new Thread( this );
    thread.start( );
  }

  public void stop( )
  {
    up = false;
    try {
      thread.join( );
    } catch (InterruptedException ex) {
      Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
    }
  }


  @Override
  public void run( )
  {
    server.start( );
  }


  private PlayerInformation[] players = new PlayerInformation[2];

  @Override
  public void newConnection( ClientConnection client )
  {
    Logger.getGlobal( ).info( "Engine: got new connection ");
  }

  @Override
  public void receive( ClientConnection client, Message message )
  {
    Logger.getGlobal( ).info( "Engine: got message: " + message.getType().toString( ) + " | " + message.printMessage( ));
    PlayerInformation player = getPlayerInformation( client );
    switch( message.getType( ))
    {
      case REGISTER:
        String name = "";
        try {
          DataInputStream payload =  message.getPayloadReader( );
          int length = payload.readUnsignedByte();
          byte[]tmp = new byte[length];
          payload.read(tmp);
          name = new String(tmp, "UTF-8");
        } catch (IOException ex) {
          Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }


        // TODO: synchronized block, registering player
        synchronized( players )
        {
          Logger.getGlobal( ).info( "Engine: registering player " + name + ": " + message.getConnection( ) );
          if( players[0] == null )
          {
            players[0] = new PlayerInformation( name, message.getConnection(), client);

          }
          else if( players[1] == null )
          {
            players[1] = new PlayerInformation( name, message.getConnection(), client);
            state = EngineState.PREPARING_GRID;

            Message m = new Message( MessageType.STATE_CHANGE);
            try {
              ByteArrayOutputStream baos = new ByteArrayOutputStream( );
              DataOutputStream payload = new DataOutputStream( baos );

              payload.writeByte( state.ordinal( ));
              m.setPayload(baos.toByteArray( ));

              players[0].getClient().send(m);
              players[1].getClient().send(m);

            } catch (IOException ex) {
              Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
          else
          {
            // refuse connection and close it
            client.close();
          }
        }
        //
        break;
      case PLACE_SHIP:
        {
          if( state != EngineState.PREPARING_GRID )
          {
            Logger.getGlobal( ).info( "Engine: player " + player.getName() + " cannot place ship, engine state is: " + state.name( ));
            return;
          }

          boolean success = false;
          try {
            DataInputStream payload =  message.getPayloadReader( );
            ShipType shipType = ShipType.values()[payload.readUnsignedByte()];
            int x = payload.readUnsignedByte();
            int y = payload.readUnsignedByte();
            boolean vertical = payload.readBoolean();

            if( player.getGrid( ).placeShip( shipType, x, y, vertical ))
            {
              Logger.getGlobal( ).info( "Engine: player " + player.getName() + " placed ship at " + x + " " + y );
              //sendGood;
              success = true;
            }
            else
            {
              Logger.getGlobal( ).info( "Engine: player " + player.getName() + " cannot place ship at " + x + " " + y );
              //sendError;
              success = false;
            }


          } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
          }

          // send PLACE_SHIP_RESPONSE
          Message m = new Message( MessageType.PLACE_SHIP_RESPONSE);
          try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream( );
            DataOutputStream payload = new DataOutputStream( baos );

            payload.writeBoolean( success );
            m.setPayload(baos.toByteArray( ));

            player.getClient().send(m);

          } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        break;
      case PLAYER_READY:
        {
          if( state != EngineState.PREPARING_GRID)
            return;

          player.setReady();
          if( !players[0].isReady( ) || !players[1].isReady( ))
          {
            return;
          }

          // send STATE PLAY
          state = EngineState.PLAY;
          Message m = new Message( MessageType.STATE_CHANGE);
          try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream( );
            DataOutputStream payload = new DataOutputStream( baos );

            payload.writeByte( state.ordinal( ));
            m.setPayload(baos.toByteArray( ));

            players[0].getClient().send(m);
            players[1].getClient().send(m);

          } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
          }

          // send TURN
          sendTurn( player );

        }
        break;
      case SHOOT:
        {
          synchronized( this )
          {
            boolean success = false;
            ShootState shot = ShootState.NOT_POSSIBLE;
            int x = 0;
            int y = 0;
            PlayerInformation opponend = getOpponend(player);

            if( state == EngineState.PLAY && player.getState( ) == EngineState.YOUR_TURN )
            {
              try {
                DataInputStream payload =  message.getPayloadReader( );
                x = payload.readUnsignedByte();
                y = payload.readUnsignedByte();


                shot = opponend.getGrid( ).shoot( x, y );
                Logger.getGlobal( ).info( "Engine: player " + player.getName() + " shot at " + x + " " + y + " and got " + shot.name( ));
                success = true;

              } catch (IOException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                success = false;
              }
            }

            // inform other opponend
            if( success )
            {
              Message m = new Message( MessageType.OPPONEND_SHOOT );
              try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream( );
                DataOutputStream payload = new DataOutputStream( baos );

                payload.writeByte( x );
                payload.writeByte( y );
                payload.writeByte( shot.ordinal( ));

                m.setPayload(baos.toByteArray( ));
                opponend.getClient().send(m);

              } catch (IOException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
              }
            }

            // send SHOOT_RESPONSE
            Message m = new Message( MessageType.SHOOT_RESPONSE );
            try {
              ByteArrayOutputStream baos = new ByteArrayOutputStream( );
              DataOutputStream payload = new DataOutputStream( baos );

              payload.writeBoolean( success );
              payload.writeByte( shot.ordinal( ));
              m.setPayload(baos.toByteArray( ));

              player.getClient().send(m);

            } catch (IOException ex) {
              Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }

            // check if gameover
            if( opponend.getGrid().allShipDead( ))
            {
              m = new Message( MessageType.STATE_CHANGE );
              try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream( );
                DataOutputStream payload = new DataOutputStream( baos );

                // send player
                payload.writeByte( EngineState.YOU_WON.ordinal( ));
                m.setPayload(baos.toByteArray( ));
                player.getClient().send( m );

                baos.reset();

                // send opponend
                payload.writeByte( EngineState.YOU_LOST.ordinal( ));
                m.setPayload(baos.toByteArray( ));
                opponend.getClient().send( m );

              } catch (IOException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
              }

              state = EngineState.FINISHED;
            }
            else
            {
              // if missed then change turn
              if( shot == ShootState.MISS )
                sendTurn( opponend );
              else
                sendTurn(player);
            }
          }
        }
        break;
      case RESTART:
        break;
      default:
        Logger.getGlobal( ).info( "Engine: got unknown message: " + message  );
    }
  }

  private void sendTurn( PlayerInformation player )
  {
    PlayerInformation opponend = getOpponend(player);

    opponend.setState( EngineState.OPPONENDS_TURN);
    player.setState( EngineState.YOUR_TURN);

    Message m = new Message( MessageType.STATE_CHANGE);
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream( );
      DataOutputStream payload = new DataOutputStream( baos );

      // send opponend
      payload.writeByte( EngineState.OPPONENDS_TURN.ordinal( ));
      m.setPayload(baos.toByteArray( ));
      opponend.getClient().send(m);

      baos.reset();

      // send player
      payload.writeByte( EngineState.YOUR_TURN.ordinal( ));
      m.setPayload(baos.toByteArray( ));
      player.getClient().send(m);

    } catch (IOException ex) {
      Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private PlayerInformation getPlayerInformation( ClientConnection cc )
  {
    if( players[0] != null && players[0].getClient() == cc )
    {
      return players[0];
    }
    else if( players[1] != null && players[1].getClient() == cc )
    {
      return players[1];
    }
    return null;
  }

  private PlayerInformation getOpponend( PlayerInformation p )
  {
    if( players[0] == p )
      return players[1];
    else
      return players[0];
  }
}
