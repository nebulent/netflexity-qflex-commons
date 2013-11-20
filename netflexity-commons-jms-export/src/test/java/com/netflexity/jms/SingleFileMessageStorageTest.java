package com.netflexity.jms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.netflexity.jms.mockup.TextMessage;

public class SingleFileMessageStorageTest {

	File storage = new File("storage.xml");
	
	@Before
	public void setUp() throws IOException {
		storage.createNewFile();
	}
	
	@After
	public void tearDown() {
		storage.delete();
	}
	
	@Test
	public void testOpenStorage_corect_call_sequence() {
		SingleFileMessageStorage sfms = new SingleFileMessageStorage(storage);
		assertEquals(MessageStorage.State.NOT_OPENED, sfms.getState());
		sfms.openStorage();
		assertEquals(MessageStorage.State.OPENED, sfms.getState());
		sfms.closeStorage();
		assertEquals(MessageStorage.State.CLOSED, sfms.getState());
	}

	@Test
	public void testOpenStorage_incorect_call_sequence1() {
		SingleFileMessageStorage sfms = new SingleFileMessageStorage(storage);
		sfms.openStorage();
		try {
			sfms.openStorage();
		} catch (RuntimeException e) {
			return;
		}
		fail("an exception should happen");
	}

	@Test
	public void testOpenStorage_incorect_call_sequence2() {
		SingleFileMessageStorage sfms = new SingleFileMessageStorage(storage);
		sfms.openStorage();
		sfms.closeStorage();
		try {
			sfms.openStorage();
		} catch (RuntimeException e) {
			return;
		}
		fail("an exception should happen");
	}

	@Test
	public void testSaveMessage() throws Exception {
		SingleFileMessageStorage sfms = new SingleFileMessageStorage(storage);
		sfms.openStorage();
		
		TextMessage message = new TextMessage();
		message.setText("vasea");
		sfms.saveMessage(message);
		sfms.closeStorage();
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new FileInputStream(storage));
	}

	@Test
	public void testSaveMessage_incorect_call_sequence1() throws Exception {
		SingleFileMessageStorage sfms = new SingleFileMessageStorage(storage);
		
		try {
			TextMessage message = new TextMessage();
			message.setText("vasea");
			sfms.saveMessage(message);
		} catch (RuntimeException e) {
			return;
		}
		fail("an exception should happen");
	}

	@Test
	public void testSaveMessage_incorect_call_sequence2() throws Exception {
		SingleFileMessageStorage sfms = new SingleFileMessageStorage(storage);
		sfms.openStorage();	
		sfms.closeStorage();
		
		try {
			TextMessage message = new TextMessage();
			message.setText("vasea");
			sfms.saveMessage(message);
		} catch (RuntimeException e) {
			return;
		}
		fail("an exception should happen");

	}

	@Test
	public void testCloseStorage() throws Exception {
		SingleFileMessageStorage sfms = new SingleFileMessageStorage(storage);
		sfms.openStorage();
		sfms.closeStorage();
	}

	@Test
	public void testCloseStorage_incorect_call_sequence1() {
		SingleFileMessageStorage sfms = new SingleFileMessageStorage(storage);
		sfms.openStorage();
		sfms.closeStorage();
		try {
			sfms.closeStorage();
		} catch (RuntimeException e) {
			return;
		}
		fail("an exception should happen");
	}

	@Test
	public void testCloseStorage_incorect_call_sequence2() {
		SingleFileMessageStorage sfms = new SingleFileMessageStorage(storage);
		try {
			sfms.closeStorage();
		} catch (RuntimeException e) {
			return;
		}
		fail("an exception should happen");
	}

}
