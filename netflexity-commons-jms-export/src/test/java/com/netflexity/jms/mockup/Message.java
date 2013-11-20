package com.netflexity.jms.mockup;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;

import org.apache.commons.lang.NotImplementedException;

public class Message implements javax.jms.Message {

	Map<String, Object> properties = new HashMap<String, Object>();
	private int JMSDeliveryMode;
	private Destination JMSDestination;
	private long JMSExpiration;
	private int JMSPriority;
	private boolean JMSRedelivered;
	private Destination JMSReplyTo;
	private long JMSTimestamp;
	private String JMSType = "mytype";
	private String JMSCorrelationID = "123-345";
	private String JMSMessageID = "messageid";

	
	public Message() {
		//boolean, byte, short, int, long, float, double, and String.
		properties.put("boolean", new Boolean("true"));
		properties.put("byte", new Byte(Byte.MAX_VALUE));
		properties.put("short", new Short(Short.MAX_VALUE));
		properties.put("int", new Integer(Integer.MAX_VALUE));
		properties.put("long", new Long(Long.MAX_VALUE));
		properties.put("float", new Float(12.5));
		properties.put("double", new Double(12.5));
		properties.put("string", "test");
	}
	
	public void acknowledge() throws JMSException {
		throw new NotImplementedException();
		
	}

	public void clearBody() throws JMSException {
		throw new NotImplementedException();
		
	}

	public void clearProperties() throws JMSException {
		throw new NotImplementedException();
	}

	public boolean getBooleanProperty(String arg0) throws JMSException {
		return false;
	}

	public byte getByteProperty(String arg0) throws JMSException {
		return 0;
	}

	public double getDoubleProperty(String arg0) throws JMSException {
		return 0;
	}

	public float getFloatProperty(String arg0) throws JMSException {
		return 0;
	}

	public int getIntProperty(String arg0) throws JMSException {
		return 0;
	}

	public String getJMSCorrelationID() throws JMSException {
		return JMSCorrelationID;
	}

	public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
		return JMSCorrelationID.getBytes();
	}

	public int getJMSDeliveryMode() throws JMSException {
		return JMSDeliveryMode;
	}

	public Destination getJMSDestination() throws JMSException {
		return JMSDestination;
	}

	public long getJMSExpiration() throws JMSException {
		return JMSExpiration;
	}

	public String getJMSMessageID() throws JMSException {
		return JMSMessageID ;
	}

	public int getJMSPriority() throws JMSException {
		return JMSPriority;
	}

	public boolean getJMSRedelivered() throws JMSException {
		return JMSRedelivered;
	}

	public Destination getJMSReplyTo() throws JMSException {
		return JMSReplyTo;
	}

	public long getJMSTimestamp() throws JMSException {
		return JMSTimestamp;
	}

	public String getJMSType() throws JMSException {
		return JMSType ;
	}

	public long getLongProperty(String arg0) throws JMSException {
		return 0;
	}

	public Object getObjectProperty(String propertyName) throws JMSException {
		return properties.get(propertyName);
	}

	public Enumeration getPropertyNames() throws JMSException {
		return new Enumeration<String>() {
			Iterator<String> iterator = properties.keySet().iterator();
			public String nextElement() {
				
				return iterator.next();
			}
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}
		};
	}

	public short getShortProperty(String arg0) throws JMSException {
		return 0;
	}

	public String getStringProperty(String arg0) throws JMSException {
		return null;
	}

	public boolean propertyExists(String propertyName) throws JMSException {
		return properties.containsKey(propertyName);
	}

	public void setBooleanProperty(String arg0, boolean arg1)
			throws JMSException {
		properties.put(arg0, arg1);
		
	}

	public void setByteProperty(String arg0, byte arg1) throws JMSException {
		properties.put(arg0, arg1);
		
	}

	public void setDoubleProperty(String arg0, double arg1)
			throws JMSException {
		properties.put(arg0, arg1);
		
	}

	public void setFloatProperty(String arg0, float arg1)
			throws JMSException {
		properties.put(arg0, arg1);
		
	}

	public void setIntProperty(String arg0, int arg1) throws JMSException {
		properties.put(arg0, arg1);
		
	}

	public void setJMSCorrelationID(String arg0) throws JMSException {
		JMSCorrelationID = arg0;
	}

	public void setJMSCorrelationIDAsBytes(byte[] arg0) throws JMSException {
		JMSCorrelationID = new String(arg0);
	}

	public void setJMSDeliveryMode(int arg0) throws JMSException {
		JMSDeliveryMode = arg0;
	}

	public void setJMSDestination(Destination arg0) throws JMSException {
		JMSDestination = arg0;
	}

	public void setJMSExpiration(long arg0) throws JMSException {
		JMSExpiration = arg0;
	}

	public void setJMSMessageID(String arg0) throws JMSException {
		JMSMessageID = arg0;
	}

	public void setJMSPriority(int arg0) throws JMSException {
		JMSPriority = arg0;
	}

	public void setJMSRedelivered(boolean arg0) throws JMSException {
		JMSRedelivered = arg0;
	}

	public void setJMSReplyTo(Destination arg0) throws JMSException {
		JMSReplyTo = arg0;
	}

	public void setJMSTimestamp(long arg0) throws JMSException {
		JMSTimestamp = arg0;
	}

	public void setJMSType(String arg0) throws JMSException {
		JMSType = arg0;
	}

	public void setLongProperty(String arg0, long arg1) throws JMSException {
		properties.put(arg0, arg1);
		
	}

	public void setObjectProperty(String arg0, Object arg1)
			throws JMSException {
		properties.put(arg0, arg1);
		
	}

	public void setShortProperty(String arg0, short arg1)
			throws JMSException {
		properties.put(arg0, arg1);
		
	}

	public void setStringProperty(String arg0, String arg1)
			throws JMSException {
		properties.put(arg0, arg1);
		
	}

}
