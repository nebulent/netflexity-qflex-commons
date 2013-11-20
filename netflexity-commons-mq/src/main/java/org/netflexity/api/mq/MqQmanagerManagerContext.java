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

import org.netflexity.api.mq.ibm.enums.WebsphereMQVersionEnum;
import org.netflexity.api.mq.ibm.enums.WebspherePlatformEnum;
import org.netflexity.api.mq.ibm.enums.WebsphereSSLCipherSpecEnum;

/**
 * @author MAX
 * 
 * Context object for qmanager manager factory.
 */
public class MqQmanagerManagerContext implements Serializable {
    
    private String qmanagerName;
    private String host;
    private int port;
    private String channelName;
    private String requestQueueName, replyQueueName;
    private WebspherePlatformEnum os;
    private WebsphereMQVersionEnum version;
    
    // SSL info.
    protected String sslKeyStore, sslKeyStorePassword;
    protected String sslTrustStore, sslTrustStorePassword;
    private WebsphereSSLCipherSpecEnum sslCipherSpec;
    protected String sslPeerName;
    
    /**
     * @param qmanagerName
     * @param host
     * @param port
     * @param channelName
     * @param requestQueueName
     * @param replyQueueName
     * @param os
     * @param version
     * @param sslKeyStore
     * @param sslKeyStorePassword
     * @param sslTrustStore
     * @param sslTrustStorePassword
     * @param sslCipherSpec
     * @param sslPeerName
     */
    public MqQmanagerManagerContext(String qmanagerName, String host, int port, String channelName, String requestQueueName, String replyQueueName, WebspherePlatformEnum os, WebsphereMQVersionEnum version, String sslKeyStore, String sslKeyStorePassword, String sslTrustStore, String sslTrustStorePassword, WebsphereSSLCipherSpecEnum sslCipherSpec, String sslPeerName) {
        this(qmanagerName, host, port, channelName, requestQueueName, replyQueueName, os, version);
        this.sslKeyStore = sslKeyStore;
        this.sslKeyStorePassword = sslKeyStorePassword;
        this.sslTrustStore = sslTrustStore;
        this.sslTrustStorePassword = sslTrustStorePassword;
        this.sslPeerName = sslPeerName;
	if(sslKeyStore == null && sslKeyStorePassword == null && sslTrustStore == null && sslTrustStorePassword == null){
	    this.sslCipherSpec = null;
	}else{
	    this.sslCipherSpec = sslCipherSpec;	
	}
    }

    /**
     * @param qmanagerName
     * @param host
     * @param port
     * @param channelName
     * @param requestQueueName
     * @param replyQueueName
     * @param os
     */
    public MqQmanagerManagerContext(String qmanagerName, String host, int port, String channelName, String requestQueueName, String replyQueueName, WebspherePlatformEnum os, WebsphereMQVersionEnum version) {
        this.qmanagerName = qmanagerName;
        this.host = host;
        this.port = port;
        this.channelName = channelName;
        this.requestQueueName = requestQueueName;
        this.replyQueueName = replyQueueName;
        this.os = os;
        this.version = version;
    }
    
    /**
     * @return Returns the channelName.
     */
    public String getChannelName() {
        return channelName;
    }
    /**
     * @param channelName The channelName to set.
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    /**
     * @return Returns the host.
     */
    public String getHost() {
        return host;
    }
    /**
     * @param host The host to set.
     */
    public void setHost(String host) {
        this.host = host;
    }
    /**
     * @return Returns the os.
     */
    public WebspherePlatformEnum getOs() {
        return os;
    }
    /**
     * @param os The os to set.
     */
    public void setOs(WebspherePlatformEnum os) {
        this.os = os;
    }
    /**
     * @return Returns the port.
     */
    public int getPort() {
        return port;
    }
    /**
     * @param port The port to set.
     */
    public void setPort(int port) {
        this.port = port;
    }
    /**
     * @return Returns the qmanagerName.
     */
    public String getQmanagerName() {
        return qmanagerName;
    }
    /**
     * @param qmanagerName The qmanagerName to set.
     */
    public void setQmanagerName(String qmanagerName) {
        this.qmanagerName = qmanagerName;
    }
    /**
     * @return Returns the replyQueueName.
     */
    public String getReplyQueueName() {
        return replyQueueName;
    }

    /**
     * @param replyQueueName The replyQueueName to set.
     */
    public void setReplyQueueName(String replyQueueName) {
        this.replyQueueName = replyQueueName;
    }

    /**
     * @return Returns the requestQueueName.
     */
    public String getRequestQueueName() {
        return requestQueueName;
    }

    /**
     * @param requestQueueName The requestQueueName to set.
     */
    public void setRequestQueueName(String requestQueueName) {
        this.requestQueueName = requestQueueName;
    }

	/**
	 * @return
	 */
	public WebsphereMQVersionEnum getVersion() {
		return version;
	}

	/**
	 * @param version
	 */
	public void setVersion(WebsphereMQVersionEnum version) {
		this.version = version;
	}

    /**
     * @return the sslCipherSpec
     */
    public WebsphereSSLCipherSpecEnum getSslCipherSpec() {
        return sslCipherSpec;
    }

    /**
     * @param sslCipherSpec the sslCipherSpec to set
     */
    public void setSslCipherSpec(WebsphereSSLCipherSpecEnum sslCipherSpec) {
        this.sslCipherSpec = sslCipherSpec;
    }

    /**
     * @return the sslKeyStore
     */
    public String getSslKeyStore() {
        return sslKeyStore;
    }

    /**
     * @param sslKeyStore the sslKeyStore to set
     */
    public void setSslKeyStore(String sslKeyStore) {
        this.sslKeyStore = sslKeyStore;
    }

    /**
     * @return the sslKeyStorePassword
     */
    public String getSslKeyStorePassword() {
        return sslKeyStorePassword;
    }

    /**
     * @param sslKeyStorePassword the sslKeyStorePassword to set
     */
    public void setSslKeyStorePassword(String sslKeyStorePassword) {
        this.sslKeyStorePassword = sslKeyStorePassword;
    }

    /**
     * @return the sslPeerName
     */
    public String getSslPeerName() {
        return sslPeerName;
    }

    /**
     * @param sslPeerName the sslPeerName to set
     */
    public void setSslPeerName(String sslPeerName) {
        this.sslPeerName = sslPeerName;
    }

    /**
     * @return the sslTrustStore
     */
    public String getSslTrustStore() {
        return sslTrustStore;
    }

    /**
     * @param sslTrustStore the sslTrustStore to set
     */
    public void setSslTrustStore(String sslTrustStore) {
        this.sslTrustStore = sslTrustStore;
    }

    /**
     * @return the sslTrustStorePassword
     */
    public String getSslTrustStorePassword() {
        return sslTrustStorePassword;
    }

    /**
     * @param sslTrustStorePassword the sslTrustStorePassword to set
     */
    public void setSslTrustStorePassword(String sslTrustStorePassword) {
        this.sslTrustStorePassword = sslTrustStorePassword;
    }
}
