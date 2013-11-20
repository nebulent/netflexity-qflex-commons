package org.netflexity.api.ssh.jsch.pool;

import org.apache.commons.pool.KeyedObjectPool;

import com.jcraft.jsch.JSchException;

/**
 * @author Max Fedorov
 *
 * Factory interface for creating Jsch SSH session.
 */
public interface JschSessionFactory {
    
	/**
     * Create Jsch session.
     * 
     * @param poolKey
     * @param pool
     * @return
     * @throws JSchException
     */
    public JschSessionWrapper createSession(JschPoolableKey poolKey, KeyedObjectPool pool) throws JSchException;
}
