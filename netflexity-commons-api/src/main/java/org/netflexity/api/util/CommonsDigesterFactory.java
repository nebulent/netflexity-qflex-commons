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
package org.netflexity.api.util;

import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;

/**
 * @author FEDORMAX
 *
 * CommonsDigesterFactory
 */
public class CommonsDigesterFactory {
    // Singleton instance.
    private static CommonsDigesterFactory instance = new CommonsDigesterFactory();
    
    // Collection of digesters.
    private Map digesters = Collections.synchronizedMap(new WeakHashMap());
    
    // Private constructor.
    private CommonsDigesterFactory(){}
    
    /**
     * @return
     */
    public static CommonsDigesterFactory getInstance() {
        return instance;
    }
    
    /**
     * @param rulesFilePath
     * @param classLoader
     * @return
     */
    public Digester getDigester(String rulesFilePath, ClassLoader classLoader) {
        synchronized(digesters){
            Digester digester = (Digester)digesters.get(rulesFilePath);
            if(digester == null){
                // Get classloader.
                if (classLoader == null) {
            		classLoader = CommonsDigesterFactory.class.getClassLoader();
                }
                
                // Retrieve URL.
            	URL rulesUrl = null;
                if (classLoader != null){
                    rulesUrl = classLoader.getResource(rulesFilePath);
                }
                if (rulesUrl == null){
                    rulesUrl = ClassLoader.getSystemResource(rulesFilePath);
                }
                
                // Load and store digester rules.
                digester = DigesterLoader.createDigester(rulesUrl);
    			digesters.put(rulesFilePath, digester);
            }
            return digester;
        }
    }
}
