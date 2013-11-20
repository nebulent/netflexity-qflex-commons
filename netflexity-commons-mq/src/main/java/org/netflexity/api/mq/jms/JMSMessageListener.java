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
package org.netflexity.api.mq.jms;

import javax.jms.MessageListener;

/**
 * MessageListener that only processes a message once.
 * 
 * @author MAX
 *
 */
public interface JMSMessageListener extends MessageListener {
    
    /**
     * @return Returns the completed status.
     */
    public boolean isCompleted();
    
    /**
     * @return Returns the context.
     */
    public JMSMessageListenerContext getContext();
}
