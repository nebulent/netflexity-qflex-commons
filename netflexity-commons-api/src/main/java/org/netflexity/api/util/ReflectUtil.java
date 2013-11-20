package org.netflexity.api.util;


/**
 * @author MFedorov
 *
 * Various Reflection Utilities.
 */
public final class ReflectUtil {

    /**
	 * @param className
	 * @param loader
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class findClass(String className, ClassLoader loader) throws ClassNotFoundException {
        try {
            return Class.forName(className, true, loader);
        }
        catch (ClassNotFoundException e) {
        }
        
        ClassLoader loader2 = ReflectUtil.class.getClassLoader();
        if (loader2 != loader){
            try {
                return Class.forName(className, true, loader2);
            }
            catch (ClassNotFoundException e2) {
            }
        }
        
        // See if we already tried this.
        if (loader != null && loader2 != null) {
            return Class.forName(className, true, null);
        }
        throw new ClassNotFoundException("Failed to find class: " + className);
    }
}
