package com.netflexity.jms.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import javax.jms.JMSException;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.w3c.dom.Document;

import com.netflexity.jms.MessageType;
import com.netflexity.jms.Util;
import com.netflexity.jms.mockup.BytesMessage;
import com.netflexity.jms.mockup.StringSnapshotAppender;

public class ByteMessageConvertorTest {

	@Test
	public void testGetMessageType() {
		assertEquals(MessageType.BYTES, new ByteMessageConvertor().getMessageType());
	}

	@Test
	public void testConvert() throws Exception {
		byte[] expected = new byte[]{10, 45, 78};
		
		ByteMessageConvertor convertor = new ByteMessageConvertor();
		BytesMessage message = new BytesMessage();
		message.writeBytes(expected);
		message.reset();
		
		StringSnapshotAppender appender = new StringSnapshotAppender();
		
		convertor.write(appender, message);
		Document doc = Util.toDocument(appender.getData());
		byte[] bytes = convertor.getDecoder().decodeBuffer(Util.getTextContent("content", doc.getDocumentElement()));
		assertArrayEquals(expected, bytes);
	}

	@Test
	public void testRead() throws JMSException, XMLStreamException {
		byte[] expected = new byte[]{10, 45, 78};

		ByteMessageConvertor convertor = new ByteMessageConvertor();
		BytesMessage message = new BytesMessage();
		message.writeBytes(expected);
		message.reset();
		
		StringSnapshotAppender appender = new StringSnapshotAppender();
		
		convertor.write(appender, message);
		
		BytesMessage message2 = new BytesMessage();

		//reading from xml
		Util.readmessage(convertor, message2, appender.getData());
		message2.reset();
		
		byte[] actual = new byte[(int)message2.getBodyLength()];
		message2.readBytes(actual);
		assertArrayEquals(expected, actual);
	}

}
