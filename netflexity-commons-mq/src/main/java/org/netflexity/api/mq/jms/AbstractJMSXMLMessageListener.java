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

import java.io.ByteArrayOutputStream;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.log4j.Logger;

/**
 * @author MAX
 * 
 */
public abstract class AbstractJMSXMLMessageListener extends AbstractJMSMessageListener {

    public static Logger logger = Logger.getLogger(AbstractJMSXMLMessageListener.class);

    /**
     * @param context
     */
    public AbstractJMSXMLMessageListener(JMSMessageListenerContext context) {
        super(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(Message msg) {
        // Process XML message.
        if (msg instanceof BytesMessage) {
            BytesMessage bytesMsg = (BytesMessage) msg;
            try {
                String xmlSource = toString(bytesMsg);
                logger.info(xmlSource);
                
                // process XML message.
                onXmlMessage(xmlSource);
            }
            catch (JMSException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
    
    /**
     * @param bytesMsg
     * @return
     * @throws JMSException
     */
    protected String toString(BytesMessage bytesMsg) throws JMSException{
        int n = -1;
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
        while ((n = bytesMsg.readBytes(bytes, bytes.length)) != -1) {
            baos.write(bytes, 0, n);
        }
        return baos.toString();
    }
    
    /**
     * @param xml
     */
    public abstract void onXmlMessage(String xml);
}
