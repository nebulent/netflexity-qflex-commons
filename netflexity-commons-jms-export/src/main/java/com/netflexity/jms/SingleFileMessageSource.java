package com.netflexity.jms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.netflexity.jms.util.ByteMessageConvertor;
import com.netflexity.jms.util.MapMessageConvertor;
import com.netflexity.jms.util.MessageConvertor;
import com.netflexity.jms.util.ObjectMessageConvertor;
import com.netflexity.jms.util.StreamMessageConvertor;
import com.netflexity.jms.util.TextMessageConvertor;
import com.netflexity.jms.util.UnknownMessageConvertor;

/**
 * 
 * {@link MessageStorage} that uses single file to store all messages
 * 
 * @author Orient
 *
 */
public class SingleFileMessageSource implements MessageSource {

	private static final Log logger = LogFactory.getLog(SingleFileMessageSource.class);

	private State state = State.NOT_OPENED;

	private File source;

	private Reader reader;

	private XMLStreamReader xmlReader;

	private Map<MessageType, MessageConvertor> converters;

	private boolean hasNext = false;

	public SingleFileMessageSource(File source) {
		//Preparing message converters
		converters = new HashMap<MessageType, MessageConvertor>();
		converters.put(MessageType.BYTES, new ByteMessageConvertor());
		converters.put(MessageType.MAP, new MapMessageConvertor());
		converters.put(MessageType.OBJECT, new ObjectMessageConvertor());
		converters.put(MessageType.STREAM, new StreamMessageConvertor());
		converters.put(MessageType.TEXT, new TextMessageConvertor());
		converters.put(MessageType.UNKNOWN, new UnknownMessageConvertor());
		this.source = source;
	}

	public void open() {
		if (state != State.NOT_OPENED) { //cannot start already running or finished storage
			throw new RuntimeException("Illegal state to call this function: " + state);
		}
		try {
			//initialiasing reader and xml stream
			reader = new BufferedReader(new FileReader(source));
			XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
			xmlReader = xmlFactory.createXMLStreamReader(reader);

			hasNext = moveToNext();

			state = State.OPENED;
		} catch (XMLStreamException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} 
	}

	public boolean hasMoreMessages() {
		if (state != State.OPENED) { 
			throw new RuntimeException("Illegal state to call this function: " + state);
		}
		return hasNext;
	}

	public Message nextMessage(Session session) throws JMSException {
		if (state != State.OPENED) { 
			throw new RuntimeException("Illegal state to call this function: " + state);
		}
		if (!hasNext) {
			throw new RuntimeException("No more messages in stream");
		}
		MessageType type = MessageType.valueOf(xmlReader.getAttributeValue(null, "type"));
		Message message = createMessageForType(type, session);
		converters.get(type).read(xmlReader, message);
		try {
			hasNext = moveToNext();
		} catch (XMLStreamException e) {
			hasNext = false;
			throw new RuntimeException("Unnable to move to the next message in xml stream", e);
		}
		
		return message;
	}

	public void close() {
		if (state != State.OPENED) { //we cannot stop storage that is not working
			throw new RuntimeException("Illegal state to call this function: " + state);
		}
		try {
			xmlReader.close();
			reader.close();

			state = State.CLOSED;
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (XMLStreamException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	public State getState() {
		return state;
	}

	private boolean moveToNext() throws XMLStreamException {
		while(xmlReader.hasNext()) {
			xmlReader.next();
			if(xmlReader.getEventType() == XMLEvent.START_ELEMENT 
					&& xmlReader.getName().getLocalPart().equals("message")) {
				return true;
			}
		}
		return false;
	}

	private Message createMessageForType(MessageType type, Session session) throws JMSException {

		switch (type) {
		case BYTES:
			return session.createBytesMessage();

		case MAP:
			return session.createMapMessage();

		case OBJECT:
			return session.createObjectMessage();

		case STREAM:
			return session.createStreamMessage();

		case TEXT:
			return session.createTextMessage();

		case UNKNOWN:
			return session.createMessage();

		default:
			return session.createMessage();
		}
	}
}
