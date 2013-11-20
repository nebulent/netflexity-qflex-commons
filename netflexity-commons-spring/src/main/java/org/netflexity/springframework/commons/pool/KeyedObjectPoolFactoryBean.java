/*
 *  2006 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.springframework.commons.pool;

import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.log4j.Logger;
import org.netflexity.api.util.pool.GenericKeyedPoolableObjectFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author MAX
 *
 */
public class KeyedObjectPoolFactoryBean implements FactoryBean, InitializingBean {

    private KeyedObjectPool pool;
    private GenericKeyedPoolableObjectFactory objectFactory;
    
    private int maxActive = GenericKeyedObjectPool.DEFAULT_MAX_ACTIVE;
    private long maxWait = GenericKeyedObjectPool.DEFAULT_MAX_WAIT;
    private int maxIdle = GenericKeyedObjectPool.DEFAULT_MAX_IDLE;
    private int maxTotal = GenericKeyedObjectPool.DEFAULT_MAX_TOTAL;
    private byte whenExhaustedAction = GenericKeyedObjectPool.DEFAULT_WHEN_EXHAUSTED_ACTION;
    private boolean testOnBorrow = GenericKeyedObjectPool.DEFAULT_TEST_ON_BORROW;
    private boolean testOnReturn = GenericKeyedObjectPool.DEFAULT_TEST_ON_RETURN;
    private boolean testWhileIdle = GenericKeyedObjectPool.DEFAULT_TEST_WHILE_IDLE;
    private long timeBetweenEvictionRunsMillis = GenericKeyedObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
    private int numTestsPerEvictionRun = GenericKeyedObjectPool.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
    private long minEvictableIdleTimeMillis = GenericKeyedObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
    
    private static Logger logger = Logger.getLogger(KeyedObjectPoolFactoryBean.class);
    
    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public Object getObject() throws Exception {
        return pool;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class getObjectType() {
        return (pool != null) ? pool.getClass() : KeyedObjectPool.class;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        // Configure object pool properties.
        GenericKeyedObjectPool.Config config = new GenericKeyedObjectPool.Config();
        config.maxActive = getMaxActive();
        config.maxWait = getMaxWait();
        config.maxIdle = getMaxIdle();
        config.maxTotal = getMaxTotal();
        config.whenExhaustedAction = getWhenExhaustedAction();
        config.testOnBorrow = isTestOnBorrow();
        config.testOnReturn = isTestOnReturn();
        config.testWhileIdle = isTestWhileIdle();
        config.timeBetweenEvictionRunsMillis = getTimeBetweenEvictionRunsMillis();
        config.numTestsPerEvictionRun = getNumTestsPerEvictionRun();
        config.minEvictableIdleTimeMillis = getMinEvictableIdleTimeMillis();
        
        // Create keyed pool and set it in passed in object factory.
        pool = new GenericKeyedObjectPool(objectFactory, config);
        objectFactory.setKeyedObjectPool(pool);
        
        // Log creation.
        logger.info("Created object pool with the following configuration: " + config);
    }
    
    /**
     * @return Returns the objectFactory.
     */
    public GenericKeyedPoolableObjectFactory getObjectFactory() {
        return objectFactory;
    }

    /**
     * @param objectFactory The objectFactory to set.
     */
    public void setObjectFactory(GenericKeyedPoolableObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    /**
     * @return Returns the maxActive.
     */
    public int getMaxActive() {
        return maxActive;
    }

    /**
     * @param maxActive The maxActive to set.
     */
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    /**
     * @return Returns the maxIdle.
     */
    public int getMaxIdle() {
        return maxIdle;
    }

    /**
     * @param maxIdle The maxIdle to set.
     */
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    /**
     * @return Returns the maxTotal.
     */
    public int getMaxTotal() {
        return maxTotal;
    }

    /**
     * @param maxTotal The maxTotal to set.
     */
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    /**
     * @return Returns the maxWait.
     */
    public long getMaxWait() {
        return maxWait;
    }

    /**
     * @param maxWait The maxWait to set.
     */
    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    /**
     * @return Returns the minEvictableIdleTimeMillis.
     */
    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    /**
     * @param minEvictableIdleTimeMillis The minEvictableIdleTimeMillis to set.
     */
    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    /**
     * @return Returns the numTestsPerEvictionRun.
     */
    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    /**
     * @param numTestsPerEvictionRun The numTestsPerEvictionRun to set.
     */
    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    /**
     * @return Returns the testOnBorrow.
     */
    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    /**
     * @param testOnBorrow The testOnBorrow to set.
     */
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    /**
     * @return Returns the testOnReturn.
     */
    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    /**
     * @param testOnReturn The testOnReturn to set.
     */
    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    /**
     * @return Returns the testWhileIdle.
     */
    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    /**
     * @param testWhileIdle The testWhileIdle to set.
     */
    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    /**
     * @return Returns the timeBetweenEvictionRunsMillis.
     */
    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    /**
     * @param timeBetweenEvictionRunsMillis The timeBetweenEvictionRunsMillis to set.
     */
    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    /**
     * @return Returns the whenExhaustedAction.
     */
    public byte getWhenExhaustedAction() {
        return whenExhaustedAction;
    }

    /**
     * @param whenExhaustedAction The whenExhaustedAction to set.
     */
    public void setWhenExhaustedAction(byte whenExhaustedAction) {
        this.whenExhaustedAction = whenExhaustedAction;
    }
}
