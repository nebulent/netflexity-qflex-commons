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
package org.netflexity.api.mq;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.netflexity.api.mq.MqException;
import org.netflexity.api.mq.MqQmanagerManager;
import org.netflexity.api.mq.MqQueue;
import org.netflexity.api.mq.ibm.IBMMQSCMqQmanagerManager;

public class IBMMQSCMqQmanagerManagerTest /*extends TestCase*/ {

    private MqQmanagerManager manager;
/*
    protected void setUp() throws Exception {
        super.setUp();
        try {
            manager = new IBMMQSCMqQmanagerManager("QT01", "IBMDEV", 1414, "SYSTEM.DEF.SVRCONN", "SYSTEM.COMMAND.INPUT", "SYSTEM.COMMAND.OUTPUT");
        }
        catch (MqException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        try {
            manager.close();
        }
        catch (MqException e) {
            assertTrue(e.getMessage(), false);
        }
    }
*/
    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMMQSCMqQmanagerManager.getAllQueues()'
     */
/*
    public void testGetAllQueues() {
        try {
            List queues = manager.getAllQueues();
            for (Iterator iter = queues.iterator(); iter.hasNext();) {
                MqQueue q = (MqQueue) iter.next();
                System.out.println(q);
            }
        }
        catch (MqException e) {
            assertTrue(e.getMessage(), false);
        }
    }
*/
    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMMQSCMqQmanagerManager.getQueueStats(String[], String[])'
     */
    public void testGetQueueStats() {

    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMMQSCMqQmanagerManager.getAllChannels()'
     */
    public void testGetAllChannels() {

    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMMQSCMqQmanagerManager.getChannel(String)'
     */
    public void testGetChannel() {

    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMMQSCMqQmanagerManager.getQueue(String)'
     */
    public void testGetQueue() {

    }

}
