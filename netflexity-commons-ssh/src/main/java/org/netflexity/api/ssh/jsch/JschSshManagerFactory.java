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
package org.netflexity.api.ssh.jsch;

import org.netflexity.api.ssh.SshException;
import org.netflexity.api.ssh.SshManager;
import org.netflexity.api.ssh.SshManagerContext;
import org.netflexity.api.ssh.SshManagerFactory;

/**
 * @author MAX
 *
 */
public class JschSshManagerFactory implements SshManagerFactory {

    private static final SshManagerFactory factory = new JschSshManagerFactory();
    
    /**
     * Default.
     */
    private JschSshManagerFactory() {
    }

    /**
     * @return
     */
    public static SshManagerFactory getFactory(){
        return factory;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.ssh.SshManagerFactory#createSshManager(org.netflexity.api.ssh.SshManagerContext)
     */
    public SshManager createSshManager(SshManagerContext context) throws SshException {
        return new JschSshManager(context.getConnectionPool(), context.getHost(), context.getPort(), context.getUserName(), context.getPassword(), context.getRootDir());
    }
}
