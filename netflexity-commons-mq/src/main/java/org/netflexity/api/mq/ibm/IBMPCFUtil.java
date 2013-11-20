package org.netflexity.api.mq.ibm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.netflexity.api.mq.ibm.pool.PoolableIBMPCFAgent;
import org.netflexity.api.mq.ibm.pool.PoolableIBMPCFMessageAgent;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.pcf.MQCFH;
import com.ibm.mq.pcf.PCFAgent;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
import com.ibm.mq.pcf.PCFParameter;

/**
 * @author issmma
 *
 * PCF utility methods.
 */
public class IBMPCFUtil {
    
    public static final Map<Integer, String> CHANNEL_STATUS_MAP = new HashMap<Integer, String>();
    static{
        CHANNEL_STATUS_MAP.put(CMQCFC.MQCHS_BINDING, "BINDING");
        CHANNEL_STATUS_MAP.put(CMQCFC.MQCHS_INACTIVE, "INACTIVE");
        CHANNEL_STATUS_MAP.put(CMQCFC.MQCHS_INITIALIZING, "INITIALIZING");
        CHANNEL_STATUS_MAP.put(CMQCFC.MQCHS_PAUSED, "PAUSED");
        CHANNEL_STATUS_MAP.put(CMQCFC.MQCHS_REQUESTING, "REQUESTING");
        CHANNEL_STATUS_MAP.put(CMQCFC.MQCHS_RETRYING, "RETRYING");
        CHANNEL_STATUS_MAP.put(CMQCFC.MQCHS_RUNNING, "RUNNING");
        CHANNEL_STATUS_MAP.put(CMQCFC.MQCHS_STARTING, "STARTING");
        CHANNEL_STATUS_MAP.put(CMQCFC.MQCHS_STOPPED, "STOPPED");
    }
    
    public static final Map<String, Integer> REVERSE_CHANNEL_STATUS_MAP = new HashMap<String, Integer>();
    static{
        REVERSE_CHANNEL_STATUS_MAP.put("BINDING", CMQCFC.MQCHS_BINDING);
        REVERSE_CHANNEL_STATUS_MAP.put("INACTIVE", CMQCFC.MQCHS_INACTIVE);
        REVERSE_CHANNEL_STATUS_MAP.put("INITIALIZING", CMQCFC.MQCHS_INITIALIZING);
        REVERSE_CHANNEL_STATUS_MAP.put("PAUSED", CMQCFC.MQCHS_PAUSED);
        REVERSE_CHANNEL_STATUS_MAP.put("REQUESTING", CMQCFC.MQCHS_REQUESTING);
        REVERSE_CHANNEL_STATUS_MAP.put("RETRYING", CMQCFC.MQCHS_RETRYING);
        REVERSE_CHANNEL_STATUS_MAP.put("RUNNING", CMQCFC.MQCHS_RUNNING);
        REVERSE_CHANNEL_STATUS_MAP.put("STARTING", CMQCFC.MQCHS_STARTING);
        REVERSE_CHANNEL_STATUS_MAP.put("STOPPED", CMQCFC.MQCHS_STOPPED);
    }
    
    public static Logger logger = Logger.getLogger(IBMPCFUtil.class.getName());
    
	/**
	 * @param connection
	 * @param modelQueueName
	 * @return
	 * @throws MQException
	 */
    public static PCFAgent getPCFAgent(MQQueueManager connection, String modelQueueName) throws MQException {
	if (StringUtils.isNotBlank(modelQueueName)) {
	    return new PoolableIBMPCFAgent(connection, modelQueueName);
	}
	return new PoolableIBMPCFAgent(connection);
    }
	
	/**
     * @param connection
     * @param modelQueueName
     * @return
     * @throws MQException
     */
    public static PCFMessageAgent getPCFMessageAgent(MQQueueManager connection, String modelQueueName) throws MQException {
        if(StringUtils.isNotBlank(modelQueueName)){
            return new PoolableIBMPCFMessageAgent(connection, modelQueueName);
        }
        return new PoolableIBMPCFMessageAgent(connection);
    }
    
    /**
     * @param message
     * @param parameters
     */
    public static void addParameters(PCFMessage message, PCFParameter parameters[]){
	for(PCFParameter parameter : parameters){
            message.addParameter(parameter);
        }
    }
    
    /**
     * Method getMessagesParameterMap.
     * @param responses
     * @return List
     */
    public static List getMessagesParameterMap(MQMessage[] responses) {
        List messages = new ArrayList(responses.length);
        logger.info("Received reply from PCFAgent of size (" + responses.length + ")");
        try {
            for (int i = 0; i < responses.length; i++) {
                MQMessage response = responses[i];
                if(response.getDataLength() > 0){
                    // Check the PCF header (MQCFH) in the response message
                    MQCFH cfh = new MQCFH(response);
                    
                    // Case no errors were reported.
                    if (cfh.reason == 0){
                        // Iterate through a list of parameters of each response.
                        Map parameters = new HashMap();
                        for (int j = 0; j < cfh.parameterCount; j++) {
                            PCFParameter p = PCFParameter.nextParameter(response);
                            parameters.put(new Integer(p.getParameter()), p.getValue());
                        }
                        messages.add(parameters);
                    }
                    else {
                        logger.warn("Failure to read parameters due to: " + cfh);
                        
                        // Walk through the returned parameters describing the error
                        for (i = 0; i < cfh.parameterCount; i++) {
                            logger.warn(PCFParameter.nextParameter(response));
                        }
                    }
                }
            }
        } 
        catch (MQException mqe) {
            logger.error(mqe.getMessage(), mqe);
        } 
        catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
        }
        return messages;
    }
    
	/**
	 * @param num
	 * @return
	 */
	public static String toHex(byte[] bytes) {
	    StringBuilder retString = new StringBuilder();
	    for (int i = 0; i < bytes.length; ++i) {
		retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1));
	    }
	    return retString.toString();
	}
	
	/**
	 * @param hex
	 * @return
	 */
	public static byte[] toBytes(String hex) {
	    byte[] bts = new byte[hex.length() / 2];
	    for (int i = 0; i < bts.length; i++) {
		bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return bts;
	}
    
    /**
     * Get message data. Can be read only once.
     * 
     * @param msg
     * @return
     */
    public static String getMessageData(MQMessage msg){
        String data = StringUtils.EMPTY;
        try {
            if(msg.getDataLength() > 0){
                int msgLength = msg.getDataLength();
                byte[] msgContent = new byte[msgLength];
                msg.readFully(msgContent);
                data = new String(msgContent, "UTF-8"); 
                logger.debug("Retrieved message data :" + data);
            }
        }
        catch (Throwable e){ // EOFException, IOException
            logger.error("Failed to retrieve message data for message :" + msg, e);
        }
        return data;
    }

}
