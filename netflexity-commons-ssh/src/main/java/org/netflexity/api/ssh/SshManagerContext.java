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
package org.netflexity.api.ssh;

import java.io.Serializable;

import org.apache.commons.pool.KeyedObjectPool;

/**
 * @author MAX
 *
 */
public class SshManagerContext implements Serializable{
    
    public static final int SSH_PORT = 22;
    
    private String host;
    private int port = SSH_PORT;
    private String userName;
    private String password;
    private String rootDir;
    private KeyedObjectPool connectionPool;
    
    /**
     * @param connectionPool
     * @param host
     * @param port
     * @param userName
     * @param password
     * @param rootDir
     */
    public SshManagerContext(KeyedObjectPool connectionPool, String host, int port, String userName, String password, String rootDir) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.rootDir = rootDir;
        this.connectionPool = connectionPool;
    }
    
    /**
     * @param connectionPool
     * @param host
     * @param userName
     * @param password
     * @param rootDir
     */
    public SshManagerContext(KeyedObjectPool connectionPool, String host, String userName, String password, String rootDir) {
        this(connectionPool, host, SSH_PORT, userName, password, rootDir);
    }

    /**
     * @return the connectionPool
     */
    public KeyedObjectPool getConnectionPool() {
        return connectionPool;
    }

    /**
     * @param connectionPool the connectionPool to set
     */
    public void setConnectionPool(KeyedObjectPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * @return Returns the host.
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host The host to set.
     */
    public void setHost(String host) {
        this.host = host;
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
     * @return Returns the port.
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port The port to set.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the rootDir
     */
    public String getRootDir() {
        return rootDir;
    }

    /**
     * @param rootDir the rootDir to set
     */
    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }
}
