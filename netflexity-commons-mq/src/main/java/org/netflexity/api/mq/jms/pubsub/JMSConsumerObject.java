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
package org.netflexity.api.mq.jms.pubsub;


/**
 * @author Max Fedorov
 *
 * Interface that marks all the classes as JMS-enabled.
 */
public interface JMSConsumerObject {
    
    /**
     * @return Returns the channelName.
     */
    public String getChannelName();

    /**
     * @return Returns the destinationName.
     */
    public String getDestinationName();

    /**
     * @return Returns the host.
     */
    public String getHost();

    /**
     * @return Returns the identifier.
     */
    public String getIdentifier();

    /**
     * @return Returns the port.
     */
    public int getPort();

    /**
     * @return Returns the qmanagerName.
     */
    public String getQmanagerName();

    /**
     * @return Returns the queueName.
     */
    public String getQueueName();
    
    public String getSslCipherSpec();
}
