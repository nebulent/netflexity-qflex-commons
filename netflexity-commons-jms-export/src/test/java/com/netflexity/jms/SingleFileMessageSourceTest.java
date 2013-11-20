package com.netflexity.jms;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.jmock.Mockery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.netflexity.jms.mockup.Session;

public class SingleFileMessageSourceTest {

	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	Mockery mockupContext = new Mockery();
	
	
	@Test
	public void testSingleFileMessageSource() {

	}

	@Test
	public void testOpen() throws IOException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:empty_dump.xml").getFile());
		sfms.open();
		assertFalse(sfms.hasMoreMessages());
		sfms.close();
	}
	
	@Test
	public void testOpen_incorrect() throws IOException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:empty_dump.xml").getFile());
		sfms.open();
		sfms.close();
	}

	@Test
	public void testHasMoreMessages() throws IOException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:one_message_dump.xml").getFile());
		sfms.open();
		assertTrue(sfms.hasMoreMessages());
		sfms.close();
	}
	
	@Test
	public void testHasMoreMessages_incorrect_invocation() throws IOException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:one_message_dump.xml").getFile());
		sfms.open();
		assertTrue(sfms.hasMoreMessages());
		sfms.close();
		try {
			sfms.hasMoreMessages();
		} catch (RuntimeException e) {
			return;
		}
		fail("An exception should be thrown here");
	}

	@Test
	public void testNextMessage() throws IOException, JMSException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:one_message_dump.xml").getFile());
		sfms.open();
		assertTrue(sfms.hasMoreMessages());
		assertNotNull(sfms.nextMessage(new Session()));
		assertFalse(sfms.hasMoreMessages());
		sfms.close();
	}
	
	@Test
	public void testNextMessage_incorrect_invocation1() throws IOException, JMSException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:one_message_dump.xml").getFile());
		sfms.open();
		assertTrue(sfms.hasMoreMessages());
		assertNotNull(sfms.nextMessage(new Session()));
		assertFalse(sfms.hasMoreMessages());
		try {
			sfms.nextMessage(new Session());
		} catch (RuntimeException e) {
			return;
		}
		sfms.close();
	}
	
	@Test
	public void testNextMessage_incorrect_invocation2() throws IOException, JMSException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:one_message_dump.xml").getFile());
		sfms.open();
		assertTrue(sfms.hasMoreMessages());
		sfms.close();
		try {
			sfms.nextMessage(new Session());
		} catch (RuntimeException e) {
			return;
		}
	}
	
	@Test
	public void testNextMessage_4_message_types() throws IOException, JMSException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:4_message_dump.xml").getFile());
		Session session = new Session();
		
		sfms.open();
		assertTrue(sfms.hasMoreMessages());
		assertTrue(sfms.nextMessage(session) instanceof TextMessage);
		assertTrue(sfms.nextMessage(session) instanceof ObjectMessage);
		assertTrue(sfms.nextMessage(session) instanceof BytesMessage);
		assertTrue(sfms.nextMessage(session) instanceof MapMessage);
		assertFalse(sfms.hasMoreMessages());
		sfms.close();
	}
	
	@Test
	public void testClose() throws IOException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:empty_dump.xml").getFile());
		sfms.open();
		assertFalse(sfms.hasMoreMessages());
		sfms.close();
	}
	
	@Test
	public void testClose_incorrect_invocation() throws IOException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:empty_dump.xml").getFile());
		try {
			sfms.close();
		} catch (RuntimeException e) {
			return;
		}
		fail("An exception should be thrown here");
	}

	@Test
	public void testGetState() throws IOException {
		SingleFileMessageSource sfms = new SingleFileMessageSource(
				context.getResource("classpath:empty_dump.xml").getFile());
		assertEquals(MessageSource.State.NOT_OPENED, sfms.getState());
		sfms.open();
		assertEquals(MessageSource.State.OPENED, sfms.getState());
		assertFalse(sfms.hasMoreMessages());
		sfms.close();
		assertEquals(MessageSource.State.CLOSED, sfms.getState());
	}

}
