package org.netflexity.api.orm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.netflexity.api.util.StringConstants;
import org.xml.sax.SAXException;

/**
 * @author MFedorov
 *
 * Serves as a container for all outlined datasources.
 */
public class DatasourceMetadataFactory {

    private static Logger logger = Logger.getLogger(DatasourceMetadataFactory.class.getPackage().getName());
    // String constant used in Properties file.
    public static final String DEFAULT_MAPPING_FILE_PATH = "dorm.xml";
    // Apache Digester 1.6 parsing rules.
    public static final String DEFAULT_DIGESTER_RULES_FILE_PATH = StringUtils.replace(DatasourceMetadataFactory.class.getPackage().getName(), StringConstants.DOT, StringConstants.FORWARD_SLASH) + "/orm-rules.xml";
    // Singleton instance of the factory class.
    private static DatasourceMetadataFactory instance = new DatasourceMetadataFactory();
    // Map, containing existing datasource metadata.
    private static Map datasourceMetadata = new Hashtable();

    /**
     * Default Constructor
     */
    private DatasourceMetadataFactory() {
    }

    /**
     * @return
     */
    public synchronized static DatasourceMetadataFactory getInstance() {
	return getInstance(DEFAULT_DIGESTER_RULES_FILE_PATH, DEFAULT_MAPPING_FILE_PATH, DatasourceMetadataFactory.class.getClassLoader());
    }

    /**
     * @param metaFilePath
     * @return
     */
    public synchronized static DatasourceMetadataFactory getInstance(String metaFilePath) {
	return getInstance(DEFAULT_DIGESTER_RULES_FILE_PATH, metaFilePath, DatasourceMetadataFactory.class.getClassLoader());
    }

    /**
     * @param classLoader
     * @return
     */
    public synchronized static DatasourceMetadataFactory getInstance(ClassLoader classLoader) {
	return getInstance(DEFAULT_DIGESTER_RULES_FILE_PATH, DEFAULT_MAPPING_FILE_PATH, classLoader);
    }

    /**
     * @param rulesFilePath
     * @param metaFilePath
     * @param classLoader
     * @return
     */
    public synchronized static DatasourceMetadataFactory getInstance(String rulesFilePath, String metaFilePath, ClassLoader classLoader) {
	if (datasourceMetadata.isEmpty()) {
	    if (classLoader == null) {
		classLoader = DatasourceMetadataFactory.class.getClassLoader();
	    }
	    InputStream metaStream = null;
	    URL rulesUrl = null;
	    if (classLoader != null) {
		metaStream = classLoader.getResourceAsStream(metaFilePath);
		rulesUrl = classLoader.getResource(rulesFilePath);
	    }
	    if (metaStream == null) {
		metaStream = ClassLoader.getSystemResourceAsStream(metaFilePath);
	    }
	    if (rulesUrl == null) {
		rulesUrl = ClassLoader.getSystemResource(rulesFilePath);
	    }

	    // Load metadata objects.
	    Digester digester = DigesterLoader.createDigester(rulesUrl);
	    try {
		List datasourceMetaList = (List) digester.parse(metaStream);
		for (Iterator iter = datasourceMetaList.iterator(); iter.hasNext();) {
		    DatasourceMetadata dsmd = (DatasourceMetadata) iter.next();
		    datasourceMetadata.put(dsmd.getName(), dsmd);
		    logger.info("Loaded datasource '" + dsmd.getName() + "'.");
		}
	    } catch (IOException e) {
		throw new MetadataException(e.getMessage(), e);
	    } catch (SAXException e) {
		throw new MetadataException(e.getMessage(), e);
	    }
	}
	return instance;
    }

    /**
     * @param url
     * @return
     */
    public synchronized static DatasourceMetadataFactory getInstance(URL url) {
	if (datasourceMetadata == null) {
	    ClassLoader classLoader = DatasourceMetadataFactory.class.getClassLoader();
	    URL rulesUrl = null;
	    if (classLoader != null) {
		rulesUrl = classLoader.getResource(DEFAULT_DIGESTER_RULES_FILE_PATH);
	    }
	    if (rulesUrl == null) {
		rulesUrl = ClassLoader.getSystemResource(DEFAULT_DIGESTER_RULES_FILE_PATH);
	    }

	    // Load metadata objects.
	    Digester digester = DigesterLoader.createDigester(rulesUrl);
	    try {
		List datasourceMetaList = (List) digester.parse(url.openStream());
		for (Iterator iter = datasourceMetaList.iterator(); iter.hasNext();) {
		    DatasourceMetadata dsmd = (DatasourceMetadata) iter.next();
		    datasourceMetadata.put(dsmd.getName(), dsmd);
		    logger.info("Loaded datasource '" + dsmd.getName() + "'.");
		}
	    } catch (IOException e) {
		throw new MetadataException(e.getMessage(), e);
	    } catch (SAXException e) {
		throw new MetadataException(e.getMessage(), e);
	    }
	}
	return instance;
    }

    /**
     * @return
     */
    public Iterator getDatasourceMetadata() {
	return Collections.unmodifiableCollection(datasourceMetadata.values()).iterator();
    }

    /**
     * @param name
     * @return
     */
    public DatasourceMetadata getDatasourceMetadataByName(String name) {
	if (StringUtils.isBlank(name)) {
	    throw new MetadataException("'NAME' must be specified to retrieve a DATASOURCE.");
	}
	if (!datasourceMetadata.containsKey(name)) {
	    throw new MetadataException("DATASOURCE name '" + name + "' does not exist");
	}
	return (DatasourceMetadata) datasourceMetadata.get(name);
    }
}
