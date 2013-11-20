/*
 *  2005 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.mq.jms.p2p;

import java.io.File;

import javax.jms.JMSException;
import javax.jms.Queue;

import com.ibm.mq.jms.MQQueue;

/**
 * @author FEDORMAX
 *
 * Abstraction of real JMS Queue.
 */
public class JMSQueue{

    public static final String IBM_QUEUE_PREFIX = "queue://";
    
    private Queue queue;
    private String queueId;
    private String qmanagerName;
    private String queueName;
    
    /**
     * @param qmanagerName
     * @param queueName
     */
    public JMSQueue(String qmanagerName, String queueName) throws JMSException{
        this.qmanagerName = qmanagerName;
        this.queueName = queueName;
        this.queueId = qmanagerName + File.separator + queueName;
        this.queue = new MQQueue(queueName);
    }
    
    /**
     * @return
     */
    public String getQueueName(){
        return queueName;
    }
    
    /**
     * @return
     */
    public String getQueueId() {
        return queueId;
    }
    /**
     * @return
     */
    public Queue getQueue() {
        return queue;
    }
    
    /**
     * @return Returns the qmanagerName.
     */
    public String getQmanagerName() {
        return qmanagerName;
    }
    
    /**
	 * Convert queue name to internal JMS provider queue name.
	 * 
	 * @param queueName
	 * @return
	 */
	public static String createDestinationName(String queueName){
		return IBM_QUEUE_PREFIX + queueName;
	}
}
