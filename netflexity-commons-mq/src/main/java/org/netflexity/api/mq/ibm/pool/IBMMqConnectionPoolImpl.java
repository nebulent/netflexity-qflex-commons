package org.netflexity.api.mq.ibm.pool;

import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.log4j.Logger;
import org.netflexity.api.mq.MqException;
import org.netflexity.api.util.PropertiesLoader;
import org.netflexity.api.util.StringConstants;

import com.ibm.mq.MQException;

/**
 * @author Max Fedorov
 *
 * Mq Series Connection pool.
 */
public class IBMMqConnectionPoolImpl extends GenericKeyedObjectPool implements IBMMqConnectionPool {

    public static final String MAX_LIFE_EXPECTANCY_MILLIS = "maxLifeExpectancyMillis";
    public static final int FAILED_TO_GET_FROM_POOL_ERROR = 666;
    public static final String MAX_ACTIVE = "maxActive";
    public static final String MAX_WAIT = "maxWait";
    public static final String MAX_IDLE = "maxIdle";
    public static final String MAX_TOTAL = "maxTotal";
    public static final String WHEN_EXHAUSTED_ACTION = "whenExhaustedAction";
    public static final String TEST_ON_BORROW = "testOnBorrow";
    public static final String TEST_ON_RETURN = "testOnReturn";
    public static final String TEST_WHILE_IDLE = "testWhileIdle";
    public static final String TIME_BETWEEN_EVICTION_RUNS = "timeBetweenEvictionRunsMillis";
    public static final String NUMBER_OF_TESTS_PER_EVICTION = "numTestsPerEvictionRun";
    public static final String MIN_EVICTABLE_IDLE = "minEvictableIdleTimeMillis";
    private static Logger logger = Logger.getLogger(IBMMqConnectionPoolImpl.class);
    private String mqPoolPropertiesPath;
    private int maxLifeExpectancyMillis;

    protected GenericKeyedObjectPool.Config getConfig() {
        Properties descriptor = PropertiesLoader.getInstance().getProperty(mqPoolPropertiesPath, IBMMqConnectionPoolImpl.class.getClassLoader());
        GenericKeyedObjectPool.Config config = new GenericKeyedObjectPool.Config();
        config.maxActive = Integer.parseInt(descriptor.getProperty(MAX_ACTIVE, String.valueOf(GenericKeyedObjectPool.DEFAULT_MAX_ACTIVE)));
        config.maxWait = Long.parseLong(descriptor.getProperty(MAX_WAIT, String.valueOf(GenericKeyedObjectPool.DEFAULT_MAX_WAIT)));
        config.maxIdle = Integer.parseInt(descriptor.getProperty(MAX_IDLE, String.valueOf(GenericKeyedObjectPool.DEFAULT_MAX_IDLE)));
        config.maxTotal = Integer.parseInt(descriptor.getProperty(MAX_TOTAL, String.valueOf(GenericKeyedObjectPool.DEFAULT_MAX_TOTAL)));
        config.whenExhaustedAction = Byte.parseByte(descriptor.getProperty(WHEN_EXHAUSTED_ACTION, String.valueOf(GenericKeyedObjectPool.DEFAULT_WHEN_EXHAUSTED_ACTION)));
        config.testOnBorrow = new Boolean(descriptor.getProperty(TEST_ON_BORROW, String.valueOf(GenericKeyedObjectPool.DEFAULT_TEST_ON_BORROW))).booleanValue();
        config.testOnReturn = new Boolean(descriptor.getProperty(TEST_ON_RETURN, String.valueOf(GenericKeyedObjectPool.DEFAULT_TEST_ON_RETURN))).booleanValue();
        config.testWhileIdle = new Boolean(descriptor.getProperty(TEST_WHILE_IDLE, String.valueOf(GenericKeyedObjectPool.DEFAULT_TEST_WHILE_IDLE))).booleanValue();
        config.timeBetweenEvictionRunsMillis = Long.parseLong(descriptor.getProperty(TIME_BETWEEN_EVICTION_RUNS, String.valueOf(GenericKeyedObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS)));
        config.numTestsPerEvictionRun = Integer.parseInt(descriptor.getProperty(NUMBER_OF_TESTS_PER_EVICTION, String.valueOf(GenericKeyedObjectPool.DEFAULT_NUM_TESTS_PER_EVICTION_RUN)));
        config.minEvictableIdleTimeMillis = Integer.parseInt(descriptor.getProperty(MIN_EVICTABLE_IDLE, String.valueOf(GenericKeyedObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS)));
        this.maxLifeExpectancyMillis = Integer.parseInt(descriptor.getProperty(MAX_LIFE_EXPECTANCY_MILLIS, String.valueOf(GenericKeyedObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS)));
        return config;
    }

    /**
     * @param mqPoolPropertiesPath
     */
    IBMMqConnectionPoolImpl(String mqPoolPropertiesPath) {
        this.mqPoolPropertiesPath = mqPoolPropertiesPath;
        setConfig(getConfig());
        setFactory(new PoolableIBMMqConnectionFactory(this, maxLifeExpectancyMillis));
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.ibm.pool.IBMMqConnectionPool#borrowMqConnection()
     */
    @Override
    public Object borrowMqConnection(PoolableKey key) throws MqException {
        PoolableIBMMqConnection conn = null;
        try {
            conn = (PoolableIBMMqConnection) super.borrowObject(key);
            logger.info("Borrowed connection " + StringConstants.EQ + conn.getId() + StringConstants.GT);
            logger.info("MQ Connection Pool Statistics for key " + key + StringConstants.LT
                    + "active" + StringConstants.EQ + getNumActive(key) + StringConstants.PIPE
                    + "idle" + StringConstants.EQ + getNumIdle(key) + StringConstants.GT);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage(), e);
            MqException ex = new MqException(e.getMessage(), e);
            ex.setErrorCode(FAILED_TO_GET_FROM_POOL_ERROR);
            throw ex;
        } catch (MQException e) {
            logger.error("Failed to borrowMqConnection: " + (conn == null ? "null" : StringConstants.EQ + conn.getId() + StringConstants.GT));
            logger.info("MQ Connection Pool Statistics for key " + key + StringConstants.LT
                    + "active" + StringConstants.EQ + getNumActive(key) + StringConstants.PIPE
                    + "idle" + StringConstants.EQ + getNumIdle(key) + StringConstants.GT);
            logger.error(e.getMessage(), e);
            MqException ex = new MqException(e.getMessage(), e);
            ex.setErrorCode(e.reasonCode);
            throw ex;
        } catch (Exception e) {
            logger.error("Failed to borrowMqConnection: " + StringConstants.EQ + (conn == null ? "null" : conn.getId() + StringConstants.GT));
            logger.info("MQ Connection Pool Statistics for key " + key + StringConstants.LT
                    + "active" + StringConstants.EQ + getNumActive(key) + StringConstants.PIPE
                    + "idle" + StringConstants.EQ + getNumIdle(key) + StringConstants.GT);
            logger.error(e.getMessage(), e);
            throw new MqException(e.getMessage(), e);
        }
        return conn;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.ibm.pool.IBMMqConnectionPool#returnMqConnection(java.lang.Object)
     */
    @Override
    public void returnMqConnection(PoolableKey key, Object obj) throws MqException {
        try {
            PoolableIBMMqConnection conn = (PoolableIBMMqConnection) obj;
            super.returnObject(key, conn);
            logger.info("Returned connection " + StringConstants.LT + conn.getId() + StringConstants.GT);
        } catch (Exception e) {
            logger.info("Returned connection failed: " + StringConstants.LT + (obj == null ? "null" : ((PoolableIBMMqConnection) obj).getId()) + StringConstants.GT);
            throw new MqException(e.getMessage(), e);
        }
    }
}
