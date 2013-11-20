package com.netflexity.jms.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.netflexity.jms.Util;
import com.netflexity.jms.mockup.NullSnapshotAppender;
import com.netflexity.jms.mockup.StringSnapshotAppender;

public class TextMessageConvertorTest {

	@Test
	public void convert_correct_convertion() throws JMSException, ParserConfigurationException, SAXException, IOException {
		TextMessageConvertor con = new TextMessageConvertor();
		TextMessage message = new com.netflexity.jms.mockup.TextMessage();
		message.setText("some text");
		StringSnapshotAppender appender = new StringSnapshotAppender();
		
		con.write(appender, message);
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(appender.getData().getBytes()));
		
		Element content = (Element)doc.getElementsByTagName("content").item(0);
		assertEquals(con.getEncoder().encode("some text".getBytes()), content.getTextContent());
	}

	@Test
	public void convert_bad_xml() throws JMSException, ParserConfigurationException, SAXException, IOException {
		TextMessageConvertor con = new TextMessageConvertor();
		TextMessage message = new com.netflexity.jms.mockup.TextMessage();
		message.setText("&<some text");
		StringSnapshotAppender appender = new StringSnapshotAppender();
		
		con.write(appender, message);
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(appender.getData().getBytes()));
	}

	
	@Test
	public void convert_loadtesting() throws JMSException {

		NullSnapshotAppender appender = new NullSnapshotAppender();
		
		
		TextMessageConvertor con = new TextMessageConvertor();
		TextMessage message = new com.netflexity.jms.mockup.TextMessage();
		StringBuilder builder = new StringBuilder();
		String data = "12345123451234512345123451234512345123451234512345" + //100 bytes
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345" + 
		"12345123451234512345123451234512345123451234512345"; //1Kib
		for(int i=1; i<=1000*15+500; i++) { //15,5 Mib
			builder.append(data);
		}
		message.setText(builder.toString());
		//byte[] bytes = new byte[message.getText().length()*2];
		long start = System.currentTimeMillis();
		con.write(appender, message);
		//System.out.println("Time elapsed: " + (System.currentTimeMillis() - start));
	}
	
	@Test
	public void testRead() throws JMSException, XMLStreamException {
		TextMessageConvertor con = new TextMessageConvertor();
		TextMessage message = new com.netflexity.jms.mockup.TextMessage();
		message.setText("public void read() throws JMSException, XMLStreamException");
		StringSnapshotAppender appender = new StringSnapshotAppender();
		
		con.write(appender, message);
		
		//creating second message
		com.netflexity.jms.mockup.TextMessage message2 = new com.netflexity.jms.mockup.TextMessage();

		//reading from xml
		//System.out.println(appender.getData());
		Util.readmessage(con, message2, appender.getData());
		
		assertEquals("public void read() throws JMSException, XMLStreamException", message2.getText());
		
	}
}
