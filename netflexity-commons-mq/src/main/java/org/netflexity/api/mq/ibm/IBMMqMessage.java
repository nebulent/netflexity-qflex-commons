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

import org.netflexity.api.mq.MqMessage;
import org.netflexity.api.mq.MqMessageHeader;

import com.ibm.mq.MQMessage;

/**
 * Class MqMessage holds information about queue message.
 *
 * Created on: May 20, 2004
 * 
 * @author Max Fedorov
 */
public class IBMMqMessage implements MqMessage {

    private String id;
    private String description;
    private String data;
    private MqMessageHeader messageHeader;
    
    public IBMMqMessage(){}
    
    /**
     * @param msg
     */
    public IBMMqMessage(MQMessage msg) {
        this.messageHeader = new IBMMqMessageHeader(msg);
        this.data = IBMPCFUtil.getMessageData(msg);
        this.id = IBMPCFUtil.toHex(msg.messageId);
        if(data != null && data.length() > 80){
            setDescription(data.substring(0, 80));
        }
        else{
            setDescription(data);
        }
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessage#getMessageHeader()
     */
    @Override
    public MqMessageHeader getMessageHeader() {
        return messageHeader;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessage#setMessageHeader(org.netflexity.api.mq.browser.MqMessageHeader)
     */
    @Override
    public void setMessageHeader(MqMessageHeader header) {
        this.messageHeader = header;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessage#getData()
     */
    @Override
    public String getData() {
        return data;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessage#setData(java.lang.String)
     */
    @Override
    public void setData(String data) {
        this.data = data;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessage#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessage#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessage#getId()
     */
    @Override
    public String getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessage#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }
}