package com.netflexity.jms.util;

import javax.jms.JMSException;
import javax.jms.StreamMessage;
import javax.xml.stream.XMLStreamReader;

import com.netflexity.jms.MessageType;
import com.netflexity.jms.SnapshotAppender;

public class StreamMessageConvertor extends XmlMessageConvertor<StreamMessage> {

	@Override
	public void appendContent(StreamMessage message, SnapshotAppender result)
			throws JMSException {
//		result.append("<content>");
//		message.
//		result.append("</content>");

	}

	@Override
	public MessageType getMessageType() {
		return MessageType.STREAM;
	}

	@Override
	protected void parseMessageContent(XMLStreamReader xmlReader,
			StreamMessage message) throws JMSException {
	}

}
