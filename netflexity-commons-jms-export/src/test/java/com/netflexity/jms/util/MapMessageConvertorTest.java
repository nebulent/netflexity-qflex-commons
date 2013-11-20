package com.netflexity.jms.util;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import javax.jms.JMSException;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.netflexity.jms.MessageType;
import com.netflexity.jms.Util;
import com.netflexity.jms.mockup.MapMessage;
import com.netflexity.jms.mockup.StringSnapshotAppender;

public class MapMessageConvertorTest {

	@Test
	public void testGetMessageType() {
		assertEquals(MessageType.MAP, new MapMessageConvertor().getMessageType());
	}

	@Test
	public void testConvert() throws Exception {
		byte[] expected = new byte[]{10, 45, 78};
		
		MapMessageConvertor convertor = new MapMessageConvertor();
		MapMessage message = new MapMessage();
		message.setObject("boolean", new Boolean("true"));
		message.setObject("byte", new Byte(Byte.MAX_VALUE));
		message.setObject("short", new Short(Short.MAX_VALUE));
		message.setObject("int", new Integer(Integer.MAX_VALUE));
		message.setObject("long", new Long(Long.MAX_VALUE));
		message.setObject("float", new Float(12.6));
		message.setObject("double", new Double(12.6));
		message.setObject("string", "test");
		message.setObject("byte[]", expected);
		
		StringSnapshotAppender appender = new StringSnapshotAppender();
		
		convertor.write(appender, message);
		Document doc = Util.toDocument(appender.getData());
		Element content = (Element)doc.getDocumentElement().getElementsByTagName("content").item(0);
		
		assertEquals("true", Util.getPropertyValue("boolean", content));
		assertEquals(DataType.BOOLEAN, Util.getPropertyType("boolean", content));

		assertEquals(Byte.MAX_VALUE, Byte.parseByte(Util.getPropertyValue("byte", content)));
		assertEquals(DataType.BYTE, Util.getPropertyType("byte", content));

		assertEquals(Short.MAX_VALUE, Short.parseShort(Util.getPropertyValue("short", content)));
		assertEquals(DataType.SHORT, Util.getPropertyType("short", content));

		assertEquals(Short.MAX_VALUE, Short.parseShort(Util.getPropertyValue("short", content)));
		assertEquals(DataType.SHORT, Util.getPropertyType("short", content));

		assertEquals(Integer.MAX_VALUE, Integer.parseInt(Util.getPropertyValue("int", content)));
		assertEquals(DataType.INT, Util.getPropertyType("int", content));

		assertEquals(Long.MAX_VALUE, Long.parseLong(Util.getPropertyValue("long", content)));
		assertEquals(DataType.LONG, Util.getPropertyType("long", content));

		assertEquals("12.6", Util.getPropertyValue("float", content));
		assertEquals(DataType.FLOAT, Util.getPropertyType("float", content));

		assertEquals("12.6", Util.getPropertyValue("double", content));
		assertEquals(DataType.DOUBLE, Util.getPropertyType("double", content));

		assertArrayEquals(expected, convertor.getDecoder().decodeBuffer(Util.getPropertyValue("byte[]", content)));
		assertEquals(DataType.BYTES_ARRAY, Util.getPropertyType("byte[]", content));

		
		//TODO: this test asssertion is not quite correct
		StringSnapshotAppender tmp = new StringSnapshotAppender();
		convertor.encodeString("test", tmp);
		assertEquals(tmp.getData(), Util.getPropertyValue("string", content));
		assertEquals(DataType.STRING, Util.getPropertyType("string", content));

	}

	@Test
	public void testRead() throws JMSException, XMLStreamException {
		byte[] expected = new byte[]{10, 45, 78};
		
		MapMessageConvertor convertor = new MapMessageConvertor();
		MapMessage message = new MapMessage();
		message.setObject("boolean", new Boolean("true"));
		message.setObject("byte", new Byte(Byte.MAX_VALUE));
		message.setObject("short", new Short(Short.MAX_VALUE));
		message.setObject("int", new Integer(Integer.MAX_VALUE));
		message.setObject("long", new Long(Long.MAX_VALUE));
		message.setObject("float", new Float(12.6));
		message.setObject("double", new Double(12.6));
		message.setObject("string", "test");
		message.setObject("byte[]", expected);
		
		StringSnapshotAppender appender = new StringSnapshotAppender();
		
		convertor.write(appender, message);
		
//		System.out.println(appender.getData());
		MapMessage message2 = new MapMessage();

		//reading from xml
		Util.readmessage(convertor, message2, appender.getData());
		
		assertEquals(message2.getObject("boolean"), new Boolean("true"));
		assertEquals(message2.getObject("byte"), new Byte(Byte.MAX_VALUE));
		assertEquals(message2.getObject("short"), new Short(Short.MAX_VALUE));
		assertEquals(message2.getObject("int"), new Integer(Integer.MAX_VALUE));
		assertEquals(message2.getObject("long"), new Long(Long.MAX_VALUE));
		assertEquals(message2.getObject("float"), new Float(12.6));
		assertEquals(message2.getObject("double"), new Double(12.6));
		assertEquals(message2.getObject("string"), "test");
		assertArrayEquals((byte[])message2.getObject("byte[]"), expected);
	}

}
