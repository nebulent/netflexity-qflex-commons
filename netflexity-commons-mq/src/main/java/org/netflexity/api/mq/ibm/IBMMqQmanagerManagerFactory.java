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
package org.netflexity.api.mq.ibm;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.mq.MqException;
import org.netflexity.api.mq.MqQmanagerManager;
import org.netflexity.api.mq.MqQmanagerManagerContext;
import org.netflexity.api.mq.MqQmanagerManagerFactory;
import org.netflexity.api.mq.ibm.enums.WebsphereMQVersionEnum;
import org.netflexity.api.mq.ibm.enums.WebspherePlatformEnum;

/**
 * Class QmanagerManagerFactory
 *
 * Created on: Sep 22, 2004
 * @author FEDORMAX
 */
public final class IBMMqQmanagerManagerFactory implements MqQmanagerManagerFactory {
    
    private static final MqQmanagerManagerFactory factory = new IBMMqQmanagerManagerFactory();
    
    /**
     * Do not allow to create instances.
     */
    private IBMMqQmanagerManagerFactory() {
    }
    
    /**
     * @return
     */
    public static MqQmanagerManagerFactory getFactory(){
        return factory;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanagerManagerFactory#createMqQmanagerManager(org.netflexity.api.mq.MqQmanagerManagerContext)
     */
    @Override
    public MqQmanagerManager createMqQmanagerManager(MqQmanagerManagerContext context) throws MqException{
        // Find out if this is a mainframe queue manager.
    	if (WebspherePlatformEnum.ZOS.equals(context.getOs()) && WebsphereMQVersionEnum.WSMQv5X.equals(context.getVersion())){
    	    if((StringUtils.isNotBlank(context.getSslKeyStore()) || StringUtils.isNotBlank(context.getSslTrustStore())) && (StringUtils.isNotBlank(context.getSslKeyStorePassword()) || StringUtils.isNotBlank(context.getSslTrustStorePassword()))){
                return new IBMMQSCMqQmanagerManager(context.getQmanagerName(), context.getHost(), context.getPort(), context.getChannelName(), context.getSslKeyStore(), context.getSslKeyStorePassword(), context.getSslTrustStore(), context.getSslTrustStorePassword(), context.getSslCipherSpec().getCipherSuite(), context.getSslPeerName(), context.getRequestQueueName(), context.getReplyQueueName());
            }
            return new IBMMQSCMqQmanagerManager(context.getQmanagerName(), context.getHost(), context.getPort(), context.getChannelName(), context.getRequestQueueName(), context.getReplyQueueName());
    	}
    	else{
            if((StringUtils.isNotBlank(context.getSslKeyStore()) || StringUtils.isNotBlank(context.getSslTrustStore())) && (StringUtils.isNotBlank(context.getSslKeyStorePassword()) || StringUtils.isNotBlank(context.getSslTrustStorePassword()))){
                return new IBMPCFMqQmanagerManager(context.getQmanagerName(), context.getHost(), context.getPort(), context.getChannelName(), context.getReplyQueueName(), context.getSslKeyStore(), context.getSslKeyStorePassword(), context.getSslTrustStore(), context.getSslTrustStorePassword(), context.getSslCipherSpec().getCipherSuite(), context.getSslPeerName());
            }
            return new IBMPCFMqQmanagerManager(context.getQmanagerName(), context.getHost(), context.getPort(), context.getChannelName(), context.getReplyQueueName());
    	}
    }
}
