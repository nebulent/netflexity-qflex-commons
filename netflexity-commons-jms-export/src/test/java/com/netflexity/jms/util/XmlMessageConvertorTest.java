package com.netflexity.jms.util;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.netflexity.jms.Util;
import com.netflexity.jms.mockup.StringSnapshotAppender;
import com.netflexity.jms.mockup.TextMessage;


public class XmlMessageConvertorTest {
	
	XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
	
	@Test
	public void convert_validheaderconversion () 
			throws JMSException, ParserConfigurationException, SAXException, IOException {
		XmlMessageConvertor convertor = new TextMessageConvertor();
		StringSnapshotAppender appender = new StringSnapshotAppender();	
		
		com.netflexity.jms.mockup.Message message = new TextMessage();
		message.setObjectProperty("boolean", new Boolean("true"));
		message.setObjectProperty("byte", new Byte(Byte.MAX_VALUE));
		message.setObjectProperty("short", new Short(Short.MAX_VALUE));
		message.setObjectProperty("int", new Integer(Integer.MAX_VALUE));
		message.setObjectProperty("long", new Long(Long.MAX_VALUE));
		message.setObjectProperty("float", new Float(12.5));
		message.setObjectProperty("double", new Double(12.5));
		message.setObjectProperty("string", "test");

		
		convertor.write(appender, message);
		
		Document doc = Util.toDocument(appender.getData());
		Element msgElement = doc.getDocumentElement();
		assertEquals(message.getJMSCorrelationID(), msgElement.getAttribute("JMSCorrelationID"));
		assertEquals(message.getJMSDeliveryMode(), Integer.parseInt(msgElement.getAttribute("JMSDeliveryMode")));
		assertEquals(message.getJMSExpiration(), Long.parseLong(msgElement.getAttribute("JMSExpiration")));
		assertEquals(message.getJMSMessageID(), msgElement.getAttribute("JMSMessageID"));
		assertEquals(message.getJMSPriority(), Integer.parseInt(msgElement.getAttribute("JMSPriority")));
		assertEquals(message.getJMSRedelivered(), Boolean.parseBoolean(msgElement.getAttribute("JMSRedelivered")));
		//TODO: realy dont know how to handl this attribute: message.getJMSReplyTo();
		//.append(" JMSReplyTo=\"").append(message.getJMSReplyTo()).append("\" ")
		assertEquals(message.getJMSTimestamp(), Long.parseLong(msgElement.getAttribute("JMSTimestamp")));
		assertEquals(message.getJMSType(), msgElement.getAttribute("JMSType"));
		
		assertEquals("true", Util.getPropertyValue("boolean", msgElement));
		assertEquals(DataType.BOOLEAN, Util.getPropertyType("boolean", msgElement));

		assertEquals(Byte.MAX_VALUE, Byte.parseByte(Util.getPropertyValue("byte", msgElement)));
		assertEquals(DataType.BYTE, Util.getPropertyType("byte", msgElement));

		assertEquals(Short.MAX_VALUE, Short.parseShort(Util.getPropertyValue("short", msgElement)));
		assertEquals(DataType.SHORT, Util.getPropertyType("short", msgElement));

		assertEquals(Short.MAX_VALUE, Short.parseShort(Util.getPropertyValue("short", msgElement)));
		assertEquals(DataType.SHORT, Util.getPropertyType("short", msgElement));

		assertEquals(Integer.MAX_VALUE, Integer.parseInt(Util.getPropertyValue("int", msgElement)));
		assertEquals(DataType.INT, Util.getPropertyType("int", msgElement));

		assertEquals(Long.MAX_VALUE, Long.parseLong(Util.getPropertyValue("long", msgElement)));
		assertEquals(DataType.LONG, Util.getPropertyType("long", msgElement));

		assertEquals("12.5", Util.getPropertyValue("float", msgElement));
		assertEquals(DataType.FLOAT, Util.getPropertyType("float", msgElement));

		assertEquals("12.5", Util.getPropertyValue("double", msgElement));
		assertEquals(DataType.DOUBLE, Util.getPropertyType("double", msgElement));

		
		//TODO: this test asssertion is not quite correct
		StringSnapshotAppender tmp = new StringSnapshotAppender();
		convertor.encodeString("test", tmp);
		assertEquals(tmp.getData(), Util.getPropertyValue("string", msgElement));
		assertEquals(DataType.STRING, Util.getPropertyType("string", msgElement));


	}
	
	
	@Test
	public void convert_validxml() throws JMSException, ParserConfigurationException, SAXException, IOException {
		MessageConvertor convertor = new TextMessageConvertor();
		StringSnapshotAppender appender = new StringSnapshotAppender();
		
		convertor.write(appender, new TextMessage());
		
		Util.toDocument(appender.getData());
	}
	
	@Test
	public void convert_badStringProperty() throws JMSException, ParserConfigurationException, SAXException, IOException {
		MessageConvertor convertor = new TextMessageConvertor();
		Message message = new TextMessage();
		message.setObjectProperty("badString", "<vasea");
		StringSnapshotAppender appender = new StringSnapshotAppender();
				
		convertor.write(appender, message);
		
		Util.toDocument(appender.getData());
	}
	
	@Test
	public void testRead() throws JMSException, XMLStreamException {
		//convertor
		XmlMessageConvertor convertor = new TextMessageConvertor();
		
		//making our test message
		com.netflexity.jms.mockup.Message message = new TextMessage();
		message.setJMSCorrelationID("123-456-789");
		message.setJMSDeliveryMode(6);
		message.setJMSExpiration(43345134);
		message.setJMSMessageID("m121231231");
		message.setJMSPriority(4);
		message.setJMSRedelivered(true);
		//TODO: add replyTo when implementation will be ready
		message.setJMSTimestamp(System.currentTimeMillis());
		message.setJMSType("fig ego znaet");

		message.setObjectProperty("boolean", new Boolean("true"));
		message.setObjectProperty("byte", new Byte(Byte.MAX_VALUE));
		message.setObjectProperty("short", new Short(Short.MAX_VALUE));
		message.setObjectProperty("int", new Integer(Integer.MAX_VALUE));
		message.setObjectProperty("long", new Long(Long.MAX_VALUE));
		message.setObjectProperty("float", new Float(12.5));
		message.setObjectProperty("double", new Double(12.5));
		message.setObjectProperty("string", "test");
		
		//writing to xml
		StringSnapshotAppender appender = new StringSnapshotAppender();	
		convertor.write(appender, message);		
		
		//creating second message
		com.netflexity.jms.mockup.Message message2 = new TextMessage();

		//reading from xml
		Util.readmessage(convertor, message2, appender.getData());
		
		//asserting
		assertEquals(message.getJMSCorrelationID(), message2.getJMSCorrelationID());
		assertEquals(message.getJMSDeliveryMode(), message2.getJMSDeliveryMode());
		assertEquals(message.getJMSExpiration(), message2.getJMSExpiration());
		assertEquals(message.getJMSMessageID(), message2.getJMSMessageID());
		assertEquals(message.getJMSPriority(), message2.getJMSPriority());
		assertEquals(message.getJMSRedelivered(), message2.getJMSRedelivered());
		//TODO: add replyTo when implementation will be ready
		assertEquals(message.getJMSTimestamp(), message2.getJMSTimestamp());
		assertEquals(message.getJMSType(), message2.getJMSType());
		
		assertEquals(message2.getObjectProperty("boolean"), new Boolean("true"));
		assertEquals(message2.getObjectProperty("byte"), new Byte(Byte.MAX_VALUE));
		assertEquals(message2.getObjectProperty("short"), new Short(Short.MAX_VALUE));
		assertEquals(message2.getObjectProperty("int"), new Integer(Integer.MAX_VALUE));
		assertEquals(message2.getObjectProperty("long"), new Long(Long.MAX_VALUE));
		assertEquals(message2.getObjectProperty("float"), new Float(12.5));
		assertEquals(message2.getObjectProperty("double"), new Double(12.5));
		assertEquals(message2.getObjectProperty("string"), "test");

		
	}
	
	
	
	@Test //this tests shows tha encoding part by part is not equal to encoding a whole string
	public void encodeString() throws Exception {
		TextMessageConvertor con = new TextMessageConvertor();
		StringBuilder builder = new StringBuilder();
		StringSnapshotAppender appender = new StringSnapshotAppender();

		String data = "12345123451234512345123451234512345123451234512345" + //100 bytes
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345"; //500 chars
		for(int i=1; i<=4*1000+500; i++) { //2000500 chars
			builder.append(data);
		}	
		
		String result = builder.toString();
		
		con.encodeString(result, appender);
		
		assertEquals(result, con.decodeString(appender.getData()));
	}
	
}
