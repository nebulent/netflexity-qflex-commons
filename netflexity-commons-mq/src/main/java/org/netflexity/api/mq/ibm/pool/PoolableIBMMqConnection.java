package org.netflexity.api.mq.ibm.pool;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.log4j.Logger;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

/**
 * @author Max Fedorov
 *
 * MQ Queue Manager connection wrapper.
 */
public class PoolableIBMMqConnection extends MQQueueManager {

    public static Logger logger = Logger.getLogger(PoolableIBMMqConnection.class.getName());
    // The pool to which to return.
    protected KeyedObjectPool _pool;
    protected PoolableKey _key;
    protected String id;
    protected long createdTime;

    /**
     * @param qmName
     * @throws com.ibm.mq.MQException
     */
    public PoolableIBMMqConnection(PoolableKey key, KeyedObjectPool pool) throws MQException {
        super(key.getQmanagerName());
        this.createdTime = System.currentTimeMillis();
        this._pool = pool;
        this._key = key;
        this.id = RandomStringUtils.randomNumeric(6);
    }

    /**
     * @throws MQException
     */
    public void reallyDisconnect() throws MQException {
        super.disconnect();
    }

    /* (non-Javadoc)
     * @see com.ibm.mq.MQQueueManager#disconnect()
     */
    @Override
    public void disconnect() throws MQException {
        logger.info("Disconnecting MQ QM Connection.");

        boolean isClosed = (!isConnected());

        // Check if resource is closed.
        if (isClosed) {
            try {
                logger.info("Invalidating MQ QM Connection.");
                _pool.invalidateObject(_key, this);
            } catch (Exception e) {
                logger.error("Failed to invalidate MQ QM Connection in the pool.");
                logger.error("Host: " + _key.getHost() + ":" + _key.getPort());
                logger.error("Qmanager name: " + _key.getQmanagerName());
                logger.error("Channel: " + _key.getChannelName());
                throw new MQException(0, 0, "Failed to invalidate MQ QM Connection in the pool.");
            }
        } else {
            try {
                logger.info("Trying to return MQ QM Connection to pool.");
                _pool.returnObject(_key, this);
            } catch (Exception e) {
                logger.error("Failed to return MQ QM Connection to pool.");
                logger.error("Host: " + _key.getHost() + ":" + _key.getPort());
                logger.error("Qmanager name: " + _key.getQmanagerName());
                logger.error("Channel: " + _key.getChannelName());
                throw new MQException(0, 0, "Failed to return MQ QM Connection to pool.");
            }
        }
    }

    /*
     * Has to be overridden since the connection is stored in the pool.
     *
     * (non-Javadoc)
     * @see com.ibm.mq.MQQueueManager#isConnected()
     */
    @Override
    public boolean isConnected() {
        try {
            // http://publib.boulder.ibm.com/infocenter/wmqv6/v6r0/index.jsp?topic=/com.ibm.mq.csqzaw.doc/uj15170_.htm
            super.getCommandInputQueueName();
            super.connected = true;
            return true;
        } catch (MQException e) {
            super.connected = false;
            return false;
        }
    }

    /*public boolean isConnected() {
    int openOptions = MQC.MQOO_FAIL_IF_QUIESCING | MQC.MQOO_BROWSE | MQC.MQOO_INQUIRE;
    MQGetMessageOptions gmo = new MQGetMessageOptions();
    gmo.options = MQC.MQGMO_BROWSE_NEXT | MQC.MQGMO_FAIL_IF_QUIESCING;

    MQQueue queue = null;
    try {
    queue = accessQueue("SYSTEM.DEFAULT.LOCAL.QUEUE", openOptions);
    super.connected = true;
    return true;
    }
    catch (MQException e) {
    super.connected = false;
    }
    finally {
    try {
    if (queue != null && queue.isOpen()) {
    queue.close();
    }
    }
    catch (MQException mqe) {
    logger.error(mqe.getMessage(), mqe);
    }
    }
    return false;
    }*/
    /**
     * @return unique connection id.
     */
    public String getId() {
        return id;
    }

    /**
     * @return the createdTime
     */
    public long getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
