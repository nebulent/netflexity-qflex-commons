package com.netflexity.jms;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;

public class QueueSnapshotJobTest {

	@Test
	public void testRun() throws Exception {
		BrokerService broker = new BrokerService();

		// configure the broker
		broker.addConnector("tcp://localhost:61616");

		broker.start();
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
		
		final ActiveMQQueue queue = new ActiveMQQueue("test");
		final ActiveMQQueue restoreQueue = new ActiveMQQueue("restore");
		//purgeQueue(queue, "service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
		//purgeQueue(restoreQueue, "service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
		
		JmsTemplate template = new JmsTemplate(cf);
		
		//populate with messages
		template.convertAndSend(queue, "vasea");
		template.convertAndSend(queue, new byte[]{10, -9, 0, 5});
		template.convertAndSend(queue, new HashMap());
		template.convertAndSend(queue, new TestObject());
		
		
		SingleFileMessageStorage storage = new SingleFileMessageStorage(new File("storage.xml"));
		
		QueueSnapshotJob job = new QueueSnapshotJob(cf, queue, storage);
		job.run();
			
		
		QueueRestoreJob restoreJob = new QueueRestoreJob(cf, restoreQueue, new SingleFileMessageSource(new File("storage.xml")));
		restoreJob.run();
		
		template.browse(restoreQueue, new BrowserCallback() {
		
			public Object doInJms(Session session, QueueBrowser browser) throws JMSException {
				int count = 0;
				Enumeration<Message> messages = browser.getEnumeration();
				while (messages.hasMoreElements()) {
					Message message = (Message) messages.nextElement();
					count++;
				}
				assertEquals(4, count);
				return null;
			}
		
		});
		//while (true) {}
		broker.stop();
	}
	
	private void purgeQueue(Queue queue, String jmxUrl) throws MalformedObjectNameException, NullPointerException, InstanceNotFoundException, MBeanException, ReflectionException, IOException, JMSException {
		MBeanServerConnection connection = null;
		JMXServiceURL address = new JMXServiceURL(jmxUrl);
		JMXConnector cntor = JMXConnectorFactory.connect(address, new HashMap());
		connection = cntor.getMBeanServerConnection();

		Hashtable keys = new Hashtable();
//		*  Type=Queue|Topic
//		* Destination=<destination identifier>
//		* BrokerName=<name of broker>

		keys.put("Type", "Queue");
		keys.put("BrokerName", "localhost");
		keys.put("Destination", queue.getQueueName());
		ObjectName name = new ObjectName("org.apache.activemq", keys);
		System.out.println(connection.invoke(name, "purge", new Object[]{}, new String[]{}));
	}
	
	public static class TestObject implements Serializable {
		
	}
}
