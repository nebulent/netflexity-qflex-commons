package com.netflexity.jms.mockup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.MessageEOFException;
import javax.jms.MessageFormatException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BytesMessage extends Message implements javax.jms.BytesMessage {

	   // Static -------------------------------------------------------

	   private static final long serialVersionUID = 5914561890366707664L;

	   private static final Log log = LogFactory.getLog(BytesMessage.class);

	   public static final byte TYPE = 4;

	   // Attributes ----------------------------------------------------

	   private boolean trace = log.isTraceEnabled();
	   
	   private transient ByteArrayOutputStream baos;
	   private transient DataOutputStream dos;

	   private transient ByteArrayInputStream bais;
	   private transient DataInputStream dis;
	   
		protected transient Object payload;

		protected byte[] payloadAsByteArray;

	   // Constructor ---------------------------------------------------

	   /**
	    * Only deserialization should use this constructor directly
	    */
	   public BytesMessage()
	   {   
	   }
	   

	   // BytesMessage implementation -----------------------------------

	   public boolean readBoolean() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readBoolean();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
	         throw new JMSException("IOException", "");
	      }
	   }

	   public byte readByte() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readByte();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public int readUnsignedByte() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readUnsignedByte();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public short readShort() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readShort();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public int readUnsignedShort() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readUnsignedShort();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public char readChar() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readChar();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public int readInt() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readInt();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public long readLong() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readLong();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public float readFloat() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readFloat();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public double readDouble() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readDouble();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public String readUTF() throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.readUTF();
	      }
	      catch (EOFException e)
	      {
	         throw new MessageEOFException("");
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public int readBytes(byte[] value) throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.read(value);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public int readBytes(byte[] value, int length) throws JMSException
	   {
	      checkRead();
	      try
	      {
	         return dis.read(value, 0, length);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeBoolean(boolean value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.writeBoolean(value);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeByte(byte value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.writeByte(value);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeShort(short value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.writeShort(value);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeChar(char value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.writeChar(value);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeInt(int value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.writeInt(value);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeLong(long value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.writeLong(value);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeFloat(float value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.writeFloat(value);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeDouble(double value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.writeDouble(value);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeUTF(String value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.writeUTF((String)value);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeBytes(byte[] value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.write(value, 0, value.length);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeBytes(byte[] value, int offset, int length) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         dos.write(value, offset, length);
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void writeObject(Object value) throws JMSException
	   {
	   	checkWrite();
	      try
	      {
	         if (value == null)
	         {
	            throw new NullPointerException("Attempt to write a new value");
	         }
	         if (value instanceof String)
	         {
	            dos.writeUTF((String)value);
	         }
	         else if (value instanceof Boolean)
	         {
	            dos.writeBoolean(((Boolean) value).booleanValue());
	         }
	         else if (value instanceof Byte)
	         {
	            dos.writeByte(((Byte) value).byteValue());
	         }
	         else if (value instanceof Short)
	         {
	            dos.writeShort(((Short) value).shortValue());
	         }
	         else if (value instanceof Integer)
	         {
	            dos.writeInt(((Integer) value).intValue());
	         }
	         else if (value instanceof Long)
	         {
	            dos.writeLong(((Long) value).longValue());
	         }
	         else if (value instanceof Float)
	         {
	            dos.writeFloat(((Float) value).floatValue());
	         }
	         else if (value instanceof Double)
	         {
	            dos.writeDouble(((Double) value).doubleValue());
	         }
	         else if (value instanceof byte[])
	         {
	            dos.write((byte[]) value, 0, ((byte[]) value).length);
	         }
	         else
	         {
	            throw new MessageFormatException("Invalid object for properties");
	         }
	      }
	      catch (IOException e)
	      {
		         throw new JMSException("IOException", "");
	      }
	   }

	   public void reset() throws JMSException
	   {
	      try
	      {
	         if (baos != null)
	         {
	            dos.flush();
	            
	            payload = baos.toByteArray();
	            
	            payloadAsByteArray = (byte[])payload;

	            baos.close();
	         }
	         baos = null;
	         bais = null;
	         dis = null;
	         dos = null;      
	      }
	      catch (Exception e)
	      {
	         JMSException e2 = new JMSException(e.getMessage());
	         e2.setStackTrace(e.getStackTrace());
	         throw e2;
	      }
	   }



	   public void clearBody() throws JMSException
	   {
	      try
	      {
	         if (baos != null)
	         {
	            baos.close();
	         }
	         else
	         {
	            if (bais != null)
	            {
	               bais.close();
	            }
	         }
	      }
	      catch (IOException e)
	      {
	         //don't throw an exception
	      }

	      baos = new ByteArrayOutputStream();
	      dos = new DataOutputStream(baos);
	      payload = null;
	      payloadAsByteArray = null;
	      bais = null;
	      dis = null;
	   }

	   public long getBodyLength() throws JMSException
	   {
	      checkRead();
	      return payloadAsByteArray.length;
	   }

	   void checkRead()
	   {   
	      // We have just received/reset() the message, and the client is trying to
	      // read it
	      if (bais == null)
	      {
	         bais = new ByteArrayInputStream(payloadAsByteArray);
	         dis = new DataInputStream(bais);
	      }
	   }
	   
	   void checkWrite()
	   {
	   	if (baos == null)
	   	{
	         baos = new ByteArrayOutputStream();
	         dos = new DataOutputStream(baos);
	   	}
	   }

}
