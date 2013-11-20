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


/**
 * @author MAX
 * 
 */
public abstract class AbstractJMSMessageListener implements JMSMessageListener {

    protected JMSMessageListenerContext context;
    protected boolean completed;
    
    /**
     * @param context
     */
    public AbstractJMSMessageListener(JMSMessageListenerContext context) {
        this.context = context;
    }

    /* (non-Javadoc)
     * @see com.netflexity.qflex.mq.jms.IQflexMessageListener#isCompleted()
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed The completed to set.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.JMSMessageListener#getContext()
     */
    public JMSMessageListenerContext getContext() {
        return context;
    }
}
