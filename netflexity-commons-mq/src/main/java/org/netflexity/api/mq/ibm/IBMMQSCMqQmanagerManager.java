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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.netflexity.api.mq.MqChannel;
import org.netflexity.api.mq.MqException;
import org.netflexity.api.mq.MqQmanager;
import org.netflexity.api.mq.MqQueue;
import org.netflexity.api.mq.MqQueueStat;
import org.netflexity.api.mq.MqTopic;
import org.netflexity.api.mq.ibm.enums.MqChannelAttributeEnum;
import org.netflexity.api.mq.ibm.enums.MqQueueAttributeEnum;
import org.netflexity.api.util.StringConstants;

import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.constants.CMQC;

/**
 * Class QflexZosMqQmanager
 * 
 * Created on: Jun 10, 2004
 * 
 * @author issmma, FEDORMAX
 * @version
 */
public class IBMMQSCMqQmanagerManager extends AbstractIBMMqQmanagerManager {

    public static final String ALL_LOCAL_QUEUES_COMMAND = "DIS QLOCAL(*)";
    public static final String ALL_QUEUES_COMMAND = "DIS QLOCAL(*)";
    public static final String QUEUE = "QUEUE";
    public static final String CSQM451I = "CSQM451I";
    public static final String RESET_QSTATS = "RESET QSTATS";
    public static final String DIS_CHS = "DIS CHS";
    public static final String CURRENT_STATUS = "CURRENT STATUS";
    public static final String CHSTATUS = "CHSTATUS";
    public static final String REASON = "REASON";
    public static final String RETURN = "RETURN";
    public static final String COUNT = "COUNT";
    public static final String RETURN_VALUE_STR = "00000000";
    public static final String REASON_VALUE_STR = "00000000";
    public static final String ALL_CHANNELS_COMMAND = "DIS CHANNEL(*)";
    public static final String CHANNEL = "CHANNEL";

    private String requestQueueName;
    
    /**
     * @param qmanagerName
     * @param host
     * @param port
     * @param channelName
     * @param requestQueueName
     * @param replyQueueName
     * @throws MqException
     */
    public IBMMQSCMqQmanagerManager(String qmanagerName, String host, int port, String channelName, String requestQueueName, String replyQueueName) throws MqException {
        super(qmanagerName, host, port, channelName, replyQueueName);
        this.requestQueueName = requestQueueName;;
    }
    
    /**
     * @param qmanagerName
     * @param host
     * @param port
     * @param channelName
     * @param sslKeyStore
     * @param sslKeyStorePassword
     * @param sslTrustStore
     * @param sslTrustStorePassword
     * @param sslCipherSpec
     * @param sslPeerName
     * @param requestQueueName
     * @param replyQueueName
     * @throws MqException
     */
    public IBMMQSCMqQmanagerManager(String qmanagerName, String host, int port, String channelName, String sslKeyStore, String sslKeyStorePassword, String sslTrustStore, String sslTrustStorePassword, String sslCipherSpec, String sslPeerName, String requestQueueName, String replyQueueName) throws MqException {
        super(qmanagerName, host, port, channelName, replyQueueName, sslKeyStore, sslKeyStorePassword, sslTrustStore, sslTrustStorePassword,
                sslCipherSpec, sslPeerName);
        this.requestQueueName = requestQueueName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getAllQueues()
     */
    @Override
    public List<MqQueue> getAllQueues() throws MqException{
    	Set<MqQueue> queues = new HashSet<MqQueue>();
        try {
            byte[] mid = sendMQSCRequest(ALL_QUEUES_COMMAND);
            MQMessage replyMsg = getNextMessage(mid);
            String replyMsgContent = getMessageData(replyMsg);
            int responseLength = getResponseCount(replyMsgContent, mid);
            logger.info(ALL_QUEUES_COMMAND + ": " + replyMsgContent);
            
            for (int i = 0; i < responseLength - 2; i++) { // NOTE: why not responseLength - 2
                // Retrieve message content. 
                MQMessage msg = getNextMessage(mid);
                String msgContent = getMessageData(msg);
                logger.info("Content of message (" + (i + 1) + ") : " + msgContent);
                
                // Parse out queue names.
                String queueName = null;
                int lastCharPosition = 0;
                int firstCharPosition = msgContent.indexOf(QUEUE);
                if (firstCharPosition != -1) {
                    logger.info("First char position: " + firstCharPosition + " of record " + (i + 1));
                    firstCharPosition = firstCharPosition + 6;
                    lastCharPosition = msgContent.indexOf(StringConstants.RP, firstCharPosition);
                    queueName = msgContent.substring(firstCharPosition, lastCharPosition).trim();
                    logger.info("Retrieved queue: " + queueName);
                }
                
                // Collect queue names; filter unnecessary ones.
		if (StringUtils.isNotBlank(queueName)) {
		    // Some records have to be filtered.
		    if (!MQ_OBJECT_NAME_FILTER.filter(queueName, QUEUE_FILTERS)) {
			IBMMqQueue queue = new IBMMqQueue();
			queue.addAttribute(MqQueueAttributeEnum.Q_NAME, queueName.trim());
			queue.addAttribute(MqQueueAttributeEnum.Q_TYPE, 1);
			queues.add(queue);
			logger.info("Added queue: " + queueName);
		    }
		}
            }
        }
        catch (MQException mqe) {
            String errMessage = "Failed to get all queues for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
            logger.error(errMessage, mqe);
            throw new MqException(errMessage, mqe);
        } 
        catch (IOException io) {
            String errMessage = "Failed to write MQSC command [" + ALL_QUEUES_COMMAND + "] to qmanager: " + getQmanagerName();
            logger.error(errMessage, io);
            throw new MqException(errMessage, io);
        }
        return new ArrayList<MqQueue>(queues);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getQueueStats(java.lang.String[], java.lang.String[])
     */
    @Override
    public List<MqQueueStat> getQueueStats(String[] excludeQueueNames, String[] includeQueueNames) throws MqException {
        // Handle include queue names.
        String includeQueueNameExp = StringConstants.STAR;
        if(includeQueueNames != null && includeQueueNames.length > 0){
            includeQueueNameExp = includeQueueNames[0];
        }
        
        // Compose command string.
        String command = RESET_QSTATS + StringConstants.LP + includeQueueNameExp + StringConstants.RP;
        
        // Execute command and process results.
        List<MqQueueStat> queueStats = new ArrayList<MqQueueStat>();
        try {
            byte[] mid = sendMQSCRequest(command);
            MQMessage replyMsg = getNextMessage(mid);
            String replyMsgContent = getMessageData(replyMsg);
            logger.info(command + ": " + replyMsgContent);
            
            int responseLength = getResponseCount(replyMsgContent, mid);
            for (int i = 0; i < responseLength; i++) {
                // Retrieve message content. 
                MQMessage msg = getNextMessage(mid);
                String msgContent = getMessageData(msg);
                logger.info("Content of message (" + (i + 1) + ") : " + msgContent);
                
                // Parse out queue stat records.
                if (msgContent.indexOf(CSQM451I) != -1){
                	// Create and populate queue stat object.
                    MqQueueStat mqQueueStat = new IBMMqQueueStat();
                    String tmp = msgContent.substring(21, 69); // QUEUE NAME
                    if(StringUtils.isNotEmpty(tmp)){mqQueueStat.setQueueName(new String(tmp.trim()));}
                    tmp = msgContent.substring(80, 90); // TIME SINCE RESET
                    if(StringUtils.isNotEmpty(tmp)){mqQueueStat.setTimeSinceReset(new Long(tmp.trim()));}
                    tmp = msgContent.substring(101, 111); // HIGH DEPTH
                    if(StringUtils.isNotEmpty(tmp)){mqQueueStat.setHighDepth(new Integer(tmp.trim()));}
                    tmp = msgContent.substring(120, 130); // MSG IN
                    if(StringUtils.isNotEmpty(tmp)){mqQueueStat.setMsgEnqCount(new Integer(tmp.trim()));}
                    tmp = msgContent.substring(140, 150); // MSG OUT
                    if(StringUtils.isNotEmpty(tmp)){mqQueueStat.setMsgDeqCount(new Integer(tmp.trim()));}
                    
                    // Some records have to be filtered.
                    String [] queueStatsFilters = QUEUE_STATS_FILTERS;
    		        if(excludeQueueNames != null && excludeQueueNames.length > 0){
    		            queueStatsFilters = new String[QUEUE_STATS_FILTERS.length + excludeQueueNames.length];
    		            System.arraycopy(QUEUE_STATS_FILTERS, 0, queueStatsFilters, 0, QUEUE_STATS_FILTERS.length);
    		            System.arraycopy(excludeQueueNames, 0, queueStatsFilters, QUEUE_STATS_FILTERS.length, excludeQueueNames.length);
    		        }
                    if (!MQ_OBJECT_NAME_FILTER.filter(mqQueueStat.getQueueName(), queueStatsFilters)){
                        queueStats.add(mqQueueStat);
                    }
                }
            }
        }
        catch (MQException mqe) {
            String errMessage = "Failed to get queue stats for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
            logger.error(errMessage, mqe);
            throw new MqException(errMessage, mqe);
        } 
        catch (IOException io) {
            String errMessage = "Failed to write MQSC command [" + command + "] to qmanager: " + getQmanagerName();
            logger.error(errMessage, io);
            throw new MqException(errMessage, io);
        }
        return queueStats;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getAllChannels()
     */
    @Override
    public List<MqChannel> getAllChannels() throws MqException{
        MQQueue requestQueue = null;
        Set<MqChannel> channels = new HashSet<MqChannel>();
        try {
            byte[] mid = sendMQSCRequest(ALL_CHANNELS_COMMAND);
            MQMessage replyMsg = getNextMessage(mid);
            String replyMsgContent = getMessageData(replyMsg);
            int responseLength = getResponseCount(replyMsgContent, mid);
            logger.info(ALL_CHANNELS_COMMAND + ": " + replyMsgContent);
            
            for (int i = 0; i < responseLength - 2; i++) {
            	// Retrieve message content. 
                MQMessage msg = getNextMessage(mid);
                String msgContent = getMessageData(msg);
                logger.info("Content of message (" + (i + 1) + ") : " + msgContent);
                
                // Parse out the name of the channel.
                int firstCharPosition = 0;
                int lastCharPosition = 0;
                firstCharPosition = msgContent.indexOf(CHANNEL);
                String channelName = null;
                if (firstCharPosition != -1) {
                	logger.info("First char position: " + firstCharPosition + " of record " + (i + 1));
                    firstCharPosition = firstCharPosition + 8;
                    lastCharPosition = msgContent.indexOf(StringConstants.RP, firstCharPosition);
                    channelName = msgContent.substring(firstCharPosition, lastCharPosition).trim();
                    logger.info("Retrieved channel: " + channelName);
                } 
                
                // Create and populate channel object.
		if (StringUtils.isNotEmpty(channelName)) {
		    IBMMqChannel channel = new IBMMqChannel();
		    channel.addAttribute(MqChannelAttributeEnum.CHANNEL_NAME, channelName);
		    channel.addAttribute(MqChannelAttributeEnum.CHANNEL_TYPE, new Integer(1));
		    channels.add(channel);
		    logger.info("Added channel: " + channelName);
		}
            }
        }
        catch (MQException mqe) {
            String errMessage = "Failed to get all channels for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
            logger.error(errMessage, mqe);
            throw new MqException(errMessage, mqe);
        }
        catch (IOException io) {
            String errMessage = "Failed to write MQSC command [" + ALL_CHANNELS_COMMAND + "] to qmanager: " + getQmanagerName();
            logger.error(errMessage, io);
            throw new MqException(errMessage, io);
        }
        finally {
            closeQueue(requestQueue);
        }
        return new ArrayList(channels);
    }

    @Override
    public List<MqTopic> getAllTopics() throws MqException{
        throw new MqException("Not implemented exception");
    }

    @Override
    public MqTopic getTopic(String topicName) throws MqException{
            throw new MqException("Not implemented exception");
    }
    /* (non-Javadoc)
     * TODO: Not all channel attributes are populated.
     * @see org.netflexity.api.mq.MqQmanagerManager#getChannel(java.lang.String)
     */
    @Override
    public MqChannel getChannel(String channelName) throws MqException {
        String command = DIS_CHS + StringConstants.LP + channelName + StringConstants.RP;
        
        IBMMqObject channel = new IBMMqChannel();
        try {
            byte[] mid = sendMQSCRequest(command);
            MQMessage msg = getNextMessage(mid);
            String msgContent = getMessageData(msg);
            getResponseCount(msgContent, mid);

            msg = getNextMessage(mid);
            msgContent = getMessageData(msg);

            // Parse out channel status.
            int firstCharPosition = msgContent.indexOf(CHSTATUS) + 9;
            int lastCharPosition = msgContent.indexOf(StringConstants.LP, firstCharPosition);
            channelName = msgContent.substring(firstCharPosition, lastCharPosition);
            firstCharPosition = msgContent.indexOf(CURRENT_STATUS) + 15;
            lastCharPosition = msgContent.indexOf(StringConstants.LP, firstCharPosition);
            String channelStatus = msgContent.substring(firstCharPosition, lastCharPosition);
            //logger.info("Retrieved channel: " + channelName + " with status: " + channelStatus);
            
            // Set channel name.
            channel.addAttribute(MqChannelAttributeEnum.CHANNEL_NAME, channelName);
            
            // Set status.
            Integer channelStatusInteger = (Integer)(IBMPCFUtil.REVERSE_CHANNEL_STATUS_MAP.get(channelStatus));
            if(channelStatusInteger != null){
                channel.addAttribute(MqChannelAttributeEnum.CHANNEL_STATUS, channelStatusInteger);
            }
        }
        catch (MQException mqe) {
            String errMessage = "Failed to get channel (" + channelName + ") for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
            logger.error(errMessage, mqe);
            throw new MqException(errMessage, mqe);
        } 
        catch (IOException io) {
            String errMessage = "Failed to write MQSC command [" + command + "] to qmanager: " + getQmanagerName();
            logger.error(errMessage, io);
            throw new MqException(errMessage, io);
        }
        return (MqChannel)channel;
    }

    /* (non-Javadoc)
     * TODO: Not all queue attributes are populated.
     * @see org.netflexity.api.mq.MqQmanagerManager#getQueue(java.lang.String)
     */
    @Override
    public MqQueue getQueue(String queueName) throws MqException {
        MQQueue mqQueue = null;
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INQUIRE;
        IBMMqQueue q = new IBMMqQueue();
        try {
            mqQueue = connection.accessQueue(queueName, openOptions);
            q.addAttribute(MqQueueAttributeEnum.Q_NAME, queueName);
            q.addAttribute(MqQueueAttributeEnum.Q_TYPE, mqQueue.getQueueType());
            q.addAttribute(MqQueueAttributeEnum.MAX_Q_DEPTH, mqQueue.getMaximumDepth());
            q.addAttribute(MqQueueAttributeEnum.OPEN_INPUT_COUNT, mqQueue.getOpenInputCount());
            q.addAttribute(MqQueueAttributeEnum.OPEN_OUTPUT_COUNT, mqQueue.getOpenOutputCount());
            q.addAttribute(MqQueueAttributeEnum.CURRENT_Q_DEPTH, mqQueue.getCurrentDepth());
            q.addAttribute(MqQueueAttributeEnum.INHIBIT_GET, mqQueue.getInhibitGet());
            q.addAttribute(MqQueueAttributeEnum.INHIBIT_PUT, mqQueue.getInhibitPut());
        }
        catch (MQException mqe) {
            String errMessage = "Failed to get queue (" + queueName + ") for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
            logger.error(errMessage, mqe);
            throw new MqException(errMessage, mqe);
        }
        finally{
            closeQueue(mqQueue);
        }
        return (MqQueue)q;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getQmanager()
     */
    @Override
    public MqQmanager getQmanager() throws MqException {
        throw new NotImplementedException();
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#deleteChannel(java.lang.String)
     */
    @Override
    public void deleteChannel(String channelName) throws MqException {
        throw new NotImplementedException();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#deleteQueue(java.lang.String)
     */
    @Override
    public void deleteQueue(String queueName) throws MqException {
        throw new NotImplementedException();
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#setChannel(org.netflexity.api.mq.MqChannel)
     */
    @Override
    public void setChannel(MqChannel channel) throws MqException {
        throw new NotImplementedException();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#setQmanager(org.netflexity.api.mq.MqQmanager)
     */
    @Override
    public void setQmanager(MqQmanager qmanager) throws MqException {
        throw new NotImplementedException();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#setQueue(org.netflexity.api.mq.MqQueue)
     */
    @Override
    public void setQueue(MqQueue queue) throws MqException {
        throw new NotImplementedException();
    }

    /**
     * @param header
     * @param msgId
     * @return
     * @throws MqException
     * @throws MQException
     */
    private int getResponseCount(String header, byte[] msgId) throws MqException, MQException{
        int firstCharPosition = header.indexOf(RETURN) + 7;
        int lastCharPosition = firstCharPosition + 8;
        String returnString = new String(header.substring(firstCharPosition, lastCharPosition)).trim();
        if (returnString.compareTo(RETURN_VALUE_STR) != 0) {
            return 0;
        }
        firstCharPosition = header.indexOf(REASON) + 7;
        lastCharPosition = firstCharPosition + 8;
        String reasonString = new String(header.substring(firstCharPosition, lastCharPosition)).trim();
        firstCharPosition = header.indexOf(COUNT) + 6;
        lastCharPosition = firstCharPosition + 8;
        String countString = new String(header.substring(firstCharPosition, lastCharPosition)).trim();
        int countValue = new Integer(countString).intValue();
        if (reasonString.compareTo(REASON_VALUE_STR) == 0) {
            return countValue;
        } 
        else {
            for (int i = 0; i < countValue - 1; i++) {
                getNextMessage(msgId);
            }
            MQMessage replyMsg = getNextMessage(msgId);
            String replyMsgContent = getMessageData(replyMsg);
            countValue = getResponseCount(replyMsgContent, msgId);
        }
        
        logger.info("return value " + returnString);
        logger.info("reason value " + reasonString);
        logger.info("count value " + countString);

        return countValue;
    }
    
    /**
     * @param msg
     * @return
     */
    private String getMessageData(MQMessage msg) {
        String data = null;
        try {
            int msgLen = msg.getDataLength();
            data = msg.readStringOfByteLength(msgLen);
        } 
        catch (IOException ioe) {
            logger.error(ioe);
        }
        return data;
    }
    
    /**
     * @param msgId
     * @return
     * @throws MQException
     */
    private MQMessage getNextMessage(byte[] msgId) throws MqException, MQException{
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INPUT_SHARED;
        MQGetMessageOptions gmo = new MQGetMessageOptions();
        gmo.options = CMQC.MQGMO_FAIL_IF_QUIESCING | CMQC.MQGMO_NO_SYNCPOINT | CMQC.MQGMO_WAIT | CMQC.MQGMO_CONVERT;
        gmo.matchOptions = CMQC.MQMO_MATCH_CORREL_ID;
        gmo.waitInterval = 3000;
        
        // Make use of existing connection.
        MQMessage replyMsg = createResponseContainer(msgId);;
        if(connection != null && connection.isOpen()){
            MQQueue replyQueue = null;
            try{
                replyQueue = connection.accessQueue(replyQueueName, openOptions);
                replyQueue.get(replyMsg, gmo);
            }
            catch (MQException mqe) {
                throw mqe;
            } 
            finally {
                closeQueue(replyQueue);
            }
        }
        return replyMsg;
    }
    
    /**
     * @param id
     * @return
     */
    private MQMessage createResponseContainer(byte[] id) {
        MQMessage message = new MQMessage();
        message.correlationId = id;
        return message;
    }
    
    /**
     * @param command
     * @return
     * @throws MQException
     * @throws IOException
     */
    public byte[] sendMQSCRequest(String command) throws MqException, MQException, IOException{
        int openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_OUTPUT;
        MQPutMessageOptions pmo = new MQPutMessageOptions();
        pmo.options = CMQC.MQPMO_FAIL_IF_QUIESCING | CMQC.MQPMO_NO_SYNCPOINT;
        
        // Construct message.
        MQMessage requestMsg = new MQMessage();
        requestMsg.replyToQueueName = replyQueueName;
        requestMsg.format = CMQC.MQFMT_STRING;
        requestMsg.messageType = CMQC.MQMT_REQUEST;
        requestMsg.expiry = 10000;

        // Make use of existing connection.
        if(connection != null && connection.isOpen()){
            MQQueue requestQueue = null;
            try{
                requestMsg.writeString(command);
                requestQueue = connection.accessQueue(requestQueueName, openOptions);
                requestQueue.put(requestMsg);
            }
            catch (MQException mqe) {
                throw mqe;
            } 
            catch (IOException io) {
                throw io;
            }
            finally {
                closeQueue(requestQueue);
            }
        }
        return requestMsg.messageId;
    }

	/* (non-Javadoc)
	 * @see org.netflexity.api.mq.MqQmanagerManager#getTopology()
	 */
    @Override
    public List<MqChannel> getTopology() throws MqException {
	throw new MqException(new OperationNotSupportedException());
    }
}