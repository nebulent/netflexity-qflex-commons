package org.netflexity.api.ssh.jsch.pool;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Session;

/**
 * @author Max Fedorov
 *
 * Jsch ssh session wrapper.
 */
public class JschSessionWrapper{
	
    public static Logger logger = Logger.getLogger(JschSessionWrapper.class.getName());
    
    protected KeyedObjectPool pool;
	protected JschPoolableKey key;
	protected String id;
    protected Session session;
    
	/**
	 * @param session
	 * @param key
	 * @param pool
	 */
	public JschSessionWrapper(Session session, JschPoolableKey key, KeyedObjectPool pool){
        this.session = session;
		this.pool = pool;
		this.key = key;
		this.id = RandomStringUtils.randomNumeric(6);
	}
	
	/**
     * @return Returns the session.
     */
    public Session getSession() {
        return session;
    }

    /**
	 * Really disconnect Jsch session.
	 */
	public void reallyDisconnect(){
		session.disconnect();
	}
	
	/* (non-Javadoc)
	 * @see com.jcraft.jsch.Session#disconnect()
	 */
	public void disconnect(){
	    logger.info("Disconnecting Jsch session [" + session.getHost() + ":" + session.getPort() + "] to pool.");
	    
        // Check if resource is closed.
        if (!session.isConnected()) {
        	try{
        	    logger.info("Invalidating Jsch session [" + session.getHost() + ":" + session.getPort() + "] to pool.");
        		pool.invalidateObject(key, this);
        	}
	        catch(Exception e) {
                String error = "Failed to invalidate Jsch session [" + session.getHost() + ":" + session.getPort() + "] to pool.";
	            logger.error(error);
	        }
        }
        else {
            try {
                logger.info("Trying to return Jsch session [" + session.getHost() + ":" + session.getPort() + "] to pool.");
                pool.returnObject(key, this);
            }
            catch(Exception e) {
                String error = "Failed to return Jsch session [" + session.getHost() + ":" + session.getPort() + "] to pool.";
                logger.error(error);
            }
        }
	}
    
    /**
	 * @return unique connection id.
	 */
	public String getId() {
        return id;
    }
}
