package com.netflexity.jms.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.jms.JMSException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.netflexity.jms.MessageType;
import com.netflexity.jms.Util;
import com.netflexity.jms.mockup.ObjectMessage;
import com.netflexity.jms.mockup.StringSnapshotAppender;

public class ObjectMessageConvertorTest {

	@Test
	public void testGetMessageType() {
		assertEquals(MessageType.OBJECT, new ObjectMessageConvertor().getMessageType());
	}

	@Test
	public void testConvert() throws JMSException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException {
		String object = new String("vasea");
		ObjectMessageConvertor convertor = new ObjectMessageConvertor();
		ObjectMessage message = new ObjectMessage();
		message.setObject(object);
		
		StringSnapshotAppender appender = new StringSnapshotAppender();
		
		convertor.write(appender, message);
		Document doc = Util.toDocument(appender.getData());
		byte[] bytes = convertor.getDecoder().decodeBuffer(Util.getTextContent("content", doc.getDocumentElement()));
		assertEquals(object, (String)new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject());
	}
	
	@Test
	public void testRead() throws JMSException, XMLStreamException {
		String object = new String("vasea");
		ObjectMessageConvertor convertor = new ObjectMessageConvertor();
		ObjectMessage message = new ObjectMessage();
		message.setObject(object);
		
		StringSnapshotAppender appender = new StringSnapshotAppender();
		
		convertor.write(appender, message);
		
		ObjectMessage message2 = new ObjectMessage();

		//reading from xml
		//System.out.println(appender.getData());
		Util.readmessage(convertor, message2, appender.getData());
		
		assertEquals(object, message2.getObject());
	}

}
