package com.netflexity.jms.mockup;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jms.JMSException;

public class MapMessage extends Message implements javax.jms.MapMessage {
	
	Map<String, Object> mapProperties = new HashMap<String, Object>();
	
	public MapMessage() {
	}
	
	public boolean getBoolean(String name) throws JMSException {
		return (Boolean) mapProperties.get(name);
	}

	public byte getByte(String name) throws JMSException {
		return (Byte) mapProperties.get(name);
	}

	public byte[] getBytes(String name) throws JMSException {
		return (byte[]) mapProperties.get(name);
	}

	public char getChar(String name) throws JMSException {
		return (Character) mapProperties.get(name);
	}

	public double getDouble(String name) throws JMSException {
		return (Double) mapProperties.get(name);
	}

	public float getFloat(String name) throws JMSException {
		return (Float) mapProperties.get(name);
	}

	public int getInt(String name) throws JMSException {
		return (Integer) mapProperties.get(name);
	}

	public long getLong(String name) throws JMSException {
		return (Long) mapProperties.get(name);
	}

	public Enumeration getMapNames() throws JMSException {
		return new Enumeration() {		
			Iterator<String> i = mapProperties.keySet().iterator();			
			public Object nextElement() {
				return i.next();
			}
			public boolean hasMoreElements() {
				return i.hasNext();
			}
		};
	}

	public Object getObject(String name) throws JMSException {
		return mapProperties.get(name);
	}

	public short getShort(String name) throws JMSException {
		return (Short) mapProperties.get(name);
	}

	public String getString(String name) throws JMSException {
		return (String) mapProperties.get(name);
	}

	public boolean itemExists(String name) throws JMSException {
		return mapProperties.get(name) != null;
	}

	public void setBoolean(String name, boolean value) throws JMSException {
		mapProperties.put(name, value);
	}

	public void setByte(String name, byte value) throws JMSException {
		mapProperties.put(name, value);
	}

	public void setBytes(String name, byte[] value) throws JMSException {
		mapProperties.put(name, value);
	}

	public void setBytes(String name, byte[] value, int offset, int length)
			throws JMSException {
		mapProperties.put(name, value);
	}

	public void setChar(String name, char value) throws JMSException {
		mapProperties.put(name, value);
	}

	public void setDouble(String name, double value) throws JMSException {
		mapProperties.put(name, value);
	}

	public void setFloat(String name, float value) throws JMSException {
		mapProperties.put(name, value);
	}

	public void setInt(String name, int value) throws JMSException {
		mapProperties.put(name, value);
	}

	public void setLong(String name, long value) throws JMSException {
		mapProperties.put(name, value);
	}

	public void setObject(String name, Object value) throws JMSException {
		mapProperties.put(name, value);
	}

	public void setShort(String name, short value) throws JMSException {
		mapProperties.put(name, value);
	}

	public void setString(String name, String value) throws JMSException {
		mapProperties.put(name, value);
	}

}
