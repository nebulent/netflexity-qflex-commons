/*
 *  2004 Netflexity, Ltd. All Rights Reserved.
 * 
 * CONFIDENTIAL BUSINESS INFORMATION
 * 
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND IS NOT TO BE
 * COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY OTHER PURPOSE,
 * UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION IS GIVEN.
 */
package org.netflexity.api.mq;

import java.io.Serializable;
import java.util.List;

/**
 * @author FEDORMAX
 *
 * Queue Browser interface.
 */
public interface MqQueueBrowser extends Serializable{

    /**
     * Retrieves all queue messages.
     * @param queueName
     * @return
     */
    List<MqMessage> getAllMessages(String queueName) throws MqException;

    /**
     * Retrieves messages that fit the range.
     * @param queueName
     * @param start
     * @param end
     * @return
     */
    List<MqMessage> getSelectedMessages(String queueName, int start, int end) throws MqException;

    /**
     * Retrieves full contents of the queue message.
     * 
     * @param queueName
     * @param messageId
     * @return
     */
    MqMessage getMessageById(String queueName, byte[] messageId) throws MqException;
    
    /**
     * Retrieves full contents of the queue message. Also converts hex string to bytes.
     * 
     * @param queueName
     * @param messageId
     * @return
     */
    MqMessage getMessageById(String queueName, String hexMessageId) throws MqException;
    
    /**
     * Retrieves all messages that are available and stores them polymorphically through
     * strategy instance.
     * 
     * @param queueName
     * @param hexMessageIds
     * @param strategy
     * @return
     */
    MqMessagePreserver getMessagesByIds(String queueName, String hexMessageIds[], MqMessagePreserver strategy) throws MqException;
    
    /**
     * Peek at a certain number of messages of the top of the queue.
     * 
     * @param queueName
     * @param howMany
     * @return
     */
    List<String> getMessageIds(String queueName, int howMany) throws MqException;
    
    /**
     * @param queueName
     * @param messageIds
     * @return
     */
    List<MqMessage> consumeMessagesByIds(String queueName, List<String> messageIds) throws MqException;
    
    /**
     * Consume a reply message from a dynamic queue.
     * 
     * @param requestQueueName
     * @param modelQueueName, for example, "SYSTEM.BROKER.MODEL.QUEUE"
     * @param waitInterval
     * @param message
     * @return
     * @throws MqException
     */
    MqMessage consumeRequest(String requestQueueName, String modelQueueName, int waitInterval, MqMessage message) throws MqException;
    
    /**
     * @param queueName
     * @param message
     * @throws MqException
     */
    void putMessage(String queueName, MqMessage message) throws MqException;
}