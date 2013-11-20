/*
 *  2007 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.ssh.jsch.mq;

import java.util.List;

import org.netflexity.api.ssh.SshException;
import org.netflexity.api.ssh.SshManager;

public interface MqErrorFileManager {

    /**
     * @param sshManager
     * @param qmanagerName
     * @param startDateTime
     * @param endDateTime
     * @return
     * @throws SshException
     */
    public boolean hasNewFdcFilesAvaliableOnInterval(SshManager sshManager, String qmanagerName, long startDateTime,
            long endDateTime) throws SshException;

    /**
     * @param sshManager
     * @param qmanagerName
     * @param startDateTime
     * @param endDateTime
     * @return
     * @throws SshException
     */
    public List getNewFdcFilesOnInterval(SshManager sshManager, String qmanagerName, long startDateTime,
            long endDateTime) throws SshException;

    /**
     * @param sshManager
     * @param errorCode
     * @param qmanagerName
     * @param startDateTime
     * @param endDateTime
     * @return
     * @throws SshException
     */
    public boolean hasErrorCodeOnInterval(SshManager sshManager, String errorCode, String qmanagerName,
            long startDateTime, long endDateTime) throws SshException;

    /**
     * @param sshManager
     * @param errorCode
     * @param qmanagerName
     * @param startDateTime
     * @param endDateTime
     * @return
     * @throws SshException
     */
    public AmqRecord getErrorCodeOnInterval(SshManager sshManager, String errorCode, String qmanagerName,
            long startDateTime, long endDateTime) throws SshException;

}