package com.netflexity.jms.util;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.netflexity.jms.MessageType;
import com.netflexity.jms.SnapshotAppender;

public class TextMessageConvertor extends XmlMessageConvertor<TextMessage> {

	@Override
	public void appendContent(TextMessage message, SnapshotAppender result) throws JMSException {
		result.append("<content>");
		//result.ensureCapacity(message.getText().length());
		encodeString(message.getText(), result);
		result.append("</content>");

	}

	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.TEXT;
	}

	@Override
	protected void parseMessageContent(XMLStreamReader xmlReader,
			TextMessage message) throws JMSException {	
		try {
			message.setText(decodeString(xmlReader.getElementText()));
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
	}

}
