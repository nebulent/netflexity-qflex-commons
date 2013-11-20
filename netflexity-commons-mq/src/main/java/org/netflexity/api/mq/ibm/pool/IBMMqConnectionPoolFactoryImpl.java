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
package org.netflexity.api.mq.ibm.pool;

import org.apache.log4j.Logger;
import org.netflexity.api.util.StringConstants;


/**
 * @author MAX
 *
 */
public final class IBMMqConnectionPoolFactoryImpl implements IBMMqConnectionPoolFactory {

    public static final String MQ_POOL_PROPERTIES = "mqPool.properties";
    private static Logger logger = Logger.getLogger(IBMMqConnectionPoolFactoryImpl.class);
    private static final IBMMqConnectionPoolFactory factory = new IBMMqConnectionPoolFactoryImpl();
    
    private IBMMqConnectionPool pool;
    //private Map mqConnectionPools = Collections.synchronizedMap(new HashMap(10));
    
    /**
     * Do not allow to create instances.
     */
    private IBMMqConnectionPoolFactoryImpl() {
        pool = new IBMMqConnectionPoolImpl(MQ_POOL_PROPERTIES);
    }

    /**
     * @return
     */
    public static IBMMqConnectionPoolFactory getFactory(){
        return factory;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.ibm.pool.IBMMqConnectionPoolFactory#createMqConnectionPool(org.netflexity.api.mq.ibm.pool.PoolableKey)
     */
    @Override
    public IBMMqConnectionPool createMqConnectionPool(){
        logger.info("MQ Connection Pool Statistics " + StringConstants.LT 
                + "active" + StringConstants.EQ + pool.getNumActive() + StringConstants.PIPE
                + "idle" + StringConstants.EQ + pool.getNumIdle() + StringConstants.GT);
        return pool;
//        synchronized (mqConnectionPools) {
//            IBMMqConnectionPool pool = (IBMMqConnectionPool) mqConnectionPools.get(key);
//            if(pool == null){
//                pool = new IBMMqConnectionPoolImpl(MQ_POOL_PROPERTIES, key);
//                mqConnectionPools.put(key, pool);
//            }
//            return pool;
//        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        if(pool != null){
            pool.clear();
        }
    }
}
