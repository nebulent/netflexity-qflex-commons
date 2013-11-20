
package com.netflexity.jms.util;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.netflexity.jms.MessageType;
import com.netflexity.jms.SnapshotAppender;

public class ByteMessageConvertor extends XmlMessageConvertor<BytesMessage> {

	@Override
	public void appendContent(BytesMessage message, SnapshotAppender result)
	throws JMSException {
		result.append("<content>");
		byte[] bytes = new byte[(int) message.getBodyLength()];
		//next two lines are borrowed from spring framework
		message.readBytes(bytes);
		result.append(getEncoder().encode(bytes));
		result.append("</content>");

	}

	@Override
	public MessageType getMessageType() {
		return MessageType.BYTES;
	}

	@Override
	protected void parseMessageContent(XMLStreamReader xmlReader,
			BytesMessage message) throws JMSException {
		try {
			message.writeBytes(getDecoder().decodeBuffer(xmlReader.getElementText()));
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
	}

}
