/*
 *  2006 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.mq;

import javax.jms.JMSException;

import junit.framework.TestCase;

import org.netflexity.api.mq.jms.AbstractJMSXMLMessageListener;
import org.netflexity.api.mq.jms.JMSMessageListenerContext;
import org.netflexity.api.mq.jms.pubsub.JMSConsumerObject;
import org.netflexity.api.mq.jms.pubsub.PubSub;
import org.netflexity.api.mq.jms.pubsub.PubSubManager;

public class PubSubManagerTest /*extends TestCase*/ {

    PubSub manager = PubSubManager.getInstance("QFLEX");
    JMSConsumerObject consumerObject;
    
    protected void setUp() throws Exception {
//        super.setUp();
        
        consumerObject = new JMSConsumerObject() {
            public String getQueueName() {
                return "SYSTEM.JMS.D.QFLEX.SUB.Q3"; // Has to start with "SYSTEM.JMS.D."
            }
            
            public String getQmanagerName() {
                return "DWMQ10";
            }
        
            public int getPort() {
                return 1415;
            }
        
            public String getIdentifier() {
                return "1234";
            }
        
            public String getHost() {
                return "localhost";
            }
        
            public String getDestinationName() {
                return "Topic/Sample";//"$SYS/Broker/DWMB10/StatisticsAccounting/SnapShot/+/+";
            }
        
            public String getChannelName() {
                return "QFLEX1";
            }

	    public String getSslCipherSpec() {
		return null;
	    }
	    
	    
        };
    }

    protected void tearDown() throws Exception {
//        super.tearDown();
    }

    /*
     * Test method for 'org.netflexity.api.mq.jms.pubsub.PubSubManager.getInstance(String)'
     */
    public void testGetInstance() {
        
    }

    /*
     * Test method for 'org.netflexity.api.mq.jms.pubsub.PubSubManager.subscribeDurable(JMSConsumerObject, JMSMessageListenerContext, JMSMessageListener)'
     */
    public void testSubscribeDurable() {
        JMSMessageListenerContext ctx = new JMSMessageListenerContext();
        try {
            // Subscribe to topic.
            manager.subscribeDurable(consumerObject, new AbstractJMSXMLMessageListener(ctx) {
            
                public void onXmlMessage(String xml) {
                    System.out.println("Received testSubscribeDurable: " + xml);
                }
            });
            
            // Publish message.
            //manager.publish(consumerObject, "Bada Bing!");
            
            // Wait a little.
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }
        }
        catch (JMSException e) {
            //assertTrue(e.getMessage(), false);
        }
    }

    /*
     * Test method for 'org.netflexity.api.mq.jms.pubsub.PubSubManager.subscribe(JMSConsumerObject, JMSMessageListenerContext, JMSMessageListener)'
     */
    public void testSubscribe() {
        JMSMessageListenerContext ctx = new JMSMessageListenerContext();
        try {
            // Subscribe to topic.
            manager.subscribe(consumerObject, new AbstractJMSXMLMessageListener(ctx) {
            
                public void onXmlMessage(String xml) {
                    System.out.println("Received testSubscribe: " + xml);
                }
            });
            
            // Publish message.
            //manager.publish(consumerObject, "Bada Bing!");
            
            // Wait a little.
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }
        }
        catch (JMSException e) {
//            assertTrue(e.getMessage(), false);
        }
    }

    /*
     * Test method for 'org.netflexity.api.mq.jms.pubsub.PubSubManager.subscribeOnceNonBlocking(JMSConsumerObject, JMSMessageListenerContext, JMSMessageListener)'
     */
    public void testSubscribeOnceNonBlocking() {
        JMSMessageListenerContext ctx = new JMSMessageListenerContext();
        try {
            // Subscribe to topic.
            manager.subscribeOnceNonBlocking(consumerObject, new AbstractJMSXMLMessageListener(ctx) {
            
                public void onXmlMessage(String xml) {
                    System.out.println("Received testSubscribeOnceNonBlocking: " + xml);
                }
            });
            
            // Publish message.
            //manager.publish(consumerObject, "Bada Bing!");
            
            // Wait a little.
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }
        }
        catch (JMSException e) {
//            assertTrue(e.getMessage(), false);
        }
    }

    /*
     * Test method for 'org.netflexity.api.mq.jms.pubsub.PubSubManager.unsubscribe(JMSConsumerObject)'
     */
    public void testUnsubscribe() {
        try {
            // Subscribe to topic.
            manager.unsubscribe(consumerObject);
        }
        catch (JMSException e) {
//            assertTrue(e.getMessage(), false);
        }
    }

    /*
     * Test method for 'org.netflexity.api.mq.jms.pubsub.PubSubManager.publish(JMSConsumerObject, String)'
     */
    public void testPublish() {
        try {
            // Subscribe to topic.
            manager.publish(consumerObject, "TEST");
        }
        catch (JMSException e) {
//            assertTrue(e.getMessage(), false);
        }
    }

}
