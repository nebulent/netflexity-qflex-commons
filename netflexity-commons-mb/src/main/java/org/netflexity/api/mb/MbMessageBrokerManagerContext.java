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
package org.netflexity.api.mb;

import java.io.Serializable;

public interface MbMessageBrokerManagerContext extends Serializable{

    /**
     * @return Returns the brokerName.
     */
    public String getBrokerName();

    /**
     * @param brokerName The brokerName to set.
     */
    public void setBrokerName(String brokerName);

    /**
     * @return Returns the brokerUuid.
     */
    public String getBrokerUuid();

    /**
     * @param brokerUuid The brokerUuid to set.
     */
    public void setBrokerUuid(String brokerUuid);

    /**
     * @return Returns the channelName.
     */
    public String getChannelName();

    /**
     * @param channelName The channelName to set.
     */
    public void setChannelName(String channelName);

    /**
     * @return Returns the host.
     */
    public String getHost();

    /**
     * @param host The host to set.
     */
    public void setHost(String host);

    /**
     * @return Returns the port.
     */
    public int getPort();

    /**
     * @param port The port to set.
     */
    public void setPort(int port);

    /**
     * @return Returns the qmanagerName.
     */
    public String getQmanagerName();

    /**
     * @param qmanagerName The qmanagerName to set.
     */
    public void setQmanagerName(String qmanagerName);

    /**
     * @return Returns the subQueueName.
     */
    public String getSubQueueName();

    /**
     * @param subQueueName The subQueueName to set.
     */
    public void setSubQueueName(String subQueueName);

    /**
     * @return Returns the brokerVersion.
     */
    public String getBrokerVersion();

    /**
     * @param brokerVersion The brokerVersion to set.
     */
    public void setBrokerVersion(String brokerVersion);
    
    
    public void setSslCipherSpec(String sslCipherSpec);
    
    public String getSslCipherSpec();

}