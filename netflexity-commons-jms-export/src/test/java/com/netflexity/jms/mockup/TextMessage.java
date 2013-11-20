package com.netflexity.jms.mockup;

import javax.jms.JMSException;

public class TextMessage extends Message implements javax.jms.TextMessage {
	
	private String text = "some text";
	
	public String getText() throws JMSException {
		return text;
	}

	public void setText(String text) throws JMSException {
		this.text = text;
	}


}
