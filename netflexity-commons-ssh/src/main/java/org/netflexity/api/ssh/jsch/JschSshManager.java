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
package org.netflexity.api.ssh.jsch;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.pool.KeyedObjectPool;
import org.apache.log4j.Logger;
import org.netflexity.api.ssh.SshException;
import org.netflexity.api.ssh.SshManager;
import org.netflexity.api.ssh.jsch.pool.JschPoolableKey;
import org.netflexity.api.ssh.jsch.pool.JschSessionWrapper;
import org.netflexity.api.util.StringConstants;
import org.springframework.util.CollectionUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;

/**
 * @author MAX
 */
public class JschSshManager implements SshManager {
	
    public static final String EXEC_CHANNEL = "exec";
    public static final String SHELL_CHANNEL = "shell";
    public static final String NEWLINE = "\n";
    public static final String CMD_LS_FDC = "/bin/ls -lAQ *.FDC";
	public static final String CMD_SHORT_LS = "/bin/ls -1";
	public static final String CMD_ECHO_LANG = "echo $LANG";
	public static final String CMD_CAT = "cat ";
	public static final String CMD_CD = "cd ";
	public static final String CMD_PWD = "pwd";

    private static Logger logger = Logger.getLogger(JschSshManager.class.getName());
    
	private long startConnection;
	
	protected String host;
	protected int port;
	protected String userId;
	protected String password;
	protected String rootDir;
    protected int responseTimeOut = 5000;
    protected int threadSleepTimeOut = 100;

	protected JschSessionWrapper session;
	protected KeyedObjectPool sessionPool;

	/**
	 * 
	 * @param sessionPool
	 * @param host
	 * @param port
	 * @param userId
	 * @param password
	 * @param rootDir
	 * @param responseTimeout
	 * @param threadSleepTimeout
	 * @throws SshException
	 */
	private void init(KeyedObjectPool sessionPool, String host, int port, String userId, String password,
		String rootDir, int responseTimeout, int threadSleepTimeout) throws SshException {
		this.sessionPool = sessionPool;
		this.host = host;
		this.port = port;
		this.userId = userId;
		this.password = password;
		this.rootDir = rootDir;

		this.responseTimeOut = responseTimeout;
		this.threadSleepTimeOut = threadSleepTimeout;

		// Get ssh session from a pool.
		JschPoolableKey key = new JschPoolableKey(host, port, userId, password);
		try {
			session = (JschSessionWrapper) sessionPool.borrowObject(key);
			changeWorkingDirectory(rootDir);
		} catch (Exception e) {
			throw new SshException("Failed to retrieve Jsch session from pool.", e);
		}

		if (session != null && session.getSession().isConnected()) {
			logger.info("Connected to jsch session at " + session.getSession().getUserName() + StringConstants.AT
					+ session.getSession().getHost() + StringConstants.COLON + session.getSession().getPort());
			startConnection = new Date().getTime();
		}

	}

	/**
	 * @param sessionPool
	 * @param host
	 * @param port
	 * @param userId
	 * @param password
	 * @param rootDir
	 * @throws SshException
	 */
	public JschSshManager(KeyedObjectPool sessionPool, String host, int port, String userId, String password,
			String rootDir) throws SshException {
		init(sessionPool, host, port, userId, password, rootDir, 5000, 200);
	}

	/**
	 * @param sessionPool
	 * @param host
	 * @param port
	 * @param userId
	 * @param password
	 * @param rootDir
	 * @param responseTimeout
	 * @param threadSleepTimeout
	 * @throws SshException
	 */
	public JschSshManager(KeyedObjectPool sessionPool, String host, int port, String userId, String password,
			String rootDir, int responseTimeout, int threadSleepTimeout) throws SshException {
		init(sessionPool, host, port, userId, password, rootDir, responseTimeout, threadSleepTimeout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.netflexity.api.ssh.SshManager#close()
	 */
	public void close() throws SshException {
		if (session != null) {
			session.disconnect();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.netflexity.api.ssh.SshManager#executeShellCommand(java.lang.String)
	 */
	public String executeShellCommand(String commandWithArgs) throws SshException {
        ChannelExec channel = null;
		try {
			channel = (ChannelExec)session.getSession().openChannel(EXEC_CHANNEL);
			channel.setCommand(CMD_CD + rootDir + StringConstants.SEMICOLON + commandWithArgs);

            // Prepare to capture results.
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			channel.setOutputStream(outStream);
			int tries = 10;
			channel.connect(responseTimeOut);
			do {
				if (channel.getExitStatus() == 0)
					break;
				else {
					try {
						Thread.sleep(threadSleepTimeOut);
					} 
                    catch (InterruptedException e) {}
					tries--;
				}
			} 
            while (tries > 0);
			return outStream.toString();
		} 
        catch (JSchException e) {
			throw new SshException("Failed to open channel and execute command :" + commandWithArgs, e);
		} 
        finally {
			if (channel != null) {
				channel.disconnect();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#getFileListing()
	 */
	public List getFileListing() throws SshException {
		String[] buff = executeShellCommand(CMD_SHORT_LS).split(NEWLINE);
		ArrayList result = new ArrayList();
		CollectionUtils.mergeArrayIntoCollection(buff, result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#getWorkingDirectory()
	 */
	public String getWorkingDirectory() throws SshException {
		return executeShellCommand(CMD_PWD).replaceAll(NEWLINE, StringConstants.EMPTY);
	}
	
	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#changeWorkingDirectory(java.lang.String)
	 */
	public void changeWorkingDirectory(String path) throws SshException {
		String oldPath = rootDir;
		rootDir = path;
		String buff = executeShellCommand(StringConstants.EMPTY);
		if (buff.length() != 0) {
			rootDir = oldPath;
			throw new SshException("Failed to change current directory to :" + path);
		}
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#gotoParentDirectory()
	 */
	public void gotoParentDirectory() throws SshException {
		changeWorkingDirectory("..");
	}
	
	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#getTextFileContent(java.lang.String)
	 */
	public String getTextFileContent(String fileName) throws SshException {
		return executeShellCommand(CMD_CAT + fileName);
	}
	
    /* (non-Javadoc)
     * @see org.netflexity.api.ssh.SshManager#getSessionDuration()
     */
    public long getSessionDuration() {
        return (new Date()).getTime() - startConnection;
    }
    
	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#isConnected()
	 */
	public boolean isConnected() {
		return session.getSession().isConnected();
	}

	/**
	 * @return Returns the sessionPool.
	 */
	public KeyedObjectPool getSessionPool() {
		return sessionPool;
	}

	/**
	 * @param sessionPool
	 *            The sessionPool to set.
	 */
	public void setSessionPool(KeyedObjectPool sessionPool) {
		this.sessionPool = sessionPool;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#getHost()
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            The host to set.
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#getPassword()
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#getPort()
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#getRootDir()
	 */
	public String getRootDir() {
		return rootDir;
	}

	/**
	 * @param rootDir
	 *            The rootDir to set.
	 */
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.ssh.SshManager#getUserId()
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
