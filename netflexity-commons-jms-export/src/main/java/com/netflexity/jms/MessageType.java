package com.netflexity.jms;

import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

public enum MessageType {
	
	TEXT, MAP, BYTES, OBJECT, STREAM, UNKNOWN;
	
	public static MessageType getTypeForMessage(Message message) {
		if (message instanceof TextMessage) {
			return TEXT;
		}
		if (message instanceof MapMessage) {
			return MAP;
		}
		if (message instanceof BytesMessage) {
			return BYTES;
		}
		if (message instanceof ObjectMessage) {
			return OBJECT;
		}
		if (message instanceof StreamMessage) {
			return STREAM;
		}
		return UNKNOWN;
	}
}
