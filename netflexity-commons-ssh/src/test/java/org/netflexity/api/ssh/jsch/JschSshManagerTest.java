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
package org.netflexity.api.ssh.jsch;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.netflexity.api.ssh.SshException;
import org.netflexity.api.ssh.jsch.JschSshManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author MAX
 *
 */
public class JschSshManagerTest extends TestCase{

    protected ApplicationContext appContext;
    protected Logger logger;
    protected JschSshManager sshManager;
    
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
        sshManager = (JschSshManager) appContext.getBean("jschSshSession");
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
    }

    public void testAll() {
        getFileListing();
        changeWorkingDirectory();
        getTextFileContent();
    }
    
    public void executeShellCommand() {
        try {
            String result = sshManager.executeShellCommand("qflex_ssh.sh a b");
            logger.info(result);
        }
        catch (SshException e) {
            logger.error(e.getMessage(), e);
            Assert.assertTrue(e.getMessage(), false);
        }
    }
    
    /**
     * Test method for {@link org.netflexity.api.ssh.jsch.JschSshManager#getFileListing()}.
     */
    public void getFileListing() {
        try {
            List listings = sshManager.getFileListing();
            for (Iterator iter = listings.iterator(); iter.hasNext();) {
                String fileName = (String) iter.next();
                logger.info(fileName);
            }
        }
        catch (SshException e) {
            logger.error(e.getMessage(), e);
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    /**
     * Test method for {@link org.netflexity.api.ssh.jsch.JschSshManager#changeWorkingDirectory(java.lang.String)}.
     */
    public void changeWorkingDirectory() {
        try {
            sshManager.changeWorkingDirectory("/var/mqm/errors");
            String result = sshManager.getWorkingDirectory();
            Assert.assertEquals("/var/mqm/errors", result);
        }
        catch (SshException e) {
            logger.error(e.getMessage(), e);
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    /**
     * Test method for {@link org.netflexity.api.ssh.jsch.JschSshManager#getTextFileContent(java.lang.String)}.
     */
    public void getTextFileContent() {
        try {
            String result = sshManager.getTextFileContent("AMQ16665.0.FDC");
            logger.info(result);
        }
        catch (SshException e) {
            logger.error(e.getMessage(), e);
            Assert.assertTrue(e.getMessage(), false);
        }
    }

}
