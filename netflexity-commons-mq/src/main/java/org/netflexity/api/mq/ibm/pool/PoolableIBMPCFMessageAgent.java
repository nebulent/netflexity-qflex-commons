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
package org.netflexity.api.mq.ibm.pool;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.pcf.MQCFH;
import com.ibm.mq.pcf.MQCFIL;
import com.ibm.mq.pcf.MQCFIN;
import com.ibm.mq.pcf.MQCFST;
import com.ibm.mq.pcf.PCFMessageAgent;
import com.ibm.mq.pcf.PCFParameter;
import java.lang.reflect.Field;

/**
 * @author FEDORMAX
 *
 * PoolablePcfAgent
 */
public class PoolableIBMPCFMessageAgent extends PCFMessageAgent {

    public static Logger logger = Logger.getLogger(PoolableIBMPCFMessageAgent.class);

    // private MQQueueManager _conn;
    /**
     * @param arg0
     * @throws com.ibm.mq.MQException
     */
    public PoolableIBMPCFMessageAgent(MQQueueManager conn) throws MQException {
        super(conn);
        // setWaitInterval(5); // 5 seconds (both expiry and wait)
        //this._conn = conn;
        setReplyQueuePrefix("QFLEX");
    }

    /**
     * @param arg0
     * @param arg1
     * @throws MQException
     */
    public PoolableIBMPCFMessageAgent(MQQueueManager conn, String modelQueueName) throws MQException {
        super();
        super.modelQueueName = modelQueueName;
        connect(conn);
        setReplyQueuePrefix("QFLEX");
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @throws com.ibm.mq.MQException
     */
    public PoolableIBMPCFMessageAgent(String arg0, int arg1, String arg2) throws MQException {
        super(arg0, arg1, arg2);
        setReplyQueuePrefix("QFLEX");
    }

    /* (non-Javadoc)
     * @see com.ibm.mq.pcf.PCFAgent#open(com.ibm.mq.MQQueueManager, java.lang.String, java.lang.String, boolean)
     */
    /*protected synchronized void open(MQQueueManager conn, String targetQueue, String targetQmanager, boolean external) throws MQException {
     try
     {
     super.disconnect();
     }
     catch(MQException mqexception) { }
     if(targetQueue == null || targetQueue.length() == 0)
     targetQueue = conn.getCommandInputQueueName();
     String prefix = replyQueuePrefix;
     if(prefix != null && prefix.length() > 0)
     prefix = prefix + Integer.toString(hashCode());
     adminQueue = conn.accessQueue(targetQueue, 8208, targetQmanager, "", "mqm");
     replyQueue = conn.accessQueue(modelQueueName, 8196, "", prefix, "mqm");
     replyQueueName = replyQueue.name;
     //dont delete on close.
     //replyQueue.closeOptions = 2;
     if(!external)
     super.qmanager = conn;

     }*/

    /* (non-Javadoc)
     * @see com.ibm.mq.pcf.PCFAgent#open(com.ibm.mq.MQQueueManager, java.lang.String, java.lang.String, boolean)
     */
    @Override
    protected synchronized void open(MQQueueManager qmanager, String targetQueue, String targetQmanager, boolean external) throws MQException {
        try {
            disconnect();
        } catch (MQException mqexception) {
        }
        if (targetQueue == null || targetQueue.length() == 0) {
            targetQueue = qmanager.getCommandInputQueueName();
        }
        String prefix = replyQueuePrefix;
        if (prefix != null && prefix.length() > 0) {
            prefix = prefix + Integer.toString(hashCode());
        }
        adminQueue = qmanager.accessQueue(targetQueue, 8208, targetQmanager, "", "mqm");
        replyQueue = qmanager.accessQueue(modelQueueName, 8196, "", prefix, "mqm");
        replyQueueName = replyQueue.name;
        // Do not delete on close.
        //replyQueue.closeOptions = 2;
        if (!external) {
            this.qmanager = qmanager;
        }
        getBasicQmgrInfo(qmanager, true);
    }

    private void getBasicQmgrInfo(MQQueueManager qmgr, boolean tryBacklevel)
            throws MQException {
        int type = 16;
        int version = 3;
        if (tryBacklevel) {
            type = 1;
            version = 1;
        }
        try {
            MQMessage message = setRequestMQMD(new MQMessage());
            MQCFH.write(message, 2, 1, type, version);
            MQCFIL.write(message, 1001, new int[]{
                31, 32, 2015
            });
            adminQueue.put(message, pmo);
            message.messageId = null;
            message.encoding = encoding;
//            message.characterSet = defaultCharacterSet;
            //MQ 6 compatibility fix
            message.characterSet = getFieldIntValueFix("defaultCharacterSet");

            replyQueue.get(message, gmo);
            MQCFH cfh = new MQCFH(message);
            if (cfh.reason == 0) {
                for (int parameterCount = cfh.parameterCount; parameterCount-- > 0;) {
                    PCFParameter p = PCFParameter.nextParameter(message);
                    switch (p.getParameter()) {
                        case 31: // '\037'
                            qmanager_level = ((MQCFIN) p).value;
                            break;

                        case 32: // ' '
                            qmanager_platform = ((MQCFIN) p).value;
                            break;

                        case 2015:
                            qmanager_name = ((MQCFST) p).string.trim();
                            break;
                    }
                }

            } else if ((cfh.reason == 3001 || cfh.reason == 3003) && tryBacklevel) {
                getBasicQmgrInfo(qmanager, false);
            } else {
                throw new MQException(cfh.compCode, cfh.reason, this);
            }
        } catch (IOException e) {
            throw new MQException(2, 2033, this);
        }
    }

    /* (non-Javadoc)
     * @see com.ibm.mq.pcf.PCFAgent#disconnect()
     */
    @Override
    public synchronized void disconnect() throws MQException {
        super.disconnect();
        logger.info("Disconnected PCFMessageAgent.");

//        if(_conn != null){
//            _conn.disconnect();
//        }
    }

    private Field getField(Class cls, String fieldName) {
        try {
            return cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException nsfe) {
            Class superClass = cls.getSuperclass();
            if (superClass != null) {
                return getField(superClass, fieldName);
            }
            return null;
        }
    }

    private int getFieldIntValueFix(String fieldName) {
        try {
            Field field = getField(this.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                Integer value = (Integer) field.get(this);
                return value.intValue();
            }
            return 0;
        } catch (IllegalAccessException iae) {
            logger.error(iae);
            return 0;
        }
    }
}
