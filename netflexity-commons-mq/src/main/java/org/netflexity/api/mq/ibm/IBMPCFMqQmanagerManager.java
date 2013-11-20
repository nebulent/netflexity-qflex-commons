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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.mq.MqChannel;
import org.netflexity.api.mq.MqException;
import org.netflexity.api.mq.MqQmanager;
import org.netflexity.api.mq.MqQueue;
import org.netflexity.api.mq.ibm.enums.AbstractMqAttributeEnum;
import org.netflexity.api.mq.ibm.enums.MqAuthRecordAttributeEnum;
import org.netflexity.api.mq.ibm.enums.MqChannelAttributeEnum;
import org.netflexity.api.mq.ibm.enums.MqQmanagerAttributeEnum;
import org.netflexity.api.mq.ibm.enums.MqQueueAttributeEnum;
import org.netflexity.api.util.StringConstants;

import com.ibm.mq.MQException;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.constants.CMQXC;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.pcf.MQCFIL;
import com.ibm.mq.pcf.MQCFIN;
import com.ibm.mq.pcf.MQCFSL;
import com.ibm.mq.pcf.MQCFST;
import com.ibm.mq.pcf.PCFAgent;
import com.ibm.mq.pcf.PCFException;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
import com.ibm.mq.pcf.PCFParameter;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.netflexity.api.mq.MqQueueStat;
import org.netflexity.api.mq.MqTopic;
import org.netflexity.api.mq.ibm.enums.MqTopicAttributeEnum;

/**
 * Class QflexUnixMqQmanager
 * 
 * Created on: Jun 10, 2004
 * 
 * @author issmma, FEDORMAX
 * @version 
 */
public class IBMPCFMqQmanagerManager extends AbstractIBMMqQmanagerManager {

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
    public IBMPCFMqQmanagerManager(String qmanagerName, String host, int port, String channelName, String replyQueueName, String sslKeyStore, String sslKeyStorePassword, String sslTrustStore, String sslTrustStorePassword, String sslCipherSpec, String sslPeerName) throws MqException {
        super(qmanagerName, host, port, channelName, replyQueueName, sslKeyStore, sslKeyStorePassword, sslTrustStore,
                sslTrustStorePassword, sslCipherSpec, sslPeerName);
    }

    /**
     * @param qmanagerName
     * @param host
     * @param port
     * @param channelName
     * @param replyQueueName
     * @throws MqException
     */
    public IBMPCFMqQmanagerManager(String qmanagerName, String host, int port, String channelName, String replyQueueName) throws MqException {
        super(qmanagerName, host, port, channelName, replyQueueName);
    }

    public List<IBMMqAuthRecord> getPermissions() throws MqException {
        PCFMessageAgent agent = null;
        List<IBMMqAuthRecord> authRecords = new ArrayList<IBMMqAuthRecord>();
        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);

            PCFParameter[] parameters = {
                /*
                 * Interpret profile as a filter on the profile name of the authority records.
                 * If you set it to MQAUTHOPT_NAME_AS_WILDCARD, the only valid value for profile name is asterisk(*).
                 */
                new MQCFIN(CMQCFC.MQIACF_AUTH_OPTIONS, CMQCFC.MQAUTHOPT_NAME_AS_WILDCARD),
                new MQCFST(CMQCFC.MQCACF_AUTH_PROFILE_NAME, StringConstants.STAR),
                new MQCFIN(CMQCFC.MQIACF_OBJECT_TYPE, CMQC.MQOT_ALL),
                new MQCFIL(CMQCFC.MQIACF_AUTH_PROFILE_ATTRS, new int[]{CMQCFC.MQIACF_ALL})
            };
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_AUTH_RECS);
            IBMPCFUtil.addParameters(request, parameters);

            PCFMessage[] responseParameters = agent.send(request);

            for (PCFMessage pcfParams : responseParameters) {
                IBMMqAuthRecord authRecord = new IBMMqAuthRecord();
                authRecord.setEntityName(pcfParams.getStringParameterValue(MqAuthRecordAttributeEnum.ENTITY_NAME.getValue().intValue()));
                authRecord.setEntityType(pcfParams.getIntParameterValue(MqAuthRecordAttributeEnum.ENTITY_TYPE.getValue().intValue()));
                authRecord.setObjectType(pcfParams.getIntParameterValue(MqAuthRecordAttributeEnum.OBJECT_TYPE.getValue().intValue()));
                authRecord.setProfileName(pcfParams.getStringParameterValue(MqAuthRecordAttributeEnum.AUTH_PROFILE_NAME.getValue().intValue()));
                authRecord.setQmanagerName(pcfParams.getStringParameterValue(MqAuthRecordAttributeEnum.QMGR_NAME.getValue().intValue()));
                int authList[] = pcfParams.getIntListParameterValue(MqAuthRecordAttributeEnum.AUTHORIZATION_LIST.getValue().intValue());
                if (authList != null) {
                    for (int al : authList) {
                        authRecord.getOperations().add(new Integer(al));
                    }
                }
                authRecords.add(authRecord);
            }
        } catch (PCFException pe) {
            String errMessage = "Failed to get auth records for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            if (mqe.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
                logger.warn(mqe.getMessage());
            } else {
                String errMessage = "Failed to get auth records for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
                logger.error(errMessage);
                throw new MqException(errMessage, mqe);
            }
        } catch (IOException e) {
            String errMessage = "Failed to get auth records for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_INQUIRE_Q;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
        return authRecords;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getAllQueues()
     */
    @Override
    public List<MqQueue> getAllQueues() throws MqException {
        PCFMessageAgent agent = null;
        List<MqQueue> queues = new ArrayList<MqQueue>();
        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);

            PCFParameter[] parameters = {
                new MQCFST(CMQC.MQCA_Q_NAME, StringConstants.STAR),
                new MQCFIN(CMQC.MQIA_Q_TYPE, CMQC.MQQT_ALL),
                new MQCFIL(CMQCFC.MQIACF_Q_ATTRS,
                new int[]{
                    CMQC.MQCA_Q_NAME,
                    CMQC.MQIA_Q_TYPE,
                    CMQC.MQIA_DEFINITION_TYPE,
                    CMQC.MQCA_ALTERATION_DATE,
                    CMQC.MQCA_ALTERATION_TIME,
                    CMQC.MQIA_USAGE
                })
            };
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q);
            IBMPCFUtil.addParameters(request, parameters);

            PCFMessage[] responseParameters = agent.send(request);
            for (PCFMessage pcfParams : responseParameters) {
                String queueName = pcfParams.getStringParameterValue(CMQC.MQCA_Q_NAME);
                Integer queueType = new Integer(pcfParams.getIntParameterValue(CMQC.MQIA_Q_TYPE));
                String alterDate = pcfParams.getStringParameterValue(CMQC.MQCA_ALTERATION_DATE);
                String alterTime = pcfParams.getStringParameterValue(CMQC.MQCA_ALTERATION_TIME);

                Integer defType = null;
                Integer usage = null;
                if (queueType.intValue() == CMQC.MQQT_LOCAL || queueType.intValue() == CMQC.MQQT_MODEL) {
                    defType = new Integer(pcfParams.getIntParameterValue(CMQC.MQIA_DEFINITION_TYPE));
                    usage = new Integer(pcfParams.getIntParameterValue(CMQC.MQIA_USAGE));
                }

                // Collect queue names and check if queue name has to be skipped.
                if (StringUtils.isNotEmpty(queueName)) {
                    if (!MQ_OBJECT_NAME_FILTER.filter(queueName, QUEUE_FILTERS)) {
                        IBMMqQueue queue = new IBMMqQueue();
                        queue.addAttribute(MqQueueAttributeEnum.Q_NAME, queueName.trim());
                        queue.addAttribute(MqQueueAttributeEnum.Q_TYPE, queueType);
                        queue.addAttribute(MqQueueAttributeEnum.DEFINITION_TYPE, defType);
                        queue.addAttribute(MqQueueAttributeEnum.ALTERATION_DATE, alterDate);
                        queue.addAttribute(MqQueueAttributeEnum.ALTERATION_TIME, alterTime);
                        queue.addAttribute(MqQueueAttributeEnum.USAGE, usage);
                        queues.add(queue);
                    }
                }
            }
        } catch (PCFException pe) {
            String errMessage = "Failed to get all queues for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            if (mqe.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
                logger.warn(mqe.getMessage());
            } else {
                String errMessage = "Failed to get all queues for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
                logger.error(errMessage);
                throw new MqException(errMessage, mqe);
            }
        } catch (IOException e) {
            String errMessage = "Failed to get all queues for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_INQUIRE_Q;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
        return queues;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getAllChannels()
     */
    @Override
    public List<MqChannel> getAllChannels() throws MqException {
        PCFMessageAgent agent = null;
        PCFParameter[] parameters = {
            new MQCFST(CMQCFC.MQCACH_CHANNEL_NAME, StringConstants.STAR),
            new MQCFIN(CMQCFC.MQIACH_CHANNEL_TYPE, CMQXC.MQCHT_ALL),
            new MQCFIL(
            CMQCFC.MQIACF_CHANNEL_ATTRS,
            new int[]{
                CMQCFC.MQCACH_CHANNEL_NAME,
                CMQCFC.MQIACH_CHANNEL_TYPE,
                CMQC.MQCA_ALTERATION_DATE,
                CMQC.MQCA_ALTERATION_TIME})
        };

        List<MqChannel> channels = new ArrayList<MqChannel>();
        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_CHANNEL);
            IBMPCFUtil.addParameters(request, parameters);

            PCFMessage[] responseParameters = agent.send(request);
            for (int i = 0; i < responseParameters.length; i++) {
                PCFMessage pcfParams = responseParameters[i];
                String channelName = pcfParams.getStringParameterValue(CMQCFC.MQCACH_CHANNEL_NAME);
                Integer channelType = new Integer(pcfParams.getIntParameterValue(CMQCFC.MQIACH_CHANNEL_TYPE));
                String alterDate = pcfParams.getStringParameterValue(CMQC.MQCA_ALTERATION_DATE);
                String alterTime = pcfParams.getStringParameterValue(CMQC.MQCA_ALTERATION_TIME);

                if (StringUtils.isNotEmpty(channelName)) {
                    IBMMqChannel channel = new IBMMqChannel();
                    channel.addAttribute(MqChannelAttributeEnum.CHANNEL_NAME, channelName.trim());
                    channel.addAttribute(MqChannelAttributeEnum.CHANNEL_TYPE, channelType);
                    channel.addAttribute(MqChannelAttributeEnum.ALTERATION_DATE, alterDate);
                    channel.addAttribute(MqChannelAttributeEnum.ALTERATION_TIME, alterTime);
                    channels.add(channel);
                }
            }
        } catch (PCFException pe) {
            String errMessage = "Failed to get all channels for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            if (mqe.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
                logger.warn(mqe.getMessage());
            } else {
                String errMessage = "Failed to get all channels for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
                logger.error(errMessage);
                throw new MqException(errMessage, mqe);
            }
        } catch (IOException e) {
            String errMessage = "Failed to get all channels for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_INQUIRE_CHANNEL;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }

        return channels;
    }

    @Override
    public List<MqTopic> getAllTopics() throws MqException {
        PCFMessageAgent agent = null;

        PCFParameter[] parameters = {
            new MQCFST(CMQC.MQCA_TOPIC_NAME, StringConstants.STAR),
            new MQCFIL(
            CMQCFC.MQIACF_TOPIC_ATTRS,
            new int[]{
                CMQC.MQCA_TOPIC_NAME,
                CMQC.MQCA_TOPIC_STRING,
                CMQC.MQCA_TOPIC_DESC
            })
        };

        List<MqTopic> topics = new ArrayList<MqTopic>();

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_TOPIC);
            IBMPCFUtil.addParameters(request, parameters);

            PCFMessage[] responseParameters = agent.send(request);
            for (int i = 0; i < responseParameters.length; i++) {
                PCFMessage pcfParams = responseParameters[i];
                String topicName = pcfParams.getStringParameterValue(CMQC.MQCA_TOPIC_NAME);
                String topicString = pcfParams.getStringParameterValue(CMQC.MQCA_TOPIC_STRING);
                String topicDesc = pcfParams.getStringParameterValue(CMQC.MQCA_TOPIC_DESC);

                if (StringUtils.isNotEmpty(topicName)) {
                    IBMMqTopic topic = new IBMMqTopic();
                    topic.addAttribute(MqTopicAttributeEnum.TOPIC_NAME, topicName.trim());
                    topic.addAttribute(MqTopicAttributeEnum.TOPIC_STATUS, topicString);
                    topic.addAttribute(MqTopicAttributeEnum.TOPIC_DESC, topicDesc);
                    topics.add(topic);
                }
            }
        } catch (PCFException pe) {
            String errMessage = "Failed to get all topics for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            if (mqe.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
                logger.warn(mqe.getMessage());
            } else {
                String errMessage = "Failed to get all topics for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
                logger.error(errMessage);
                throw new MqException(errMessage, mqe);
            }
        } catch (IOException e) {
            String errMessage = "Failed to get all topics for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_INQUIRE_CHANNEL;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
        return topics;
    }


    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getTopology()
     */
    @Override
    public List<MqChannel> getTopology() throws MqException {
        PCFMessageAgent agent = null;
        PCFParameter[] parameters = {
            new MQCFST(CMQCFC.MQCACH_CHANNEL_NAME, StringConstants.STAR),
            new MQCFIN(CMQCFC.MQIACH_CHANNEL_TYPE, CMQXC.MQCHT_SENDER),
            new MQCFIL(
            CMQCFC.MQIACF_CHANNEL_ATTRS,
            new int[]{
                CMQCFC.MQCACH_CONNECTION_NAME,
                CMQC.MQCA_Q_MGR_NAME,
                CMQCFC.MQCACH_CHANNEL_NAME,
                CMQCFC.MQIACH_CHANNEL_TYPE,
                CMQC.MQCA_ALTERATION_DATE,
                CMQC.MQCA_ALTERATION_TIME})
        };

        List<MqChannel> channels = new ArrayList<MqChannel>();

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_CHANNEL);
            IBMPCFUtil.addParameters(request, parameters);

            PCFMessage[] responseParameters = agent.send(request);

            for (PCFMessage pcfParams : responseParameters) {
                String channelName = pcfParams.getStringParameterValue(CMQCFC.MQCACH_CHANNEL_NAME);
                String connectionName = pcfParams.getStringParameterValue(CMQCFC.MQCACH_CONNECTION_NAME);
                Integer channelType = new Integer(pcfParams.getIntParameterValue(CMQCFC.MQIACH_CHANNEL_TYPE));
                String alterDate = pcfParams.getStringParameterValue(CMQC.MQCA_ALTERATION_DATE);
                String alterTime = pcfParams.getStringParameterValue(CMQC.MQCA_ALTERATION_TIME);

//                if (channelType.intValue() == 2 && StringUtils.isNotEmpty(channelName) && StringUtils.isNotEmpty(connectionName)){
                if ((channelType.intValue() == 2 || channelType.intValue() == 1)  && StringUtils.isNotEmpty(channelName) && StringUtils.isNotEmpty(connectionName)){
                    IBMMqChannel channel = new IBMMqChannel();
                    channel.addAttribute(MqChannelAttributeEnum.CHANNEL_NAME, channelName.trim());
                    channel.addAttribute(MqChannelAttributeEnum.CONNECTION_NAME, connectionName);
                    channel.addAttribute(MqChannelAttributeEnum.CHANNEL_TYPE, channelType);
                    channel.addAttribute(MqChannelAttributeEnum.ALTERATION_DATE, alterDate);
                    channel.addAttribute(MqChannelAttributeEnum.ALTERATION_TIME, alterTime);
                    channels.add(channel);
                }
            }
        } catch (PCFException pe) {
            String errMessage = "Failed to get all channels for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            if (mqe.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
                logger.warn(mqe.getMessage(), mqe);
            } else {
                String errMessage = "Failed to get all channels for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
                logger.error(errMessage);
                throw new MqException(errMessage, mqe);
            }
        } catch (IOException e) {
            String errMessage = "Failed to get all channels for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_INQUIRE_CHANNEL;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
        return channels;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getQueueStats(java.lang.String[], java.lang.String[])
     */
    @Override
    public List<MqQueueStat> getQueueStats(String[] excludeQueueNames, String[] includeQueueNames) throws MqException {
        PCFMessageAgent agent = null;

        String queueNameExp = StringConstants.STAR;

        if (includeQueueNames != null && includeQueueNames.length > 0) {
            queueNameExp = includeQueueNames[0];
        }

        PCFParameter[] parameters = {
            new MQCFST(CMQC.MQCA_Q_NAME, queueNameExp)
        };
        List<MqQueueStat> queueStats = new ArrayList<MqQueueStat>();

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_RESET_Q_STATS);
            IBMPCFUtil.addParameters(request, parameters);

            // Use the agent to send the request.
            PCFMessage[] responseParameters = agent.send(request);

            for (PCFMessage pcfParams : responseParameters) {
                // In case statistics is off.
                if (pcfParams == null) {
                    continue;
                }

                String queueName = pcfParams.getStringParameterValue(CMQC.MQCA_Q_NAME);
                Integer enqCount = new Integer(pcfParams.getIntParameterValue(CMQC.MQIA_MSG_ENQ_COUNT));
                Integer deqCount = new Integer(pcfParams.getIntParameterValue(CMQC.MQIA_MSG_DEQ_COUNT));
                Integer highDepth = new Integer(pcfParams.getIntParameterValue(CMQC.MQIA_HIGH_Q_DEPTH));
                Integer timeSinceLastReset = new Integer(pcfParams.getIntParameterValue(CMQC.MQIA_TIME_SINCE_RESET));

                // Create and populate queue stat object.
                IBMMqQueueStat mqQueueStat = new IBMMqQueueStat();


                if (StringUtils.isNotEmpty(queueName)) {
                    queueName = queueName.trim();
                    mqQueueStat.setQueueName(queueName); // 48 bytes length
                } else {
                    continue; // No need to process without queue name.
                }
                if (enqCount != null) {
                    mqQueueStat.setMsgEnqCount(enqCount);
                }
                if (deqCount != null) {
                    mqQueueStat.setMsgDeqCount(deqCount);
                }
                if (highDepth != null) {
                    mqQueueStat.setHighDepth(highDepth);
                }
                if (timeSinceLastReset != null) {
                    mqQueueStat.setTimeSinceReset(new Long(timeSinceLastReset.longValue()));
                } // Some records have to be filtered.
                String[] queueStatsFilters = QUEUE_STATS_FILTERS;

                if (excludeQueueNames != null && excludeQueueNames.length > 0) {
                    queueStatsFilters = new String[QUEUE_STATS_FILTERS.length + excludeQueueNames.length];
                    System.arraycopy(QUEUE_STATS_FILTERS, 0, queueStatsFilters, 0, QUEUE_STATS_FILTERS.length);
                    System.arraycopy(excludeQueueNames, 0, queueStatsFilters, QUEUE_STATS_FILTERS.length, excludeQueueNames.length);
                }
                if (!MQ_OBJECT_NAME_FILTER.filter(queueName, queueStatsFilters)) {
                    queueStats.add(mqQueueStat);
                }
            }
        } catch (PCFException pe) {
            String errMessage = "Failed to get queue stats for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            String errMessage = "Failed to get queue stats for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
            logger.error(errMessage);
            throw new MqException(errMessage, mqe);
        } catch (IOException e) {
            String errMessage = "Failed to get queue stats for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_RESET_Q_STATS;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
        return queueStats;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getQueue(java.lang.String)
     */
    @Override
    public MqQueue getQueue(String queueName) throws MqException {
        IBMMqObject queue = new IBMMqQueue();

        PCFParameter[] parameters = {
            new MQCFST(CMQC.MQCA_Q_NAME, queueName)
        };
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q);
            IBMPCFUtil.addParameters(request, parameters);

            // Use the agent to send the request.
            PCFMessage[] responseParameters = agent.send(request);

            for (PCFMessage pcfParams : responseParameters) {
                populateMqObject(pcfParams, MqQueueAttributeEnum.iterator(), queue);
            }
        } catch (PCFException pe) {
            String errMessage = "Failed to get queue (" + queueName + ") for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            if (mqe.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
                logger.warn(mqe.getMessage());
            } else {
                String errMessage = "Failed to get queue (" + queueName + ") for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
                logger.error(errMessage);
                throw new MqException(errMessage, mqe);
            }
        } catch (IOException e) {
            String errMessage = "Failed to get queue (" + queueName + ") for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_INQUIRE_Q;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
        return (MqQueue) queue;
    }

    @Override
    public MqTopic getTopic(String topicName) throws MqException {
        IBMMqObject topic = new IBMMqTopic();

        PCFParameter[] parameters = {
            new MQCFST(CMQC.MQCA_TOPIC_NAME, topicName)
        };
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_TOPIC);
            IBMPCFUtil.addParameters(request, parameters);

            // Use the agent to send the request.
            PCFMessage[] responseParameters = agent.send(request);

            for (PCFMessage pcfParams : responseParameters) {
                populateMqObject(pcfParams, MqQueueAttributeEnum.iterator(), topic);
            }
        } catch (PCFException pe) {
            String errMessage = "Failed to get topic (" + topicName + ") for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            if (mqe.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
                logger.warn(mqe.getMessage());
            } else {
                String errMessage = "Failed to get topic (" + topicName + ") for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
                logger.error(errMessage);
                throw new MqException(errMessage, mqe);
            }
        } catch (IOException e) {
            String errMessage = "Failed to get topic (" + topicName + ") for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_INQUIRE_Q;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
        return (MqTopic) topic;
    }


    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#deleteQueue(java.lang.String)
     */
    @Override
    public void deleteQueue(String queueName) throws MqException {
        PCFParameter[] parameters = {new MQCFST(CMQC.MQCA_Q_NAME, queueName), new MQCFIN(CMQCFC.MQIACF_PURGE, CMQCFC.MQPO_YES)};
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_DELETE_Q);
            IBMPCFUtil.addParameters(request, parameters);
            agent.send(request);
        } catch (PCFException pe) {
            String errMessage = "Failed to delete queue (" + queueName + ") for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            String errMessage = "Failed to delete queue (" + queueName + ") for qmanager: " + getQmanagerName() + " cause: " + mqe.getMessage();
            logger.error(errMessage);
            throw new MqException(errMessage, mqe);
        } catch (IOException e) {
            String errMessage = "Failed to delete queue (" + queueName + ") for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_INQUIRE_Q + " cause:" + e.getMessage();
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
    }

    public void deleteTopic(String topicName) throws MqException {
        PCFParameter[] parameters = {
            new MQCFST(CMQC.MQCA_TOPIC_NAME, topicName)
        };
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_DELETE_TOPIC);
            IBMPCFUtil.addParameters(request, parameters);
            agent.send(request);
        } catch (PCFException pe) {
            String errMessage = "Failed to delete topic (" + topicName + ") for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            String errMessage = "Failed to delete topic (" + topicName + ") for qmanager: " + getQmanagerName() + " cause: " + mqe.getMessage();
            logger.error(errMessage);
            throw new MqException(errMessage, mqe);
        } catch (IOException e) {
            String errMessage = "Failed to delete topic (" + topicName + ") for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_INQUIRE_Q + " cause:" + e.getMessage();
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
    }


    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getChannel(java.lang.String)
     */
    @Override
    public MqChannel getChannel(String channelName) throws MqException {
        IBMMqObject channel = new IBMMqChannel();
        PCFParameter[] parameters = {
            new MQCFST(CMQCFC.MQCACH_CHANNEL_NAME, channelName)
        };
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_CHANNEL);
            IBMPCFUtil.addParameters(request, parameters);

            // Use the agent to send the request.
            PCFMessage[] responseParameters = agent.send(request);

            for (PCFMessage pcfParams : responseParameters) {
                populateMqObject(pcfParams, MqChannelAttributeEnum.iterator(), channel);
            }

            // Get channel status.
            request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_CHANNEL_STATUS);
            IBMPCFUtil.addParameters(request, parameters);

            try {
                responseParameters = agent.send(request);

                for (PCFMessage pcfParams : responseParameters) {
                    Integer channelStatus = new Integer(pcfParams.getIntParameterValue(MqChannelAttributeEnum.CHANNEL_STATUS.getValue().intValue()));
                    Integer channelInstanceType = new Integer(pcfParams.getIntParameterValue(MqChannelAttributeEnum.CHANNEL_INSTANCE_TYPE.getValue().intValue()));
                    String channelStartDate = pcfParams.getStringParameterValue(MqChannelAttributeEnum.CHANNEL_START_DATE.getValue().intValue());
                    String channelStartTime = pcfParams.getStringParameterValue(MqChannelAttributeEnum.CHANNEL_START_TIME.getValue().intValue());

                    if (channelStatus != null) {
                        channel.addAttribute(MqChannelAttributeEnum.CHANNEL_STATUS, channelStatus);
                    }
                    if (channelInstanceType != null) {
                        channel.addAttribute(MqChannelAttributeEnum.CHANNEL_INSTANCE_TYPE, channelInstanceType);
                        // Count server connections.
                        if (CMQC.MQOT_CURRENT_CHANNEL == channelInstanceType.intValue()) {
                            ((IBMMqChannel) channel).setConnectionCount(new Integer(((IBMMqChannel) channel).getConnectionCount().intValue() + 1));
                        }
                    }
                    if (channelStartDate != null) {
                        channel.addAttribute(MqChannelAttributeEnum.CHANNEL_START_DATE, channelStartDate);
                    }
                    if (channelStartTime != null) {
                        channel.addAttribute(MqChannelAttributeEnum.CHANNEL_START_TIME, channelStartTime);
                    }
                }
            } catch (PCFException pe) {
                // logPCFException(pe);
                if (pe.reasonCode == 3065) {
                    channel.addAttribute(MqChannelAttributeEnum.CHANNEL_STATUS, new Integer(CMQCFC.MQCHS_INACTIVE));
                } else {
                    throw pe;
                }
            }
        } catch (PCFException pe) {
            String errMessage = "Failed to get channel (" + channelName + ") for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage, Level.WARN);
            throw new MqException(pe);
        } catch (MQException mqe) {
            if (mqe.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
                logger.warn("Failed to get channel (" + channelName + ") for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode);
                logger.warn(mqe.getMessage());
            } else {
                String errMessage = "Failed to get channel (" + channelName + ") for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
                logger.error(errMessage);
                throw new MqException(errMessage, mqe);
            }
        } catch (IOException e) {
            String errMessage = "Failed to get channel (" + channelName + ") for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCACH_CHANNEL_NAME;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
        return (MqChannel) channel;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#deleteChannel(java.lang.String)
     */
    @Override
    public void deleteChannel(String channelName) throws MqException {
        PCFParameter[] parameters = {
            new MQCFST(CMQCFC.MQCACH_CHANNEL_NAME, channelName)
        };
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_DELETE_CHANNEL);
            IBMPCFUtil.addParameters(request, parameters);
            agent.send(request);
        } catch (PCFException pe) {
            String errMessage = "Failed to delete channel (" + channelName + ") for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            String errMessage = "Failed to delete channel (" + channelName + ") for qmanager: " + getQmanagerName() + " cause: " + mqe.getMessage();
            logger.error(errMessage);
            throw new MqException(errMessage, mqe);
        } catch (IOException e) {
            String errMessage = "Failed to delete channel (" + channelName + ") for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCACH_CHANNEL_NAME + " cause: " + e.getMessage();
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#getQmanager()
     */
    @Override
    public MqQmanager getQmanager() throws MqException {
        IBMMqObject qmanager = new IBMMqQmanager();
        PCFParameter[] parameters = {};// {new MQCFIN(CMQCFC.MQIACF_Q_MGR_ATTRS, CMQCFC.MQIACF_ALL)};
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_MGR);
            IBMPCFUtil.addParameters(request, parameters);

            // Use the agent to send the request.
            PCFMessage[] responseParameters = agent.send(request);

            for (PCFMessage pcfParams : responseParameters) {
                populateMqObject(pcfParams, MqQmanagerAttributeEnum.iterator(), qmanager);
            }
        } catch (PCFException pe) {
            String errMessage = "Failed to get qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            if (mqe.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
                logger.warn(mqe.getMessage());
            } else {
                String errMessage = "Failed to get qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
                logger.error(errMessage);
                throw new MqException(errMessage, mqe);
            }
        } catch (IOException e) {
            String errMessage = "Failed to get qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_INQUIRE_Q_MGR;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
        return (MqQmanager) qmanager;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#setChannel(org.netflexity.api.mq.MqChannel)
     */
    @Override
    public void setChannel(MqChannel channel) throws MqException {
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);

            // Get a list of all attributes that need to be changed.
            List<PCFParameter> params = createPCFParameters(MqChannelAttributeEnum.iterator(), (IBMMqObject) channel);

            // NOTE: Parameter that allows to create/replace attributes.
            params.add(new MQCFIN(CMQCFC.MQIACF_REPLACE, CMQCFC.MQRP_YES));

            // Convert parameters and execute command.
            PCFParameter[] parameters = new PCFParameter[params.size()];
            parameters = (PCFParameter[]) params.toArray(parameters);

            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_CREATE_CHANNEL);
            IBMPCFUtil.addParameters(request, parameters);
            agent.send(request);

        } catch (PCFException pe) {
            String errMessage = "Failed to change channel (" + channel.getChannelName() + ") for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            String errMessage = "Failed to change channel (" + channel.getChannelName() + ") for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
            logger.error(errMessage);
            throw new MqException(errMessage, mqe);
        } catch (IOException e) {
            String errMessage = "Failed to change channel (" + channel.getChannelName() + ") for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_CREATE_CHANNEL;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#setQmanager(org.netflexity.api.mq.MqQmanager)
     */
    @Override
    public void setQmanager(MqQmanager qmanager) throws MqException {
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);

            // Get a list of all attributes that need to be changed.
            List<PCFParameter> params = createPCFParameters(MqQmanagerAttributeEnum.iterator(), (IBMMqObject) qmanager);

            // NOTE: this parameter will force the change if remote queue is open.
            params.add(new MQCFIN(CMQCFC.MQIACF_FORCE, CMQCFC.MQFC_YES));

            PCFParameter[] parameters = new PCFParameter[params.size()];
            parameters = (PCFParameter[]) params.toArray(parameters);

            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_CHANGE_Q_MGR);
            IBMPCFUtil.addParameters(request, parameters);
            agent.send(request);

        } catch (PCFException pe) {
            String errMessage = "Failed to change qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            String errMessage = "Failed to change qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
            logger.error(errMessage);
            throw new MqException(errMessage, mqe);
        } catch (IOException e) {
            String errMessage = "Failed to change qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_CHANGE_Q_MGR;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManager#setQueue(org.netflexity.api.mq.MqQueue)
     */
    @Override
    public void setQueue(MqQueue queue) throws MqException {
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);

            // Get a list of all attributes that need to be changed.
            List<PCFParameter> params = createPCFParameters(MqQueueAttributeEnum.iterator(), (IBMMqObject) queue);

            // NOTE: Parameter that allows to create/replace attributes.
            params.add(new MQCFIN(CMQCFC.MQIACF_REPLACE, CMQCFC.MQRP_YES));

            // Convert parameters and execute command.
            PCFParameter[] parameters = new PCFParameter[params.size()];
            parameters = (PCFParameter[]) params.toArray(parameters);

            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_CREATE_Q);
            IBMPCFUtil.addParameters(request, parameters);
            agent.send(request);

        } catch (PCFException pe) {
            String errMessage = "Failed to change queue (" + queue.getQueueName() + ") for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            String errMessage = "Failed to change queue (" + queue.getQueueName() + ") for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
            logger.error(errMessage);
            throw new MqException(errMessage, mqe);
        } catch (IOException e) {
            String errMessage = "Failed to change queue (" + queue.getQueueName() + ") for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_CREATE_Q;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
    }

    public void setTopic(MqTopic topic) throws MqException {
        PCFMessageAgent agent = null;

        try {
            agent = IBMPCFUtil.getPCFMessageAgent(connection, replyQueueName);

            // Get a list of all attributes that need to be changed.
            List<PCFParameter> params = createPCFParameters(MqTopicAttributeEnum.iterator(), (IBMMqObject) topic);

            // NOTE: Parameter that allows to create/replace attributes.
            params.add(new MQCFIN(CMQCFC.MQIACF_REPLACE, CMQCFC.MQRP_YES));

            // Convert parameters and execute command.
            PCFParameter[] parameters = new PCFParameter[params.size()];
            parameters = (PCFParameter[]) params.toArray(parameters);

            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_CREATE_TOPIC);
            IBMPCFUtil.addParameters(request, parameters);
            agent.send(request);

        } catch (PCFException pe) {
            String errMessage = "Failed to change queue (" + topic.getTopicName() + ") for qmanager: " + getQmanagerName();
            logPCFException(pe, errMessage);
            throw new MqException(pe);
        } catch (MQException mqe) {
            String errMessage = "Failed to change queue (" + topic.getTopicName() + ") for qmanager: " + getQmanagerName() + " reason code: " + mqe.reasonCode;
            logger.error(errMessage);
            throw new MqException(errMessage, mqe);
        } catch (IOException e) {
            String errMessage = "Failed to change queue (" + topic.getTopicName() + ") for qmanager: " + getQmanagerName() + " command: " + CMQCFC.MQCMD_CREATE_Q;
            logger.error(errMessage);
            throw new MqException(errMessage, e);
        } finally {
            closePCFAgent(agent);
        }
    }

    /**
     * Populates mq object in the same order as attributes are ordered in
     * an enumeration. Order preservation is important when changing MQ object attributes.
     * 
     * @param pcfParams
     * @param attrTypesEnumIter
     * @param mqObject
     */
    protected void populateMqObject(PCFMessage pcfParams, Iterator attrTypesEnumIter, IBMMqObject mqObject) {
        while (attrTypesEnumIter.hasNext()) {
            AbstractMqAttributeEnum mqAttrType = (AbstractMqAttributeEnum) attrTypesEnumIter.next();
            mqObject.addAttribute(mqAttrType, pcfParams.getParameterValue(mqAttrType.getValue().intValue()));
        }
    }

    /**
     * Create a list of PCFParameter objects from summary.
     * 
     * @param summary
     */
    protected List<PCFParameter> createPCFParameters(Iterator attrTypesEnumIter, IBMMqObject mqObject) {
        Map attributeMap = mqObject.getAttributes();
        List<PCFParameter> params = new ArrayList<PCFParameter>(attributeMap.size());

        while (attrTypesEnumIter.hasNext()) {
            AbstractMqAttributeEnum key = (AbstractMqAttributeEnum) attrTypesEnumIter.next();

            if (attributeMap.containsKey(key)) {
                Object value = attributeMap.get(key);

                if (!key.isImmutable() || key.isMandatory()) {
                    PCFParameter param = null;

                    if (value instanceof Integer) {
                        if (value != null) {
                            Integer intval = (Integer) value;
                            param = new MQCFIN(key.getValue().intValue(), intval.intValue());
                        }
                    } else if (value instanceof String) {
                        param = new MQCFST(key.getValue().intValue(), StringUtils.defaultString((String) value));
                    } else {
                        param = new MQCFSL(key.getValue().intValue(), new String[]{StringUtils.defaultString((String) value)});
                    } // Add parameter if exists.
                    if (param != null) {
                        params.add(param);
                    }
                }
            }
        }
        return params;
    }

    /**
     * Disconnect PCF agent.
     *
     * @param agent
     * @throws MqException
     */
    private void closePCFAgent(PCFAgent agent) throws MqException {
        if (agent != null) {
            // logger.info("Disconnecting PCFAgent from queue manager " + agent.getQManagerName());
            try {
                agent.disconnect();
            } catch (MQException mqe) {
                throw new MqException(mqe.getMessage(), mqe);
            }
        }
    }

    /**
     * @param pe
     */
    private void logPCFException(PCFException pe, String errMsg) {
        logPCFException(pe, errMsg, Level.ERROR);
    }

    private void logPCFException(PCFException pe, String errMsg, Priority logPriority) {
        logger.log(logPriority, errMsg);
        if (pe.exceptionSource != null) {
            if (pe.exceptionSource.getClass().isArray()) {
                PCFMessage response[] = (PCFMessage[]) pe.exceptionSource;
                for (PCFMessage resp : response) {
                    logger.log(logPriority, resp);
                }
            } else {
                logger.log(logPriority, (PCFMessage) pe.exceptionSource);
            }
        }
    }
}
