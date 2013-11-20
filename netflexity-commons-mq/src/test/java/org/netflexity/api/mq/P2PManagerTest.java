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

import org.netflexity.api.mq.jms.p2p.P2P;
import org.netflexity.api.mq.jms.p2p.P2PManager;
import org.netflexity.api.mq.jms.pubsub.JMSConsumerObject;

public class P2PManagerTest /*extends TestCase*/ {

    P2P manager = P2PManager.getInstance();
    JMSConsumerObject consumerObject;
    
    protected void setUp() throws Exception {
//        super.setUp();
        
        consumerObject = new JMSConsumerObject() {
            public String getQueueName() {
                return "APPLES";
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
                return getQueueName();
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
     * Test method for 'org.netflexity.api.mq.jms.p2p.P2PManager.getInstance()'
     */
    public void testGetInstance() {
        
    }

    public void testSend() {
        try {
            manager.send(consumerObject, "this is a test1");
            manager.send(consumerObject, "this is a test2");
            manager.send(consumerObject, "this is a test3");
        }
        catch (JMSException e) {
//            assertTrue(e.getMessage(), false);
        }
    }
    
    /*
     * Test method for 'org.netflexity.api.mq.jms.p2p.P2PManager.receive(JMSConsumerObject, JMSMessageListener)'
     */
    public void testReceive() {

    }

    /*
     * Test method for 'org.netflexity.api.mq.jms.p2p.P2PManager.stop(JMSConsumerObject)'
     */
    public void testStop() {

    }

}
