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
package org.netflexity.api.util;

import javax.mail.MessagingException;

import org.netflexity.api.util.smtp.SmtpAgent;
import org.netflexity.api.util.smtp.SmtpAgentImpl;

import junit.framework.TestCase;

public class SmtpAgentTest extends TestCase {

    private SmtpAgent agent;
    
    protected void setUp() throws Exception {
        agent = new SmtpAgentImpl("netflexity.com");
        
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'org.netflexity.api.util.smtp.SmtpAgentImpl.sendMessage(String, String, String, String)'
     */
    public void testSendMessageStringStringStringString() {
        assertNotNull(agent);
        try {
            agent.sendMessage("", "", "", "");
        } catch (MessagingException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    /*
     * Test method for 'org.netflexity.api.util.smtp.SmtpAgentImpl.sendMessage(String, String, String, String, String)'
     */
    public void testSendMessageStringStringStringStringString() {
        assertNotNull(agent);
        try {
            agent.sendMessage("", "", "", "", "");
        } catch (MessagingException e) {
            assertTrue(e.getMessage(), false);
        }
    }

}
