package org.netflexity.api.ssh.jsch.pool;

import java.util.Hashtable;

import org.apache.commons.pool.KeyedObjectPool;
import org.apache.log4j.Logger;
import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.pool.GenericKeyedPoolableObjectFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author Max Fedorov
 *
 * Implements lifecycle methods for ssh (Jsch) session pool.
 */
public class JschSessionFactoryImpl implements GenericKeyedPoolableObjectFactory, JschSessionFactory {

    public static Logger logger = Logger.getLogger(JschSessionFactoryImpl.class);
    
    // Real pool.
	private KeyedObjectPool pool;
    
    // Must be shared between all ssh sessions.
	private JSch jsch = new JSch();
    
	/**
	 * Default.
	 */
	public JschSessionFactoryImpl() {
	}
    
	/* (non-Javadoc)
     * @see org.netflexity.api.util.pool.GenericKeyedPoolableObjectFactory#getKeyedObjectPool()
     */
    public KeyedObjectPool getKeyedObjectPool() {
        return pool;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.pool.GenericKeyedPoolableObjectFactory#setKeyedObjectPool(org.apache.commons.pool.KeyedObjectPool)
     */
    public void setKeyedObjectPool(KeyedObjectPool pool) {
        this.pool = pool;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.ssh.jsch.pool.JschSessionFactory#createSession(org.netflexity.api.ssh.jsch.pool.JschPoolableKey, org.apache.commons.pool.KeyedObjectPool)
     */
    public JschSessionWrapper createSession(JschPoolableKey poolKey, KeyedObjectPool pool) throws JSchException {
        logger.info("Creating Jsch session for " + poolKey);
        
        Session session = jsch.getSession(poolKey.getUserId(), poolKey.getHost(), poolKey.getPort());
        session.setPassword(poolKey.getPassword());
        
        Hashtable config = new Hashtable();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        // Connect with a timeout of 5 seconds.
        session.connect(5000);
        
        // Return wrapped.
        return new JschSessionWrapper(session, poolKey, pool);
    }
    
    /* (non-Javadoc)
	 * @see org.apache.commons.pool.KeyedPoolableObjectFactory#makeObject(java.lang.Object)
	 */
	public Object makeObject(Object key) throws Exception {
		//assert((key != null && key instanceof JschPoolableKey));
        
		JschPoolableKey poolKey = (JschPoolableKey)key;
        JschSessionWrapper session = (JschSessionWrapper)createSession(poolKey, pool);
		logger.info("Created Jsch session " + poolKey + StringConstants.LT + session.getId() + StringConstants.GT);
		return session;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.pool.KeyedPoolableObjectFactory#destroyObject(java.lang.Object, java.lang.Object)
	 */
	public void destroyObject(Object key, Object obj) throws Exception {
		//assert((key != null && key instanceof JschPoolableKey));
		//assert((obj != null && obj instanceof JschSessionWrapper));
        
        JschSessionWrapper session = (JschSessionWrapper)obj;
		logger.info("Destroying Jsch session " + StringConstants.LT + session.getId() + StringConstants.GT);
		session.reallyDisconnect();
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.pool.KeyedPoolableObjectFactory#validateObject(java.lang.Object, java.lang.Object)
	 */
	public boolean validateObject(Object key, Object obj) {
		//assert((key != null && key instanceof JschPoolableKey));
		//assert((obj != null && obj instanceof JschSessionWrapper));
        
        JschSessionWrapper session = (JschSessionWrapper)obj;
		boolean isConnected = session.getSession().isConnected();
		logger.info("Validation of Jsch session " + StringConstants.LT + session.getId() + StringConstants.GT + " is " + isConnected);
		return isConnected;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.pool.KeyedPoolableObjectFactory#activateObject(java.lang.Object, java.lang.Object)
	 */
	public void activateObject(Object key, Object obj) throws Exception {
        JschSessionWrapper session = (JschSessionWrapper)obj;
	    logger.info("Activating Jsch session " + StringConstants.LT + session.getId() + StringConstants.GT);
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.pool.KeyedPoolableObjectFactory#passivateObject(java.lang.Object, java.lang.Object)
	 */
	public void passivateObject(Object key, Object obj) throws Exception {
        JschSessionWrapper session = (JschSessionWrapper)obj;
	    logger.info("Deactivating Jsch session " + StringConstants.LT + session.getId() + StringConstants.GT);
	}
}
