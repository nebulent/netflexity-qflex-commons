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

import junit.framework.TestCase;

import org.netflexity.api.mq.ibm.pool.IBMMqConnectionPool;
import org.netflexity.api.mq.ibm.pool.IBMMqConnectionPoolFactoryImpl;
import org.netflexity.api.mq.ibm.pool.PoolableKey;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.pcf.PCFAgent;

public class IBMMqConnectionPoolTest /*extends TestCase*/ {

    IBMMqConnectionPool pool;
    
    protected void setUp() throws Exception {
//        super.setUp();
        pool = IBMMqConnectionPoolFactoryImpl.getFactory().createMqConnectionPool();
    }
    
    protected void tearDown() throws Exception {
//        super.tearDown();
        pool.close();
    }

    /*
     * Test method for 'org.netflexity.api.mq.ibm.pool.IBMMqConnectionPoolImpl.getInstance()'
     */
    public void testGetInstance() {
        PoolableKey key = new PoolableKey("DWMQ10", "127.0.0.1", 1415, "QFLEX1");
        
        MQQueueManager conn = null;
        boolean success = true;
        try {
            // Get 1.
            conn = (MQQueueManager)pool.borrowObject(key);
        }
        catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        finally{
            if(success){
                try {
                    conn.disconnect();
                }
                catch (MQException e1) {
                    e1.printStackTrace();
                }
            }
        }
        
        try {
            // Get 2.
            conn = (MQQueueManager)pool.borrowObject(key);
        }
        catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        finally{
            if(success){
                try {
                    conn.disconnect();
                }
                catch (MQException e1) {
                    e1.printStackTrace();
                }
            }
        }
        
        try {
            // Wait 10 sec.
            Thread.sleep(10000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        PCFAgent pcfConn = null;
        try {
            // Get 3.
            conn = (MQQueueManager)pool.borrowObject(key);
            pcfConn = new PCFAgent(conn);
        }
        catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        finally{
            if(success){
                try {
                    pcfConn.disconnect();
                    if(conn.isConnected()){
                        //System.err.println("Not disconnected after PCFAgent.disconnect()");
                        conn.disconnect();
                    }
                }
                catch (MQException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /*
     * Test method for 'org.netflexity.api.mq.ibm.pool.IBMMqConnectionPoolImpl.borrowObject(Object)'
     */
    public void testBorrowObjectObject() {

    }

    /*
     * Test method for 'org.netflexity.api.mq.ibm.pool.IBMMqConnectionPoolImpl.returnObject(Object, Object)'
     */
    public void testReturnObjectObjectObject() {

    }

}
