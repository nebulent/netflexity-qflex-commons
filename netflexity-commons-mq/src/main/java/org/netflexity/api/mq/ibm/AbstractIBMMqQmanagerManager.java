/*
 *  2004 Netflexity, Ltd. All Rights Reserved.
 * 
 * CONFIDENTIAL BUSINESS INFORMATION
 * 
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND IS NOT TO BE
 * COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY OTHER PURPOSE,
 * UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION IS GIVEN.
 */
package org.netflexity.api.mq.ibm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.netflexity.api.mq.MqException;
import org.netflexity.api.mq.MqMessage;
import org.netflexity.api.mq.MqMessageHeader;
import org.netflexity.api.mq.MqMessagePreserver;
import org.netflexity.api.mq.MqQmanagerManager;
import org.netflexity.api.mq.MqQueue;
import org.netflexity.api.mq.ibm.enums.MqQueueAttributeEnum;
import org.netflexity.api.mq.ibm.pool.IBMMqConnectionPool;
import org.netflexity.api.mq.ibm.pool.IBMMqConnectionPoolFactory;
import org.netflexity.api.mq.ibm.pool.IBMMqConnectionPoolFactoryImpl;
import org.netflexity.api.mq.ibm.pool.PoolableKey;

import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

/**
 * Abstract MQQmanager class that contains most common functionality.
 * 
 * Created on: Jun 10, 2004
 * 
 * @author issmma, FEDORMAX
 * @version 
 */
public abstract class AbstractIBMMqQmanagerManager implements MqQmanagerManager{

    public static final String QUEUE_FILTERS[] = {"COM.IBM.MQ.PCF.PCFAGENT", "AMQ", "MQAI"};
    public static final  String QUEUE_STATS_FILTERS[] = {"COM.IBM.MQ.PCF.PCFAGENT", "AMQ"};
    
    protected static final MqObjectNameFilter MQ_OBJECT_NAME_FILTER = new MqObjectNameFilterImpl();
    protected Logger logger = Logger.getLogger(this.getClass().getName());
    
    // Qmanager info.
    protected String qmanagerName;
    protected String host;
    protected int port;
    protected String channelName;
    protected String replyQueueName;
    
    // SSL info.
    protected String sslKeyStore, sslKeyStorePassword;
    protected String sslTrustStore, sslTrustStorePassword;
    protected String sslCipherSpec;
    protected String sslPeerName;
    
    protected MQQueueManager connection;
    
    /**
     * @param qmanagerName
     * @param host
     * @param port
     * @param channelName
     * @param replyQueueName
     * @throws MqException
     */
    public AbstractIBMMqQmanagerManager(String qmanagerName, String host, int port, String channelName, String replyQueueName) throws MqException{
        this.qmanagerName = qmanagerName;
        this.host = host;
        this.port = port;
        this.channelName = channelName;
        this.replyQueueName = replyQueueName;
        
        // Connect to qmanager.
        PoolableKey key = new PoolableKey(qmanagerName, host, port, channelName);
        connection = getPooledConnection(key);
        if (connection != null && connection.isConnected()) {
            logger.info("Connected to queue manager :" + qmanagerName);
        }
    }
    
    /**
     * @param qmanagerName
     * @param host
     * @param port
     * @param channelName
     * @param replyQueueName
     * @param sslKeyStore
     * @param sslKeyStorePassword
     * @param sslTrustStore
     * @param sslTrustStorePassword
     * @param sslCipherSpec
     * @param sslPeerName
     * @throws MqException
     */
    public AbstractIBMMqQmanagerManager(String qmanagerName, String host, int port, String channelName, String replyQueueName, String sslKeyStore, String sslKeyStorePassword, String sslTrustStore, String sslTrustStorePassword, String sslCipherSpec, String sslPeerName) throws MqException{
        this.qmanagerName = qmanagerName;
        this.host = host;
        this.port = port;
        this.channelName = channelName;
        this.replyQueueName = replyQueueName;
        this.sslKeyStore = sslKeyStore;
        this.sslKeyStorePassword = sslKeyStorePassword;
        this.sslTrustStore = sslTrustStore;
        this.sslTrustStorePassword = sslTrustStorePassword;
        this.sslCipherSpec = sslCipherSpec;
        this.sslPeerName = sslPeerName;
        
        // Create a pool of connections to qmanager.
        PoolableKey key = new PoolableKey(qmanagerName, host, port, channelName, sslKeyStore, sslKeyStorePassword, sslTrustStore, sslTrustStorePassword, sslCipherSpec, sslPeerName);
        connection = getPooledConnection(key);
        if (connection != null && connection.isConnected()) {
            logger.info("Connected to queue manager through SSL:" + qmanagerName);
        }
    }

    /**
     * Create a pool of connections to specified qmanager.
     * 
     * @param key
     * @return
     */
    private MQQueueManager getPooledConnection(PoolableKey key) throws MqException{
        /******** NOTE: Consider this to provide pooling capabilities.
        MQSimpleConnectionManager myConnMan=new MQSimpleConnectionManager();
        myConnMan.setActive(MQSimpleConnectionManager.MODE_ACTIVE);
        MQQueueManager qmgr=new MQQueueManager("my.qmgr.1", myConnMan);
        */
        IBMMqConnectionPoolFactory factory = IBMMqConnectionPoolFactoryImpl.getFactory();
        IBMMqConnectionPool pool = factory.createMqConnectionPool();
        MQQueueManager connection = (MQQueueManager)pool.borrowMqConnection(key);
        return connection;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#close()
     */
    @Override
    public void close() throws MqException {
        try {
            connection.disconnect();
        }
        catch (MQException mqe) {
            throw new MqException(mqe.getMessage(), mqe);
        }
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#isOpen()
     */
    @Override
    public boolean isOpen() throws MqException {
        return (connection != null && connection.isConnected());
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#isCommandServerOpen()
     */
    @Override
    public boolean isCommandServerOpen() throws MqException {
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INQUIRE;
        MQQueue queue = null;
        try {
            queue = connection.accessQueue(connection.getCommandInputQueueName(), openOptions);
            return (queue.getOpenInputCount() > 0);
        }
        catch (MQException e) {
            String errMessage = "Failed to get open input count for command server queue.";
            logger.error(errMessage, e);
            throw new MqException(errMessage, e);
        }
        finally {
            closeQueue(queue);
        }
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueueBrowser#getAllMessages(java.lang.String)
     */
    @Override
    public List<MqMessage> getAllMessages(String queueName) throws MqException{
        int currentQueueDepth = 0;
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INQUIRE;
        MQQueue queue = null;
        try {
            queue = connection.accessQueue(queueName, openOptions);
            currentQueueDepth = queue.getCurrentDepth();
        } 
        catch (MQException e) {
            String errMessage = "Failed to get all messages for queue: " + queueName;
            logger.error(errMessage, e);
            throw new MqException(errMessage, e);
        }
        finally {
            closeQueue(queue);
        }
        return getSelectedMessages(queueName, 0, currentQueueDepth);
    } 

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueueBrowser#getSelectedMessages(java.lang.String, int, int)
     */
    @Override
    public List<MqMessage> getSelectedMessages(String queueName, int start, int end) throws MqException{
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_BROWSE | CMQC.MQOO_INQUIRE;

        MQGetMessageOptions gmo = new MQGetMessageOptions();
        gmo.options = CMQC.MQGMO_BROWSE_NEXT | CMQC.MQGMO_FAIL_IF_QUIESCING | CMQC.MQGMO_ACCEPT_TRUNCATED_MSG | CMQC.MQGMO_CONVERT;

        MQQueue queue = null;
        List<MqMessage> messages = new ArrayList<MqMessage>();
        try {
            queue = connection.accessQueue(queueName, openOptions);
            int currentQueueDepth = queue.getCurrentDepth();
            if (start > currentQueueDepth){ 
                return messages; 
            }

            for (int i = 0; i < start; i++) {
                MQMessage msg = new MQMessage();
                try {
                    queue.get(msg, gmo, 0);
                } 
                catch (MQException e) {
                    if (e.reasonCode == MQException.MQRC_TRUNCATED_MSG_ACCEPTED) {
                        // it is ok.
                    }
                    else if (e.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) { 
                        return messages;
                    }
                    else {
                        String errMessage = "Failed to read messages from queue :" + queueName + " on range[0 - " + (start - 1) + "]";
                        logger.error(errMessage, e);
                        throw new MqException(errMessage, e);
                    }
                }
            }

            // Make sure we return all records if end is = 0.
            if (end == 0){
                end = currentQueueDepth;
            }
            
            // Iterate through the rest of messages.
            for (int i = start; i < end; i++) {
                MQMessage msg = new MQMessage();
                try {
                    queue.get(msg, gmo, 80);
                    messages.add(new IBMMqMessage(msg));
                } 
                catch (MQException e) {
                    if (e.reasonCode == MQException.MQRC_TRUNCATED_MSG_ACCEPTED) {
                        messages.add(new IBMMqMessage(msg));
                    }
                    else if (e.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) { 
                        return messages;
                    }
                    else {
                        String errMessage = "Failed to read messages from queue :" + queueName + " on range[" + start + " - " + (end - 1) + "]";
                        logger.error(errMessage, e);
                        throw new MqException(errMessage, e);
                    }
                }
            }
        } 
        catch (MQException e) {
            String errMessage = "Failed to get selected messages for queue: " + queueName + " on range(" + start + "," + end + ")";
            logger.error(errMessage, e);
            throw new MqException(errMessage, e);
        } 
        finally {
            closeQueue(queue);
        }
        return messages;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueueBrowser#getMessageById(java.lang.String, java.lang.String)
     */
    @Override
    public MqMessage getMessageById(String queueName, String hexMessageId) throws MqException{
        return getMessageById(queueName, IBMPCFUtil.toBytes(hexMessageId));
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueueBrowser#getMessageById(java.lang.String, byte[])
     */
    @Override
    public MqMessage getMessageById(String queueName, byte[] messageId) throws MqException{
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_BROWSE | CMQC.MQOO_INQUIRE;
        MQGetMessageOptions gmo = new MQGetMessageOptions();
        gmo.options = CMQC.MQGMO_BROWSE_NEXT | CMQC.MQGMO_FAIL_IF_QUIESCING | CMQC.MQGMO_CONVERT;

        // Set message id.
        MQMessage msg = new MQMessage();
        msg.messageId = messageId;
        
        MQQueue queue = null;
        try {
            queue = connection.accessQueue(queueName, openOptions);

            // Obtain a message by id.
            queue.get(msg, gmo);
            MqMessage message = new IBMMqMessage(msg);
            return message;
        }
        catch (MQException e) {
            if (e.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
                String errMessage = "Message [" + new String(messageId) + "] is no longer on queue: " + queueName;
                logger.error(errMessage, e);
                return null;
            }
            else {
                String errMessage = "Failed to get message [" + new String(messageId) + "] from queue: " + queueName;
                logger.error(errMessage, e);
                throw new MqException(errMessage, e);
            }
        } 
        finally {
            closeQueue(queue);
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueueBrowser#getMessagesByIds(java.lang.String, java.lang.String[], org.netflexity.api.mq.MqMessagePreserver)
     */
    @Override
    public MqMessagePreserver getMessagesByIds(String queueName, String hexMessageIds[], MqMessagePreserver strategy) throws MqException{
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_BROWSE | CMQC.MQOO_INQUIRE;
        MQGetMessageOptions gmo = new MQGetMessageOptions();
        gmo.options = CMQC.MQGMO_BROWSE_NEXT | CMQC.MQGMO_FAIL_IF_QUIESCING | CMQC.MQGMO_CONVERT;

        MQQueue queue = null;
        try {
            queue = connection.accessQueue(queueName, openOptions);
            for (int i = 0; i < hexMessageIds.length; i++) {
                MQMessage msg = new MQMessage();
                msg.messageId = IBMPCFUtil.toBytes(hexMessageIds[i]);
                
                // Obtain a message by id.
                try {
                    queue.get(msg, gmo);
                    
                    // Store message in a polymorphic way.
                    strategy.store(msg);
                } 
                catch (MQException mqe) {
                    if (mqe.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
                        String errMessage = "Message [" + hexMessageIds[i] + "] is no longer on queue: " + queueName;
                        logger.error(errMessage, mqe);
                    }
                }
            }
        }
        catch (MQException e) {
            String errMessage = "Failed to get " + hexMessageIds.length + " messages from queue: " + queueName;
            logger.error(errMessage, e);
            throw new MqException(errMessage, e);
        } 
        finally {
            closeQueue(queue);
        }
        return strategy;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueueBrowser#consumeMessagesByIds(java.lang.String, java.util.List)
     */
    @Override
    public List<MqMessage> consumeMessagesByIds(String queueName, List<String> messageIds) throws MqException{
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INPUT_AS_Q_DEF;
        MQGetMessageOptions gmo = new MQGetMessageOptions();

        MQQueue queue = null;
        List<MqMessage> messages = new ArrayList<MqMessage>(messageIds.size());
        try {
            queue = connection.accessQueue(queueName, openOptions);
            for (String msgId : messageIds) {
                // Set message id.
                MQMessage msg = new MQMessage();
                msg.messageId = IBMPCFUtil.toBytes(msgId);
                
                // Consume a message by id.
                try {
                    queue.get(msg, gmo);
                    messages.add(new IBMMqMessage(msg));
                } 
                catch (MQException mqe) {
                    if (mqe.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
                        String errMessage = "Message [" + msgId + "] is no longer on queue: " + queueName;
                        logger.error(errMessage, mqe);
                    }
                }
            }
        }
        catch (MQException e) {
            String errMessage = "Failed to consume " + messageIds.size() + " messages from queue: " + queueName;
            logger.error(errMessage, e);
            throw new MqException(errMessage, e);
        } 
        finally {
            closeQueue(queue);
        }
        return messages;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueueBrowser#getMessageIds(java.lang.String, int)
     */
    @Override
    public List<String> getMessageIds(String queueName, int howMany) throws MqException{
        List<MqMessage> messages = getSelectedMessages(queueName, 0, howMany);
        List<String> messageIds = new ArrayList<String>(messages.size());
	for(MqMessage msg : messages){
            messageIds.add(msg.getId());
        }
        return messageIds;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueueBrowser#consumeRequest(java.lang.String, java.lang.String, int, org.netflexity.api.mq.MqMessage)
     */
    @Override
    public MqMessage consumeRequest(String requestQueueName, String modelQueueName, int waitInterval, MqMessage message) throws MqException{
        int replyOpenOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INPUT_SHARED;
        MQGetMessageOptions gmo = new MQGetMessageOptions();
        gmo.options = CMQC.MQGMO_WAIT;
        gmo.waitInterval = waitInterval;
        
        MQQueue replyQueue = null;
        try{
            replyQueue = connection.accessQueue(modelQueueName, replyOpenOptions);
            MqMessageHeader header = message.getMessageHeader();
            header.setReplyToQueueName(replyQueue.getName());
            header.setReplyToQmanagerName(qmanagerName);
            
            // Put message on a queue with type request.
            putMessage(requestQueueName, message);
            
            // Consume reply message from dynamic queue.
            MQMessage msg = new MQMessage();
            try {
                replyQueue.get(msg, gmo);
                return new IBMMqMessage(msg);
            } 
            catch (MQException mqe) {
                if (mqe.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
                    String errMessage = "No messages found on queue: " + header.getReplyToQueueName();
                    logger.error(errMessage, mqe);
                }
            }
        }
        catch (MQException e) {
            String errMessage = "Failed to consume reply message from queue: " + modelQueueName;
            logger.error(errMessage, e);
            throw new MqException(errMessage, e);
        } 
        finally {
            closeQueue(replyQueue);
        }
        
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueueBrowser#putMessage(java.lang.String, org.netflexity.api.mq.MqMessage)
     */
    @Override
    public void putMessage(String queueName, MqMessage message) throws MqException{
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_OUTPUT;
        
        // Set put options.
        MQPutMessageOptions pmo = new MQPutMessageOptions();
        pmo.options = CMQC.MQPMO_LOGICAL_ORDER | CMQC.MQPMO_NEW_MSG_ID; //  | MQC.MQPMO_SYNCPOINT | MQC.MQPMO_FAIL_IF_QUIESCING
        
        MQQueue queue = null;
        try {
            queue = connection.accessQueue(queueName, openOptions);
            MQMessage msg = new MQMessage();
            MqMessageHeader header = message.getMessageHeader();
            if(StringUtils.isNotBlank(header.getCorrelationId())){
                msg.correlationId = IBMPCFUtil.toBytes(header.getCorrelationId());
            }
            if(header.getMessageType() != null){
                msg.messageType = header.getMessageType().getIntValue();
            }
            if(header.getPersistence() != null){
                msg.persistence = header.getPersistence().getIntValue();
            }
            if(StringUtils.isNotBlank(header.getMessageFormat())){
                msg.format = header.getMessageFormat();
            }
            else{
                msg.format = CMQC.MQFMT_STRING;
            }
            if(header.getExpiry() != null){
                msg.expiry = header.getExpiry().intValue();
            }
            if(StringUtils.isNotBlank(header.getReplyToQmanagerName())){
                msg.replyToQueueManagerName = header.getReplyToQmanagerName();
            }
            if(StringUtils.isNotBlank(header.getReplyToQueueName())){
                msg.replyToQueueName = header.getReplyToQueueName();
            }
            if(StringUtils.isNotBlank(message.getData())){
                try {
                    msg.writeString(message.getData());
                }
                catch (IOException e) {
                    String errMessage = "Failed to add content to message during put to queue: " + queueName + " due to " + e.getMessage();
                    logger.error(errMessage, e);
                    throw new MqException(errMessage, e);
                }
            }
            
            // Put a message.
            queue.put(msg, pmo);
        }
        catch (MQException e) {
            String errMessage = "Failed to put message on queue: " + queueName + " due to " + e.getMessage();
            logger.error(errMessage, e);
            throw new MqException(errMessage, e);
        } 
        finally {
            closeQueue(queue);
        }
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getQueueDetails(java.lang.String)
     * 
     * Not all the properties exist for CLUSTER queues. Please refer to
     * http://publib.boulder.ibm.com/infocenter/wmqv6/v6r0/index.jsp?topic=/com.ibm.mq.csqzac.doc/pc12970_.htm
     */
    @Override
    public MqQueue getQueueDetails(String queueName) throws MqException{
    	IBMMqQueue queue = new IBMMqQueue();
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INQUIRE;
        MQQueue mqQueue = null;
        try {
        	mqQueue = connection.accessQueue(queueName, openOptions);
        	
        	// Retrieve and set all live (dynamic) queue attributes, along with some static ones.
            queue.addAttribute(MqQueueAttributeEnum.Q_NAME, queueName);
            if(mqQueue.getQueueType() != CMQC.MQQT_CLUSTER){
                queue.addAttribute(MqQueueAttributeEnum.OPEN_INPUT_COUNT, mqQueue.getOpenInputCount());
                queue.addAttribute(MqQueueAttributeEnum.OPEN_OUTPUT_COUNT, mqQueue.getOpenOutputCount());
                queue.addAttribute(MqQueueAttributeEnum.CURRENT_Q_DEPTH, mqQueue.getCurrentDepth());
                queue.addAttribute(MqQueueAttributeEnum.MAX_Q_DEPTH, mqQueue.getMaximumDepth());
                queue.addAttribute(MqQueueAttributeEnum.INHIBIT_GET, mqQueue.getInhibitGet());
            }
            queue.addAttribute(MqQueueAttributeEnum.INHIBIT_PUT, mqQueue.getInhibitPut());
        } 
        catch (MQException e) {
            String errMessage = "Failed to get all messages for queue: " + queueName;
            logger.error(errMessage, e);
            throw new MqException(errMessage, e);
        }
        finally {
            closeQueue(mqQueue);
        }
        return queue;
    }
    
    /**
     * @param queue
     * @throws MqException
     */
    protected void closeQueue(MQQueue queue) throws MqException{
        try {
            if (queue != null && queue.isOpen()) {
                queue.close();
            }
        } 
        catch (MQException mqe) {
            throw new MqException(mqe.getMessage(), mqe);
        }
    }

    //get EVENT MSGS start here
    @Override
    public List<MQMessage> getConfigChangeEventMessages() throws MqException{
//        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_BROWSE | CMQC.MQOO_INQUIRE;

        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_BROWSE | CMQC.MQOO_INQUIRE;

        int currentQueueDepth = 0;
        MQGetMessageOptions gmo = new MQGetMessageOptions();
//        gmo.options = CMQC.MQGMO_BROWSE_NEXT | CMQC.MQGMO_FAIL_IF_QUIESCING | CMQC.MQGMO_ACCEPT_TRUNCATED_MSG | CMQC.MQGMO_CONVERT;

        MQQueue queue = null;
        List<MQMessage> messages = new ArrayList<MQMessage>();
        try {
            queue = connection.accessQueue("SYSTEM.ADMIN.CONFIG.EVENT", openOptions);
            currentQueueDepth = queue.getCurrentDepth();
            for (int i = 0; i < currentQueueDepth; i++) {
                MQMessage msg = new MQMessage();
                try {
                    queue.get(msg, gmo, 8000);
                    messages.add(msg);
                }
                catch (MQException e) {
                    if (e.reasonCode == MQException.MQRC_TRUNCATED_MSG_ACCEPTED) {
                        messages.add(msg);
                    }
                    else if (e.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
                        return messages;
                    }
                    else {
                        String errMessage = "Failed to read messages from queue :" + "SYSTEM.ADMIN.CONFIG.EVENT" + " on range[" + 0 + " - " + (currentQueueDepth - 1) + "]";
                        logger.error(errMessage, e);
                        throw new MqException(errMessage, e);
                    }
                }
            }
        }
        catch (MQException e) {
            String errMessage = "Failed to get selected messages for queue: " + "SYSTEM.ADMIN.CONFIG.EVENT" + " on range(" + 0 + "," + currentQueueDepth + ")";
            logger.error(errMessage, e);
            throw new MqException(errMessage, e);
        }
        finally {
            closeQueue(queue);
        }
        return messages;
    }

    @Override
    public List<MQMessage> consumeMessages(String queueName, int batchSize) throws MqException {
	int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INPUT_AS_Q_DEF;
	MQGetMessageOptions gmo = new MQGetMessageOptions();
	MQQueue queue = null;
	MQMessage msg = null;
	boolean readAll = batchSize == 0;
	List<MQMessage> messages = new ArrayList<MQMessage>(readAll ? 50 : batchSize);
	try {
	    queue = connection.accessQueue(queueName, openOptions);
	    while (batchSize > 0) {
//		System.out.println("read loop: " + batchSize + ", readAll: " + readAll);
		msg = new MQMessage();
		queue.get(msg, gmo);
		messages.add(msg);
		batchSize--;
	    }
	} catch (MQException mqe) {
	    if (mqe.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
		return messages;
	    } else if (mqe.reasonCode == MQException.MQRC_TRUNCATED_MSG_ACCEPTED) {
		messages.add(msg);
	    } else {
		String errMessage = "Failed to get messages for queue: " + queueName;
		logger.error(errMessage, mqe);
		throw new MqException(errMessage, mqe);
	    }
	} finally {
	    closeQueue(queue);
	}
	return messages;
    }

//    public List<MQMessage> browseAndMarkQueueMessages(String queueName, int batchSize) throws MqException {
//
//	int openOptions = CMQC.MQOO_BROWSE | /*CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_INQUIRE |*/ CMQC.MQOO_CO_OP;
//
//	MQGetMessageOptions gmo = new MQGetMessageOptions();
//	gmo.options = CMQC.MQGMO_BROWSE_HANDLE |/* CMQC.MQGMO_FAIL_IF_QUIESCING | CMQC.MQGMO_ACCEPT_TRUNCATED_MSG | CMQC.MQGMO_CONVERT |*/ CMQC.MQGMO_UNMARKED_BROWSE_MSG | CMQC.MQGMO_MARK_BROWSE_CO_OP | CMQC.MQGMO_MARK_BROWSE_HANDLE;
//
//	MQQueue queue = null;
//	MQMessage msg = null;
//	boolean readAll = batchSize == 0;
//	List<MQMessage> messages = new ArrayList<MQMessage>(readAll ? 10 : batchSize);
//	try {
//	    queue = connection.accessQueue(queueName, openOptions);
//	    while (true) {
//		System.out.println("read loop: " + batchSize + ", readAll: " + readAll);
//		msg = new MQMessage();
////		queue.get(msg, gmo, 8000);
//		queue.get(msg, gmo);
//		messages.add(msg);
////		batchSize--;
////		if (batchSize < 0 || !readAll) {
////		    System.out.println("break on batch");
////		    break;
////		}
//	    }
//	} catch (MQException mqe) {
//	    if (mqe.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
//		System.out.println("browsed all messages");
//		return messages;
//	    } else if (mqe.reasonCode == MQException.MQRC_TRUNCATED_MSG_ACCEPTED) {
//		messages.add(msg);
//	    } else {
//		String errMessage = "Failed to get messages for queue: " + queueName;
//		logger.error(errMessage, mqe);
//		throw new MqException(errMessage, mqe);
//	    }
//	} finally {
//	    closeQueue(queue);
//	}
//	return messages;
//    }

    /**
     * @return Returns the channelName.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @return Returns the host.
     */
    public String getHost() {
        return host;
    }

    /**
     * @return Returns the port.
     */
    public int getPort() {
        return port;
    }

    /**
     * @return Returns the qmanagerName.
     */
    public String getQmanagerName() {
        return qmanagerName;
    }

    /**
     * @return the sslCipherSpec
     */
    public String getSslCipherSpec() {
        return sslCipherSpec;
    }

    /**
     * @return the sslKeyStore
     */
    public String getSslKeyStore() {
        return sslKeyStore;
    }

    /**
     * @return the sslKeyStorePassword
     */
    public String getSslKeyStorePassword() {
        return sslKeyStorePassword;
    }

    /**
     * @return the sslPeerName
     */
    public String getSslPeerName() {
        return sslPeerName;
    }

    /**
     * @return the sslTrustStore
     */
    public String getSslTrustStore() {
        return sslTrustStore;
    }

    /**
     * @return the sslTrustStorePassword
     */
    public String getSslTrustStorePassword() {
        return sslTrustStorePassword;
    }

}