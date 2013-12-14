package battleship.model;

import battleship.util.Utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Message
{
  public final static short MAGIC = 0x4153;
  private final short connection;
  private final MessageType type;

//  ByteArrayOutputStream payload_out;
//  ByteArrayInputStream payload_in;
  private byte[] payload;

  public Message( MessageType type )
  {
      this.connection = Short.parseShort(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
      this.type = type;
      payload = new byte[0];
  }

  private Message( MessageType type, short connection, int payload_size )
  {
      this.connection = connection;
      this.type = type;
      this.payload = new byte[payload_size];
  }

  public short getConnection( )
  {
      return connection;
  }

  public MessageType getType( )
  {
      return type;
  }

  public DataInputStream getPayloadReader( )
  {
      //Logger.getGlobal().info( "Message: getPayloadReader " + payload.length );
      return new DataInputStream( new ByteArrayInputStream( payload ));
  }

  public byte[] getByteArray( )
  {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      DataOutputStream dos = new DataOutputStream( bos );
      dos.writeShort( MAGIC );
      dos.writeShort( connection );
      dos.writeShort( type.ordinal( ));
      dos.writeShort( payload.length);
    } catch (IOException ex) {
        Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
    }

    byte[] header = bos.toByteArray();
    byte[] combined = new byte[header.length + payload.length];

    for (int i = 0; i < combined.length; ++i)
    {
        combined[i] = i < header.length ? header[i] : payload[i - header.length];
    }
    return combined;
  }

//  public ByteArrayOutputStream getByteArrayOutputStream()
//  {
//      ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//      try {
//        DataOutputStream dos = new DataOutputStream( bos );
//        dos.writeShort( MAGIC );
//        dos.writeShort( connection );
//        dos.writeShort( type.ordinal( ));
//        dos.writeShort( payload_out.size( ));
//        payload_out.dos);
//      } catch (IOException ex) {
//          Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
//      }
//      return bos;
//  }

  public int getPayloadSize( )
  {
     // header + payload
     return payload.length;
  }

  public static Message createMessage( byte[] header )
  {
      ByteArrayInputStream bis = new ByteArrayInputStream(header);
      DataInputStream dis = new DataInputStream( bis );
      try {
          if( Message.MAGIC != dis.readShort())
              return null;

          short connection = dis.readShort();
          MessageType type = MessageType.values()[dis.readShort()];
          int payload_size = dis.readShort();

          return new Message(type, connection, payload_size);

      } catch (IOException ex) {
          Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
  }

  public void setPayload( byte[] data )
  {
      payload = data;
  }

  public String printMessage( )
  {
      return Utils.bytesToHex( getByteArray() );
  }
}
