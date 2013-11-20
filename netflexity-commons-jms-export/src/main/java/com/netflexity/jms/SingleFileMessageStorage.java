package com.netflexity.jms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

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
public class SingleFileMessageStorage implements MessageStorage {
		
	private static final Log logger = LogFactory.getLog(SingleFileMessageStorage.class);
	
	private State state = State.NOT_OPENED;
	
	private File storage;

	private Writer writer;
	
	private SnapshotAppender appender;

	private Map<MessageType, MessageConvertor> converters;
	
	public SingleFileMessageStorage(File storage) {
		//Preparing message converters
		converters = new HashMap<MessageType, MessageConvertor>();
		converters.put(MessageType.BYTES, new ByteMessageConvertor());
		converters.put(MessageType.MAP, new MapMessageConvertor());
		converters.put(MessageType.OBJECT, new ObjectMessageConvertor());
		converters.put(MessageType.STREAM, new StreamMessageConvertor());
		converters.put(MessageType.TEXT, new TextMessageConvertor());
		converters.put(MessageType.UNKNOWN, new UnknownMessageConvertor());
		this.storage = storage;
	}
	
	public void openStorage() {
		if (state != State.NOT_OPENED) { //cannot start already running or finished storage
			throw new RuntimeException("Illegal state to call this function: " + state);
		}
		try {
			//initialiasing writer and appender
			writer = new BufferedWriter(new FileWriter(storage));
			appender = new WriterSnapshotAppender(writer);
			
			//opening messages block 
			writer.append("<messages>");
			
			//marking storage as started
			state = State.OPENED;
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public MessageStats saveMessage(Message message) {
		if (state != State.OPENED) {
			throw new RuntimeException("Illegal state to call this function: " + state);
		}
		MessageConvertor convertor = converters.get(MessageType.getTypeForMessage(message));
		try {
			convertor.write(appender, message);
		} catch (JMSException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		//TODO: should we calculate some statistics here?
		return new MessageStats();
	}

	public void closeStorage() {
		if (state != State.OPENED) { //we cannot stop storage that is not working
			throw new RuntimeException("Illegal state to call this function: " + state);
		}
		try {
			//closing messages block and writer
			writer.append("</messages>");
			writer.close();
			
			state = State.CLOSED;
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	public State getState() {
		return state;
	}
	
	/**
	 * 
	 * {@link SnapshotAppender} implementation that uses writer to store incoming data
	 * 
	 * @author Orient
	 *
	 */
	private class WriterSnapshotAppender extends AbstractSnapshotAppender {
		
		Writer writer;
		
		public WriterSnapshotAppender(Writer writer) {
			this.writer = writer;
		}

		public SnapshotAppender append(String data) {
			try {
				writer.append(data);
				return this;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}


}
