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

import java.io.Serializable;

public interface MqMessage extends Serializable{

    /**
     * @return Returns the messageHeader.
     */
    public MqMessageHeader getMessageHeader();

    /**
     * @param messageHeader The messageHeader to set.
     */
    public void setMessageHeader(MqMessageHeader header);

    /**
     * @return Returns the data.
     */
    public String getData();

    /**
     * @param data The data to set.
     */
    public void setData(String data);

    /**
     * @return Returns the description.
     */
    public String getDescription();

    /**
     * @param description The description to set.
     */
    public void setDescription(String description);

    /**
     * @return Returns the id.
     */
    public String getId();

    /**
     * @param id The id to set.
     */
    public void setId(String id);

}