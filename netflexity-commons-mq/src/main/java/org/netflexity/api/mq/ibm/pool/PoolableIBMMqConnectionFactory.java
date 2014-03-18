package org.netflexity.api.mq.ibm.pool;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.log4j.Logger;
import org.netflexity.api.mq.ibm.enums.WebsphereSSLCipherSpecEnum;
import org.netflexity.api.util.Assertion;
import org.netflexity.api.util.StringConstants;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

/**
 * @author Max Fedorov
 *
 * Implements lifecycle methods for MQ/PCF thread pool.
 */
public class PoolableIBMMqConnectionFactory implements KeyedPoolableObjectFactory, IBMMqConnectionFactory {

    public static Logger logger = Logger.getLogger(PoolableIBMMqConnectionFactory.class);
    private KeyedObjectPool _pool;
    private int maxLifeExpectancyMillis;

    /**
     * @param pool
     * @param maxLifeExpectancyMillis
     */
    public PoolableIBMMqConnectionFactory(KeyedObjectPool pool, int maxLifeExpectancyMillis) {
        this._pool = pool;
        this.maxLifeExpectancyMillis = maxLifeExpectancyMillis;
    }

    /* (non-Javadoc)
     * @see org.apache.commons.pool.KeyedPoolableObjectFactory#makeObject(java.lang.Object)
     */
    @Override
    public Object makeObject(Object key) throws Exception {
        Assertion.asert((key != null && key instanceof PoolableKey));
        PoolableKey poolKey = (PoolableKey) key;
        PoolableIBMMqConnection conn = (PoolableIBMMqConnection) createMqConnection(poolKey, _pool);
        logger.info("Created connection " + poolKey + StringConstants.LT + conn.getId() + StringConstants.GT);
        return conn;
    }

    /* (non-Javadoc)
     * @see org.apache.commons.pool.KeyedPoolableObjectFactory#destroyObject(java.lang.Object, java.lang.Object)
     */
    @Override
    public void destroyObject(Object key, Object obj) throws Exception {
        Assertion.asert((key != null && key instanceof PoolableKey));
        Assertion.asert((obj != null && obj instanceof MQQueueManager));
        PoolableIBMMqConnection conn = (PoolableIBMMqConnection) obj;
        logger.info("Destroying connection " + StringConstants.LT + conn.getId() + StringConstants.GT);
        conn.reallyDisconnect();
    }

    /* (non-Javadoc)
     * @see org.apache.commons.pool.KeyedPoolableObjectFactory#validateObject(java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean validateObject(Object key, Object obj) {
        Assertion.asert((key != null && key instanceof PoolableKey));
        Assertion.asert((obj != null && obj instanceof MQQueueManager));
        PoolableIBMMqConnection conn = (PoolableIBMMqConnection) obj;
        if ((System.currentTimeMillis() - conn.getCreatedTime()) >= maxLifeExpectancyMillis) {
            logger.info("Validation of connection failed since connection lived longer than " + maxLifeExpectancyMillis);
            return false;
        }
        boolean isConnected = conn.isConnected();
        logger.info("Validation of connection " + StringConstants.LT + conn.getId() + StringConstants.GT + " is " + isConnected);
        return isConnected;
    }

    /* (non-Javadoc)
     * @see org.apache.commons.pool.KeyedPoolableObjectFactory#activateObject(java.lang.Object, java.lang.Object)
     */
    @Override
    public void activateObject(Object key, Object obj) throws Exception {
        PoolableIBMMqConnection conn = (PoolableIBMMqConnection) obj;
        logger.info("Activating connection " + StringConstants.LT + conn.getId() + StringConstants.GT);
    }

    /* (non-Javadoc)
     * @see org.apache.commons.pool.KeyedPoolableObjectFactory#passivateObject(java.lang.Object, java.lang.Object)
     */
    @Override
    public void passivateObject(Object key, Object obj) throws Exception {
        PoolableIBMMqConnection conn = (PoolableIBMMqConnection) obj;
        logger.info("Deactivating connection " + StringConstants.LT + conn.getId() + StringConstants.GT);
    }

    /* (non-Javadoc)
     * @see com.netflexity.qflex.mq.pool.IMqConnectionFactory#createMqConnection(com.netflexity.qflex.mq.pool.PoolableKey, org.apache.commons.pool.KeyedObjectPool)
     */
    @Override
    public MQQueueManager createMqConnection(PoolableKey poolKey, KeyedObjectPool pool) throws MQException {
        logger.info("Creating connection " + poolKey);

        // Lock the use of static variables before creating new mq connection.
        synchronized (MQEnvironment.class) {
            // Set SSL.
            if (StringUtils.isNotBlank(poolKey.getSslTrustStore())) {
                System.setProperty("javax.net.debug", "all");
                System.setProperty("javax.net.ssl.trustStore", poolKey.getSslTrustStore());
            }
            if (StringUtils.isNotBlank(poolKey.getSslKeyStore())) {
                System.setProperty("javax.net.debug", "all");
                System.setProperty("javax.net.ssl.keyStore", poolKey.getSslKeyStore());
            }
            if (StringUtils.isNotBlank(poolKey.getSslKeyStorePassword())) {
                System.setProperty("javax.net.ssl.keyStorePassword", poolKey.getSslKeyStorePassword());
            }
            if (StringUtils.isNotBlank(poolKey.getSslTrustStorePassword())) {
                System.setProperty("javax.net.ssl.trustStorePassword", poolKey.getSslTrustStorePassword());
            }
            if (StringUtils.isNotBlank(poolKey.getSslCipherSpec()) && (StringUtils.isNotBlank(poolKey.getSslKeyStore()) || StringUtils.isNotBlank(poolKey.getSslTrustStore()))) {
                MQEnvironment.sslFipsRequired = (WebsphereSSLCipherSpecEnum.TLS_RSA_WITH_3DES_EDE_CBC_SHA.getName().equals(poolKey.getSslCipherSpec())
                        || WebsphereSSLCipherSpecEnum.TLS_RSA_WITH_AES_128_CBC_SHA.getName().equals(poolKey.getSslCipherSpec())
                        || WebsphereSSLCipherSpecEnum.TLS_RSA_WITH_AES_256_CBC_SHA.getName().equals(poolKey.getSslCipherSpec())
                        );
                MQEnvironment.properties.put(MQC.SSL_CIPHER_SUITE_PROPERTY, poolKey.getSslCipherSpec());
            }
            if (StringUtils.isNotBlank(poolKey.getSslPeerName())) {
                MQEnvironment.properties.put(MQC.SSL_PEER_NAME_PROPERTY, poolKey.getSslPeerName());
            }

            // Set host info.
            MQEnvironment.hostname = poolKey.getHost();
            MQEnvironment.channel = poolKey.getChannelName();
            MQEnvironment.port = poolKey.getPort();
            return new PoolableIBMMqConnection(poolKey, pool);
        }
    }
}
