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
package org.netflexity.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Max Fedorov
 *
 * Utility class that loads property files and keeps them in memory.
 */
public final class PropertiesLoader {
    
    // Singleton instance.
    private static PropertiesLoader instance = new PropertiesLoader();
    // Cache of all referenced properties.
    private Map propertiesCache = Collections.synchronizedMap(new HashMap());
    
    /**
     * Constructor to prevent everyone from creating a new instance.
     */
    private PropertiesLoader(){
    }
    
    /**
     * @return
     */
    public static PropertiesLoader getInstance(){
        return instance;
    }
    
    /**
     * @param pathName
     * @return
     */
    public Properties getProperty(String pathName){
        return getProperty(pathName, PropertiesLoader.class.getClassLoader());
    }
    
    /**
     * @param pathName
     * @return
     */
    public Properties getProperty(String pathName, ClassLoader loader){
        synchronized(propertiesCache){
            Properties prop = (Properties)propertiesCache.get(pathName);
            if (prop == null) {
    			prop = new Properties();
                try {
                    // Load properties.
                    InputStream in = PropertiesUtil.getInputStream(pathName, loader);
    				prop.load(in);
                    
                    // Add properties to cache.
                    propertiesCache.put(pathName, prop);
    			} 
                catch (IOException e) {
                    throw new RuntimeException("Failed to load properties :" + pathName, e);
    			}
    		}
            return prop;
        }
    }
}
