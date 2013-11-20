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

import org.netflexity.api.mq.jms.AbstractJMSXMLMessageListener;
import org.netflexity.api.mq.jms.JMSMessageListenerContext;

/**
 * @author MAX
 *
 */
public class DurSubNoneBlockingUnitTest extends PubSubManagerTest {
    
    /*
     * Test method for 'org.netflexity.api.mq.jms.pubsub.PubSubManager.subscribeDurableOnceNonBlocking(JMSConsumerObject, JMSMessageListenerContext, JMSMessageListener)'
     */
/*
    public void testSubscribeOnceDurableNoneBlocking() {
        JMSMessageListenerContext ctx = new JMSMessageListenerContext();
        try {
            // Subscribe to topic.
            manager.subscribeDurableOnceNonBlocking(consumerObject, new AbstractJMSXMLMessageListener(ctx) {
            
                public void onXmlMessage(String xml) {
                    setCompleted(true);
                    System.out.println("Received testSubscribeDurable: " + xml);
                }
            });
            
            // Wait a little.
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }
        }
        catch (JMSException e) {
            assertTrue(e.getMessage(), false);
        }
    }
 */
}
