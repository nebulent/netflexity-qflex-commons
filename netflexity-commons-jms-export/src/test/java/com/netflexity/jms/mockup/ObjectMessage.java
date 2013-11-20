package com.netflexity.jms.mockup;

import java.io.Serializable;

import javax.jms.JMSException;

public class ObjectMessage extends Message implements javax.jms.ObjectMessage {

	Serializable object;
	
	public Serializable getObject() throws JMSException {
		return object;
	}

	public void setObject(Serializable object) throws JMSException {
		this.object = object;
	}

}
