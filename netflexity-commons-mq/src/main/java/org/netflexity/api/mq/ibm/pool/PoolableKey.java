package org.netflexity.api.mq.ibm.pool;

import java.io.File;
import java.io.Serializable;

/**
 * @author Max Fedorov
 *
 * Key that contains all necessary information to build an instance of a
 * pooled object.
 */
public class PoolableKey implements Serializable {

    private String qmanagerName;
    private String channelName;
    private String host;
    private int port;
    // SSL info.
    protected boolean isSecure;
    protected String sslKeyStore, sslKeyStorePassword;
    protected String sslTrustStore, sslTrustStorePassword;
    protected String sslCipherSpec;
    protected String sslPeerName;

    /**
     * @param qmanagerName
     * @param host
     * @param port
     * @param channelName
     * @param sslKeyStore
     * @param sslKeyStorePassword
     * @param sslTrustStore
     * @param sslTrustStorePassword
     * @param sslCipherSpec
     * @param sslPeerName
     */
    public PoolableKey(String qmanagerName, String host, int port, String channelName, String sslKeyStore, String sslKeyStorePassword, String sslTrustStore, String sslTrustStorePassword, String sslCipherSpec, String sslPeerName) {
	this(qmanagerName, host, port, channelName);
	this.sslKeyStore = sslKeyStore;
	this.sslKeyStorePassword = sslKeyStorePassword;
	this.sslTrustStore = sslTrustStore;
	this.sslTrustStorePassword = sslTrustStorePassword;
	this.sslCipherSpec = sslCipherSpec;
	this.sslPeerName = sslPeerName;
	this.isSecure = true;
    }

    /**
     * @param qmanagerName
     * @param host
     * @param port
     * @param channelName
     */
    public PoolableKey(String qmanagerName, String host, int port, String channelName) {
	this.qmanagerName = qmanagerName;
	this.host = host;
	this.port = port;
	this.channelName = channelName;
    }

    /**
     * @return
     */
    public String getChannelName() {
	return channelName;
    }

    /**
     * @param channelName
     */
    public void setChannelName(String channelName) {
	this.channelName = channelName;
    }

    /**
     * @return
     */
    public String getHost() {
	return host;
    }

    /**
     * @param host
     */
    public void setHost(String host) {
	this.host = host;
    }

    /**
     * @return
     */
    public int getPort() {
	return port;
    }

    /**
     * @param port
     */
    public void setPort(int port) {
	this.port = port;
    }

    /**
     * @return
     */
    public String getQmanagerName() {
	return qmanagerName;
    }

    /**
     * @param qmanagerName
     */
    public void setQmanagerName(String qmanagerName) {
	this.qmanagerName = qmanagerName;
    }

    /**
     * @return the sslCipherSpec
     */
    public String getSslCipherSpec() {
	return sslCipherSpec;
    }

    /**
     * @param sslCipherSpec the sslCipherSpec to set
     */
    public void setSslCipherSpec(String sslCipherSpec) {
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return toString().hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return qmanagerName + File.separator + host + File.separator + port + File.separator + channelName + File.separator + String.valueOf(isSecure);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (obj != null && obj instanceof PoolableKey) {
	    return obj.hashCode() == hashCode();
	}
	return false;
    }
}
