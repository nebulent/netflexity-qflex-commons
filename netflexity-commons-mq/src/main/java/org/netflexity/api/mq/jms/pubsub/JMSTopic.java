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
package org.netflexity.api.mq.jms.pubsub;

import java.io.File;

import javax.jms.JMSException;
import javax.jms.Topic;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQTopic;

/**
 * @author FEDORMAX
 *
 * Abstraction of a real JMS Topic.
 */
public class JMSTopic{

    public static final String IBM_TOPIC_PREFIX = "topic://";
    public static final String IBM_BROKER_SUB_Q_PARAM = "?brokerDurSubQueue=";
    
    private Topic topic;
    private String brokerDurSubQueueName;
    private String topicName;
    private String qmanagerName;
    
    /**
     * @param qmanagerName
     * @param topic
     * @param brokerQueue
     * @param identifier
     */
    public JMSTopic(String qmanagerName, String topicName, String brokerDurSubQueueName) throws JMSException{
        this.brokerDurSubQueueName = brokerDurSubQueueName;
        this.qmanagerName = qmanagerName;
        this.topicName = qmanagerName + File.separator + topicName + File.separator + brokerDurSubQueueName;
        
        // Create MQ Series specific managed object(Topic) that supports Broker v2 Topic syntax.
        MQTopic mqTopic = new MQTopic();
        mqTopic.setBaseTopicName(topicName);
        mqTopic.setBrokerDurSubQueue(brokerDurSubQueueName);
        mqTopic.setBrokerVersion(JMSC.MQJMS_BROKER_V2);
        
        // Assign mq topic.
        this.topic = mqTopic;
    }
    
    /**
     * @return Returns the topic.
     */
    public Topic getTopic() {
        return topic;
    }
    
    /**
     * @return
     */
    public String getBrokerDurSubQueueName() {
        return brokerDurSubQueueName;
    }
    
    /**
     * @return
     */
    public String getTopicName() {
        return topicName;
    }
    
    /**
     * @return Returns the qmanagerName.
     */
    public String getQmanagerName() {
        return qmanagerName;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return qmanagerName + File.separator + topicName + File.separator + brokerDurSubQueueName;
    }
}
