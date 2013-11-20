package com.netflexity.jms.util;

import javax.jms.JMSException;
import javax.jms.Message;

public interface MessageConvertor<M extends Message, OUT, IN> {
	
	void write(OUT output, M message) throws JMSException;
	
	void read(IN input, M message) throws JMSException;
}
