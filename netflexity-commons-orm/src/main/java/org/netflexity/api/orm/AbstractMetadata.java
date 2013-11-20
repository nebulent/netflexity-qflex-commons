package org.netflexity.api.orm;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.ReflectUtil;

/**
 * @author Max Fedorov
 *
 * Properties and attributes utilized by all types of ORM metadata
 * classes.
 */
public class AbstractMetadata implements Metadata {

    private String name;
    private String className;
    private Class javaClass;
    private Properties properties = new Properties();
    private ClassLoader classLoader = AbstractMetadata.class.getClassLoader();

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.Metadata#getClassName()
     */
    @Override
    public String getClassName() {
	return className;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.Metadata#setClassName(java.lang.String)
     */
    @Override
    public void setClassName(String className) {
	if (StringUtils.isBlank(className)) {
	    throw new MetadataException("'CLASS' attribute must be supplied for " + this.getClass().getName());
	}

	this.className = className;
	try {
	    this.javaClass = ReflectUtil.findClass(this.className, this.classLoader);
	} catch (ClassNotFoundException e) {
	    throw new MetadataException("Failed to find class '" + this.className + "' for " + this.getClass().getName());
	}
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.Metadata#getName()
     */
    @Override
    public String getName() {
	return name;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.Metadata#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
	if (StringUtils.isBlank(name)) {
	    throw new MetadataException("'NAME' is a required unique attribute, which must be supplied for " + this.getClass().getName());
	}
	this.name = name;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.Metadata#getProperties()
     */
    @Override
    public Collection getProperties() {
	return Collections.unmodifiableCollection(properties.values());
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.Metadata#getProperty(java.lang.String)
     */
    @Override
    public String getProperty(String key) {
	return properties.getProperty(key);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.Metadata#getProperty(java.lang.String, java.lang.String)
     */
    @Override
    public String getProperty(String key, String defval) {
	return properties.getProperty(key, defval);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.Metadata#addProperty(java.lang.String, java.lang.String)
     */
    @Override
    public void addProperty(String key, String value) {
	if (StringUtils.isEmpty(key)) {
	    throw new MetadataException("'KEY' is a required attribute of <PROPERTY>, which must be supplied for " + this.getClass().getName());
	}
	this.properties.setProperty(key, value);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.Metadata#getJavaClass()
     */
    @Override
    public Class getJavaClass() {
	return javaClass;
    }
}
