package org.netflexity.api.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Properties;

import org.apache.commons.discovery.DiscoveryException;
import org.apache.commons.discovery.tools.DiscoverClass;

/**
 * @author Max Fedorov
 *
 * Common methods for Apache Commons Discovery API.
 */
public class DiscoveryUtil {
    
    /**
     * Finds the concrete class that corresponds to the specified 
     * conceptual class.
     * 
     * @param conceptualClass Class The conceptual class for which a concrete class should be found.
     * @param conrete implementations collected from properties files.
     * 
     * @return Class The concrete class corresponding to the
     * specified conceptual class.
     */
    public static Class find(Class conceptualClass, Properties discoveryClasses) throws InstantiationException{
        try {
            DiscoverClass dc = new DiscoverClass();
            return dc.find(conceptualClass, discoveryClasses);
        }
        catch (DiscoveryException e) {
            int modifiers = conceptualClass.getModifiers();
            if (!Modifier.isAbstract(modifiers) && !Modifier.isInterface(modifiers)) {
                return conceptualClass;
            }
        }
        throw new InstantiationException("Concrete class not found " + discoveryClasses);
    }

    /**
     * Creates a new instance of the concrete class that corresponds to the 
     * specified conceptual class.
     * 
     * @param conceptualClass Class The conceptual class for which a concrete class should be found.
     * @param conrete implementations collected from properties files.
     * 
     * @return Object A new instance.
     */
    public static Object newInstance(Class conceptualClass, Properties discoveryClasses) throws InstantiationException{
        try {
            Class cl = find(conceptualClass, discoveryClasses);
            return createInstance(cl);
        }
        catch (Exception e) {
            try {
                int modifiers = conceptualClass.getModifiers();
                if (!Modifier.isAbstract(modifiers) && !Modifier.isInterface(modifiers)) {
                    return createInstance(conceptualClass);
                }
                else{
                    throw e;
                }
            }
            catch (Throwable ie) {
                throw new InstantiationException(ie.getMessage());
            }
        }
    }

    /**
     * Convienence method to instantiate a class.
     * This method uses reflection to get the default constructor, and
     * invokes it (this is done to enable invoking non-public constructor)
     * 
     * @param cl Class The class to instantiate
     * @return Object A new instance.
     */
    private static Object createInstance(Class cl) throws InstantiationException {
        try {
            Constructor ctor = cl.getDeclaredConstructor(null);
            if (!ctor.isAccessible()) {
                ctor.setAccessible(true);
            }
            return ctor.newInstance(null);
        }
        catch (Exception ex) {
            throw new InstantiationException(ex.getMessage());
        }
    }
}
