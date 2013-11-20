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

import java.util.List;

/**
 * @author MAX
 * 
 */
public interface SshManager {

	/**
	 * @throws SshException
	 */
	public void close() throws SshException;
	
	/**
	 * @param commandWithArgs
	 * @return
	 * @throws SshException
	 */
	public String executeShellCommand(String commandWithArgs) throws SshException;

    /**
     * @return List of files at current directory
     * @throws SshException
     */
	public List getFileListing() throws SshException;

    /**
     * @return Returns current working directory
     * @throws SshException
     */
	public String getWorkingDirectory() throws SshException;

    /**
     * Navigates to any directory
     * 
     * @param path
     *            path on remote file system
     * @throws SshException
     *             if directory not changed. (Remote machine returns something
     *             at command <tt>cd</tt>)
     */
	public void changeWorkingDirectory(String path) throws SshException;

    /**
     * Navigates to parent directory
     * 
     * @throws SshException
     */
	public void gotoParentDirectory() throws SshException;

    /**
     * Bring to client content of remote file (Note: file must be text, because
     * methode use <tt>cat</tt> command)
     * 
     * @param fileName
     *            File name on remote file system
     * @return file content as String
     * @throws SshException
     */
    public String getTextFileContent(String fileName) throws SshException;

    /**
     * @return Returns <tt>true</tt> if session connected
     */
	public boolean isConnected();
    
    /**
     * @return
     */
    public long getSessionDuration();

    /**
     * @return
     */
    public String getRootDir();
    
	/**
	 * @return
	 */
	public String getHost();

	/**
	 * @return
	 */
	public String getUserId();

	/**
	 * @return
	 */
	public String getPassword();

	/**
	 * @return
	 */
	public int getPort();
}
