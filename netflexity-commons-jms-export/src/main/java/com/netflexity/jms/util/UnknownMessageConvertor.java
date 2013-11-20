package com.netflexity.jms.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.stream.XMLStreamReader;

import com.netflexity.jms.MessageType;
import com.netflexity.jms.SnapshotAppender;

public class UnknownMessageConvertor extends XmlMessageConvertor<Message> {

	
	public MessageType getMessageType() {
		return MessageType.UNKNOWN;
	}
	
	public void appendContent(Message message, SnapshotAppender result) throws JMSException {
		
	}

	@Override
	protected void parseMessageContent(XMLStreamReader xmlReader,
			Message message) throws JMSException {
	}
	
}
