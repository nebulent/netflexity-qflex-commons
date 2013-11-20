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
package org.netflexity.api.ssh.jsch.mq.ibm;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.netflexity.api.ssh.SshException;
import org.netflexity.api.ssh.jsch.JschSshManager;
import org.netflexity.api.ssh.jsch.JschSshManagerTest;
import org.netflexity.api.ssh.jsch.mq.AmqRecord;
import org.netflexity.api.ssh.jsch.mq.FdcFile;
import org.netflexity.api.ssh.jsch.mq.MqErrorFileManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IBMMqErrorFileManagerTest extends TestCase{
    
    public static final String QMANAGER = "LD01";
    protected ApplicationContext appContext;
    protected Logger logger;
    protected JschSshManager sshManager;
    protected MqErrorFileManager mqErrorFileManager;
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        appContext = new ClassPathXmlApplicationContext(JschSshManagerTest.class.getPackage().getName()
                .replace('.', '/') + "/applicationContext.xml");
        
        // Configure log4j.
        BasicConfigurator.configure();
        logger = Logger.getLogger(this.getClass().getName());
        
        // Retrieve ssh session factory bean.
        sshManager = (JschSshManager) appContext.getBean("jschSshSession2");
        
        // Retrieve MQ ErrorFileManager.
        mqErrorFileManager = (MqErrorFileManager) appContext.getBean("mqErrorFileManager");
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        sshManager = null;
    }
    
    public void testAll() {
        long now = System.currentTimeMillis();
        long thirtyDays = (90 * 24 * 3600000L);
        long ninetyDaysAgo = (now - thirtyDays);
        getErrorCodeOnInterval(ninetyDaysAgo, now);
        getNewFdcFilesOnInterval(ninetyDaysAgo, now);
    }
    
    public void getErrorCodeOnInterval(long start, long end) {
        try {
            AmqRecord amqRecord = mqErrorFileManager.getErrorCodeOnInterval(sshManager, "AMQ9209", QMANAGER, start, end);
            Assert.assertNotNull(amqRecord);
            logger.info(amqRecord.getDate() + ", error code=" + amqRecord.getErrorCode());
        }
        catch (SshException e) {
            logger.error(e.getMessage(), e);
            Assert.assertTrue(e.getMessage(), false);
        }
    }
    
    public void getNewFdcFilesOnInterval(long start, long end) {
        try {
            List newFdcFiles = mqErrorFileManager.getNewFdcFilesOnInterval(sshManager, QMANAGER, start, end);
            for (Iterator iter = newFdcFiles.iterator(); iter.hasNext();) {
                FdcFile file = (FdcFile) iter.next();
                logger.info(file.getAccessDate() + ",size=" + file.getSize());
            }
        }
        catch (SshException e) {
            logger.error(e.getMessage(), e);
            Assert.assertTrue(e.getMessage(), false);
        }
    }
}
