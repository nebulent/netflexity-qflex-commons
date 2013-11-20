package org.netflexity.api.mq.ibm.net;

import java.net.InetAddress;

public class MqQmanagerAddress {

    private InetAddress inetAddress;
    private int port;
    private String queueManagerName;
    private String channelName;

    /**
     * @param inetAddress
     * @param port
     * @param queueManagerName
     * @param channelName
     */
    public MqQmanagerAddress(InetAddress inetAddress, int port, String queueManagerName, String channelName) {
        super();
        this.inetAddress = inetAddress;
        this.port = port;
        this.queueManagerName = queueManagerName;
        this.channelName = channelName;
    }

    /**
     * @return the inetAddress
     */
    public InetAddress getInetAddress() {
        return inetAddress;
    }

    /**
     * @param inetAddress the inetAddress to set
     */
    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the queueManagerName
     */
    public String getQueueManagerName() {
        return queueManagerName;
    }

    /**
     * @param queueManagerName the queueManagerName to set
     */
    public void setQueueManagerName(String queueManagerName) {
        this.queueManagerName = queueManagerName;
    }

    /**
     * @return the channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @param channelName the channelName to set
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
