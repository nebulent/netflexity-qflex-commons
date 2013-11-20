/*
 *  2005 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.util.smtp;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;


public interface SmtpAgent {

    /**
     * @param fromAddr
     * @param toAddressList
     * @param subject
     * @param content
     * @throws MessagingException
     */
    public void sendMessage(String fromAddr, String toAddressList,
            String subject, String content) throws MessagingException;

    /**
     * @param fromAddr
     * @param ccAddressList
     * @param toAddressList
     * @param subject
     * @param content
     * @throws MessagingException
     */
    public void sendMessage(String fromAddr, String ccAddressList,
            String toAddressList, String subject, String content)
            throws MessagingException;

    /**
     * @return Returns the fileAttachments.
     */
    public List getFileAttachments();

    /**
     * @param fileAttachment
     *            The fileAttachment to add.
     */
    public void setFileAttachment(File fileAttachment);

    /**
     * @return Returns the populator.
     */
    public MimeMultipartPopulator getPopulator();

    /**
     * @param populator The populator to set.
     */
    public void setPopulator(MimeMultipartPopulator populator);

}