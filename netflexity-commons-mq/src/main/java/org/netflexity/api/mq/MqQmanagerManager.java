/*
 *  2004 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.mq;

import java.io.Serializable;
import java.util.List;

import com.ibm.mq.MQMessage;

/**
 * Common interface for all types of AbstractMqQmanagerManager (PCF, MQSC)
 *
 * Created on: Sep 22, 2004
 * @author FEDORMAX
 */
public interface MqQmanagerManager extends MqQueueBrowser, Serializable {

    /**
     * Checks if command server is running, by reading Open Input Count
     * of the command queue.
     * 
     * @return
     * @throws MqException
     */
    boolean isCommandServerOpen() throws MqException;

    /**
     * Checks if connection to qmanager is established.
     * 
     * @return boolean
     */
    boolean isOpen() throws MqException;

    /**
     * @param queueName
     * @return MqQueue
     * @throws MqException
     */
    MqQueue getQueueDetails(String queueName) throws MqException;

    /**
     * Close qmanager connection.
     * 
     * @throws MqException
     */
    void close() throws MqException;

    /**
     * Returns a list of Queue Stat records, excluding queues contained in excludeQueueNames,
     * although statistical data for all queues will be reset.
     * 
     * @param excludeQueueNames - array of expressions to filter out
     * @param includeQueueNames - array of expressions to include
     * @return
     * @throws MqException
     */
    List<MqQueueStat> getQueueStats(String[] excludeQueueNames, String[] includeQueueNames) throws MqException;

    /**
     * Returns a unique list of queue objects of all types for a 
     * given queue manager.
     * 
     * @return List of queues
     */
    List<MqQueue> getAllQueues() throws MqException;

    /**
     * Returns a list of Channel objects of any type that exist for given queue manager.
     * @return List of channels
     */
    List<MqChannel> getAllChannels() throws MqException;

    List<MqTopic> getAllTopics() throws MqException;

    MqTopic getTopic(String topicName) throws MqException;

    /**
     * @return
     * @throws MqException
     */
    List<MqChannel> getTopology() throws MqException;

    /**
     * Returns qmanager with all attributes.
     * 
     * @return
     * @throws MqException
     */
    MqQmanager getQmanager() throws MqException;

    /**
     * Returns queue with all attributes populated by queue name.
     * 
     * @param queueName
     * @return
     * @throws MqException
     */
    MqQueue getQueue(String queueName) throws MqException;

    /**
     * @param queueName
     * @throws MqException
     */
    void deleteQueue(String queueName) throws MqException;

    /**
     * Returns channel with all attributes populated by channel name.
     * 
     * @param channelName
     * @return
     * @throws MqException
     */
    MqChannel getChannel(String channelName) throws MqException;

    /**
     * @param channelName
     * @throws MqException
     */
    void deleteChannel(String channelName) throws MqException;


    List<MQMessage> getConfigChangeEventMessages() throws MqException;

//    List<MQMessage> browseAndMarkQueueMessages(String queueName, int batchSize) throws MqException;
    
    List<MQMessage> consumeMessages(String queueName, int batchSize) throws MqException;


    /**
     * Change queue manager attributes.
     * 
     * @param qmanager
     * @throws MqException
     */
    void setQmanager(MqQmanager qmanager) throws MqException;

    /**
     * Change queue attributes.
     * 
     * @param queue
     * @throws MqException
     */
    void setQueue(MqQueue queue) throws MqException;

    /**
     * Change channel attributes.
     * 
     * @param channel
     * @throws MqException
     */
    void setChannel(MqChannel channel) throws MqException;
}
