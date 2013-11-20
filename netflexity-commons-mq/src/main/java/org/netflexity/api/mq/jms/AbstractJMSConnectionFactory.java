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

import java.util.HashMap;
import java.util.Map;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * @author MAX
 *
 */
public abstract class AbstractJMSConnectionFactory {

    protected ConnectionFactory connectionfactory;
    protected AbstractJMSConnection connection;
    protected String qmanagerName;
    protected String channelName;
    protected String host;
    protected int port;
    protected String sslCipherSuite;
    protected Object _lock = new Object();
    
    protected static Map<String, String> specToSuite = new HashMap<String, String>(){
	{
	    put("NULL_MD5", "SSL_RSA_WITH_NULL_MD5");
	    put("NULL_SHA", "SSL_RSA_WITH_NULL_SHA");
	    put("RC4_MD5_EXPORT", "SSL_RSA_EXPORT_WITH_RC4_40_MD5");
	    put("RC4_MD5_US", "SSL_RSA_WITH_RC4_128_MD5");
	    put("RC4_SHA_US", "SSL_RSA_WITH_RC4_128_SHA");
	    put("RC2_MD5_EXPORT", "SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5");
	    put("DES_SHA_EXPORT", "SSL_RSA_WITH_DES_CBC_SHA");
	    put("RC4_56_SHA_EXPORT1024", "SSL_RSA_EXPORT1024_WITH_RC4_56_SHA");
	    put("DES_SHA_EXPORT1024", "SSL_RSA_EXPORT1024_WITH_DES_CBC_SHA");
	    put("TRIPLE_DES_SHA_US", "SSL_RSA_WITH_3DES_EDE_CBC_SHA");
	    put("TLS_RSA_WITH_AES_128_CBC_SHA", "SSL_RSA_WITH_AES_128_CBC_SHA");
	    put("TLS_RSA_WITH_AES_256_CBC_SHA", "SSL_RSA_WITH_AES_256_CBC_SHA");
	    put("TLS_RSA_WITH_DES_CBC_SHA", "SSL_RSA_WITH_DES_CBC_SHA");
	    put("TLS_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA");
	    put("FIPS_WITH_DES_CBC_SHA", "SSL_RSA_FIPS_WITH_DES_CBC_SHA");
	    put("FIPS_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_FIPS_WITH_3DES_EDE_CBC_SHA");
	}
    };

    /**
     * @param qmanagerName
     * @param channelName
     * @param host
     * @param port
     * @throws JMSException
     */
    public AbstractJMSConnectionFactory(String qmanagerName, String channelName, String host, int port, String sslCipherSpec) throws JMSException {
        this.qmanagerName = qmanagerName;
        this.channelName = channelName;
        this.host = host;
        this.port = port;
	this.sslCipherSuite = convertSpec(sslCipherSpec);
        this.connectionfactory = createConnectionFactory();
    }
    
    private String convertSpec(String spec){
	String suite = specToSuite.get(spec);
	if(suite == null){
//	    System.out.println("Not found suite for spec " + spec);
	    return spec;
	}
	return suite;
    }

    /**
     * @return apropriate instance of connection factory.
     */
    protected abstract ConnectionFactory createConnectionFactory() throws JMSException;

    /**
     * @return Returns the channelName.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Remove the connection.
     */
    public void removeConnection() {
        synchronized (_lock) {
            connection = null;
        }
    }

    /**
     * Close connection factory.
     */
    public void close() throws JMSException {
        synchronized (_lock) {
            if (connection != null) {
                connection.close();
            }
            connection = null;
            connectionfactory = null;
        }
    }

    /**
     * @return Returns the host.
     */
    public String getHost() {
        return host;
    }

    /**
     * @return Returns the port.
     */
    public int getPort() {
        return port;
    }

    /**
     * @return Returns the qmanagerName.
     */
    public String getQmanagerName() {
        return qmanagerName;
    }
}
