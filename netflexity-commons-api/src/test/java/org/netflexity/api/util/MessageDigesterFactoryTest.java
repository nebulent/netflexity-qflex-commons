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

import org.netflexity.api.util.MessageDigesterException;
import org.netflexity.api.util.MessageDigesterFactory;

import junit.framework.TestCase;

public class MessageDigesterFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'org.netflexity.api.util.MessageDigesterFactory.createDigest(String)'
     */
    public void testCreateDigest() {
        String plain = "abrakadabra";
        try {
            String hash1 = MessageDigesterFactory.getInstance().createDigest(plain);
            String hash2 = MessageDigesterFactory.getInstance().createDigest(plain);
            assertTrue("Hashes must be equal", hash1.equals(hash2));
        }
        catch (MessageDigesterException e) {
            assertTrue(e.getMessage(), false);
        }
            
    }

}
