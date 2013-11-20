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
package org.netflexity.api.orm;

import java.util.Collection;

public interface Metadata {

    /**
     * @return Returns the className.
     */
    String getClassName();

    /**
     * @param className The className to set.
     */
    void setClassName(String className);

    /**
     * @return Returns the name.
     */
    String getName();

    /**
     * @param name The name to set.
     */
    void setName(String name);

    /**
     * @return Returns the Map.
     */
    Collection getProperties();

    /**
     * @param key
     * @return
     */
    String getProperty(String key);

    /**
     * @param key
     * @param defval
     * @return
     */
    String getProperty(String key, String defval);

    /**
     * @param properties The properties to set.
     */
    void addProperty(String key, String value);

    /**
     * @return Returns the javaClass.
     */
    Class getJavaClass();

}