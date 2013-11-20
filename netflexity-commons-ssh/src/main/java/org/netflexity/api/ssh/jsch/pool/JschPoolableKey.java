package org.netflexity.api.ssh.jsch.pool;

import java.io.File;
import java.io.Serializable;

import org.netflexity.api.util.StringConstants;

/**
 * @author Max Fedorov
 *
 * Key that contains all necessary information to build an instance
 * of a pooled object.
 */
public class JschPoolableKey implements Serializable{

	private String host;
	private int port;
    private String userId;
    private String password;
	
	/**
	 * @param host
	 * @param port
	 * @param userId
	 * @param password
	 */
	public JschPoolableKey(String host, int port, String userId, String password) {
		this.host = host;
		this.port = port;
        this.userId = userId;
        this.password = password;
	}
	
	/**
	 * @return
	 */
	public String getHost() {
		return host;
	}
    
	/**
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}
    
	/**
	 * @return
	 */
	public int getPort() {
		return port;
	}
    
	/**
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}
    
	/**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Returns the userId.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The userId to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return toString().hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return userId + StringConstants.AT + host + File.separator + port;
	}
    
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
	    if(obj != null && obj instanceof JschPoolableKey){
	        return obj.hashCode() == hashCode();
	    }
	    return false;
    }
}
