/*
 *  2004 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
*/
package org.netflexity.api.util.smtp;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

/**
 * @author Max Fedorov
 *
 */
public interface MimeMultipartPopulator {
    
    /**
     * Add any additional body parts.
     * 
     * @param mimeParts
     * @throws MessagingException
     */
    public void populate(MimeMultipart mimeParts) throws MessagingException;
}
