package com.netflexity.jms.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.netflexity.jms.MessageType;
import com.netflexity.jms.SnapshotAppender;

public class ObjectMessageConvertor extends XmlMessageConvertor<ObjectMessage> {

	@Override
	public void appendContent(ObjectMessage message, SnapshotAppender result)
			throws JMSException {
		result.append("<content>");
		//TODO rewrite here for huge objects
		Serializable obj = message.getObject();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		result.append(getEncoder().encode(baos.toByteArray()));
		result.append("</content>");
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.OBJECT;
	}

	@Override
	protected void parseMessageContent(XMLStreamReader xmlReader,
			ObjectMessage message) throws JMSException {
		try {
			String content = xmlReader.getElementText();
			ObjectInputStream ois = 
				new ObjectInputStream(new ByteArrayInputStream(getDecoder().decodeBuffer(content)));
			Object object = ois.readObject();
			message.setObject((Serializable)object);
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
	}

}
