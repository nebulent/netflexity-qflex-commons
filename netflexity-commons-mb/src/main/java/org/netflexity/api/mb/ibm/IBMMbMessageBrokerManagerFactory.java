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
package org.netflexity.api.mb.ibm;

import org.netflexity.api.mb.MbMessageBrokerManager;
import org.netflexity.api.mb.MbMessageBrokerManagerContext;
import org.netflexity.api.mb.MbMessageBrokerManagerFactory;

/**
 * Class QmanagerManagerFactory
 *
 * Created on: Sep 22, 2004
 * @author FEDORMAX
 */
public final class IBMMbMessageBrokerManagerFactory implements MbMessageBrokerManagerFactory {
    
    private static final MbMessageBrokerManagerFactory factory = new IBMMbMessageBrokerManagerFactory();
    
    /**
     * Do not allow to create instances.
     */
    private IBMMbMessageBrokerManagerFactory() {
    }
    
    /**
     * @return
     */
    public static MbMessageBrokerManagerFactory getFactory(){
        return factory;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerFactory#createMessageBrokerManager(org.netflexity.api.mb.ibm.IBMMbMessageBrokerManagerContext)
     */
    public MbMessageBrokerManager createMessageBrokerManager(MbMessageBrokerManagerContext context){
        return new IBMMbMessageBrokerManagerImpl(
		context.getBrokerName(), 
		context.getBrokerUuid(), 
		context.getBrokerVersion(), 
		context.getSubQueueName(), 
		context.getQmanagerName(), 
		context.getHost(), 
		context.getPort(), 
		context.getChannelName(),
		context.getSslCipherSpec()
	);
    }
}