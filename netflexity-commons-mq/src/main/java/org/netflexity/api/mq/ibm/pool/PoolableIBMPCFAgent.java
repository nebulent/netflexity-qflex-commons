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

import org.apache.log4j.Logger;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.pcf.PCFAgent;

/**
 * @author FEDORMAX
 *
 * PoolablePcfAgent
 */
public class PoolableIBMPCFAgent extends PCFAgent {

    public static Logger logger = Logger.getLogger(PoolableIBMPCFAgent.class);

    // private MQQueueManager _conn;
    /**
     * @param arg0
     * @throws com.ibm.mq.MQException
     */
    public PoolableIBMPCFAgent(MQQueueManager conn) throws MQException {
        super(conn);
        // setWaitInterval(5); // 5 seconds (both expiry and wait)
        //this._conn = conn;
    }

    /**
     * @param arg0
     * @param arg1
     * @throws MQException
     */
    public PoolableIBMPCFAgent(MQQueueManager conn, String modelQueueName) throws MQException {
        super();
        super.modelQueueName = modelQueueName;
        connect(conn);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @throws com.ibm.mq.MQException
     */
    public PoolableIBMPCFAgent(String arg0, int arg1, String arg2) throws MQException {
        super(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see com.ibm.mq.pcf.PCFAgent#open(com.ibm.mq.MQQueueManager, java.lang.String, java.lang.String, boolean)
     */
    @Override
    protected synchronized void open(MQQueueManager conn, String targetQueue, String targetQmanager, boolean external) throws MQException {
        try {
            super.disconnect();
        } catch (MQException mqexception) {
        }
        if (targetQueue == null || targetQueue.length() == 0) {
            targetQueue = conn.getCommandInputQueueName();
        }
        String prefix = replyQueuePrefix;
        if (prefix != null && prefix.length() > 0) {
            prefix = prefix + Integer.toString(hashCode());
        }
        adminQueue = conn.accessQueue(targetQueue, 8208, targetQmanager, "", "mqm");
        replyQueue = conn.accessQueue(modelQueueName, 8196, "", prefix, "mqm");
        replyQueueName = replyQueue.name;
        //dont delete on close.
        //replyQueue.closeOptions = 2;
        if (!external) {
            super.qmanager = conn;
        }

    }

    /* (non-Javadoc)
     * @see com.ibm.mq.pcf.PCFAgent#disconnect()
     */
    @Override
    public synchronized void disconnect() throws MQException {
        super.disconnect();
        logger.info("Disconnected PCFAgent.");

//        if(_conn != null){
//            _conn.disconnect();
//        }
    }
}
