package org.netflexity.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * @author Max Fedorov
 *
 * Functions, specifically designed to handle digester class lookup.
 */
public class PropertiesUtil {
	
	/**
	 * @param propertyFileList (list of properties files, separated by comma)
	 * @return
	 * @throws IOException
	 */
	public static Properties getProperties(String propertyFileList)
		throws IOException {
		return getProperties(propertyFileList, PropertiesUtil.class.getClassLoader());
	}

	/**
	 * @param propertyFileList (list of properties files, separated by comma)
	 * @param cl - specific Class loader.
	 * @return
	 * @throws IOException
	 */
	public static Properties getProperties(String propertyFileList, ClassLoader classLoader) throws IOException {
		Properties merged = new Properties();
		if (propertyFileList != null) {
			String[] propertyFiles = StringUtils.split(propertyFileList, StringConstants.COMMA);
			for (int i = 0; i < propertyFiles.length; i++) {
                String propertyFile = propertyFiles[i].trim();
				InputStream in = getInputStream(propertyFile, classLoader);
				if (in != null) {
                    Properties prop = new Properties();
                    prop.load(in);
					merged.putAll(prop);
				}
			}
		}
		return merged;
	}
    
    /**
     * @param location
     * @param cl
     * @return
     */
    public static InputStream getInputStream(String location, ClassLoader cl) {
        InputStream result = null;
        if (cl == null) {
            cl = PropertiesUtil.class.getClassLoader();
        }
        if (cl != null){
            result = cl.getResourceAsStream(location);
        }
        if (result == null){
            result = ClassLoader.getSystemResourceAsStream(location);
        }
        return result;
    }
}
