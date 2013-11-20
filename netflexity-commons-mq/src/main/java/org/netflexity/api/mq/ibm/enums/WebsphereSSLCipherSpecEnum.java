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
package org.netflexity.api.mq.ibm.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;

/**
 * Enumeration for all supported Cipher specs.
 * 
 * @author MAX
 * 
 */
public final class WebsphereSSLCipherSpecEnum extends Enum {

    public static final WebsphereSSLCipherSpecEnum NULL_MD5 = new WebsphereSSLCipherSpecEnum("NULL_MD5", "SSL_RSA_WITH_NULL_MD5");
    public static final WebsphereSSLCipherSpecEnum NULL_SHA = new WebsphereSSLCipherSpecEnum("NULL_SHA", "SSL_RSA_WITH_NULL_SHA");
    public static final WebsphereSSLCipherSpecEnum RC4_MD5_EXPORT = new WebsphereSSLCipherSpecEnum("RC4_MD5_EXPORT", "SSL_RSA_EXPORT_WITH_RC4_40_MD5");
    public static final WebsphereSSLCipherSpecEnum RC4_MD5_US = new WebsphereSSLCipherSpecEnum("RC4_MD5_US", "SSL_RSA_WITH_RC4_128_MD5");
    public static final WebsphereSSLCipherSpecEnum RC4_SHA_US = new WebsphereSSLCipherSpecEnum("RC4_SHA_US", "SSL_RSA_WITH_RC4_128_SHA");
    public static final WebsphereSSLCipherSpecEnum RC2_MD5_EXPORT = new WebsphereSSLCipherSpecEnum("RC2_MD5_EXPORT", "SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5");
    public static final WebsphereSSLCipherSpecEnum DES_SHA_EXPORT = new WebsphereSSLCipherSpecEnum("DES_SHA_EXPORT", "SSL_RSA_WITH_DES_CBC_SHA");
    public static final WebsphereSSLCipherSpecEnum RC4_56_SHA_EXPORT1024 = new WebsphereSSLCipherSpecEnum("RC4_56_SHA_EXPORT1024", "SSL_RSA_EXPORT1024_WITH_RC4_56_SHA");
    public static final WebsphereSSLCipherSpecEnum DES_SHA_EXPORT1024 = new WebsphereSSLCipherSpecEnum("DES_SHA_EXPORT1024", "SSL_RSA_EXPORT1024_WITH_DES_CBC_SHA");
    public static final WebsphereSSLCipherSpecEnum TRIPLE_DES_SHA_US = new WebsphereSSLCipherSpecEnum("TRIPLE_DES_SHA_US", "SSL_RSA_WITH_3DES_EDE_CBC_SHA");
    public static final WebsphereSSLCipherSpecEnum TLS_RSA_WITH_AES_128_CBC_SHA = new WebsphereSSLCipherSpecEnum("TLS_RSA_WITH_AES_128_CBC_SHA", "SSL_RSA_WITH_AES_128_CBC_SHA");
    public static final WebsphereSSLCipherSpecEnum TLS_RSA_WITH_AES_256_CBC_SHA = new WebsphereSSLCipherSpecEnum("TLS_RSA_WITH_AES_256_CBC_SHA", "SSL_RSA_WITH_AES_256_CBC_SHA");
    public static final WebsphereSSLCipherSpecEnum TLS_RSA_WITH_DES_CBC_SHA = new WebsphereSSLCipherSpecEnum("TLS_RSA_WITH_DES_CBC_SHA", "SSL_RSA_WITH_DES_CBC_SHA");
    public static final WebsphereSSLCipherSpecEnum TLS_RSA_WITH_3DES_EDE_CBC_SHA = new WebsphereSSLCipherSpecEnum("TLS_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA");
    public static final WebsphereSSLCipherSpecEnum FIPS_WITH_DES_CBC_SHA = new WebsphereSSLCipherSpecEnum("FIPS_WITH_DES_CBC_SHA", "SSL_RSA_FIPS_WITH_DES_CBC_SHA");
    public static final WebsphereSSLCipherSpecEnum FIPS_WITH_3DES_EDE_CBC_SHA = new WebsphereSSLCipherSpecEnum("FIPS_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_FIPS_WITH_3DES_EDE_CBC_SHA");
    
    private String cipherSuite;
    
    /**
     * @param arg0
     */
    public WebsphereSSLCipherSpecEnum(String type, String cipherSuite) {
        super(type);
        this.cipherSuite = cipherSuite;
    }

    /**
     * @return the cipherSuite
     */
    public String getCipherSuite() {
        return cipherSuite;
    }

    /**
     * @param cipherSuite the cipherSuite to set
     */
    public void setCipherSuite(String cipherSuite) {
        this.cipherSuite = cipherSuite;
    }

    /**
     * @param type
     * @return
     */
    public static WebsphereSSLCipherSpecEnum getEnum(String type) {
        return (WebsphereSSLCipherSpecEnum) getEnum(WebsphereSSLCipherSpecEnum.class, type);
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(WebsphereSSLCipherSpecEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(WebsphereSSLCipherSpecEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(WebsphereSSLCipherSpecEnum.class);
    }
}
