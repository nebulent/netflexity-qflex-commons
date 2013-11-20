package org.netflexity.api.orm;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.netflexity.api.orm.util.SqlUtil;
import org.netflexity.api.util.AssertionFailedException;
import org.netflexity.api.util.StringConstants;

/**
 * @author Max Fedorov
 *
 * Contains all the objects and attributes that belong to &lt;FIELD&gt;
 * XML element.
 */
public class FieldMetadataImpl extends AbstractMetadata implements FieldMetadata {

    private static Logger logger = Logger.getLogger(FieldMetadataImpl.class.getPackage().getName());
    public static final int LOWER_CASE = 1;
    public static final int UPPER_CASE = 2;
    public static final int CAPITALIZE = 4;
    public static final int TRIM = 8;
    public static final int TRUNCATE_SILENTLY = 16;
    public static final String LOWER_CASE_STR = "LOWER_CASE";
    public static final String UPPER_CASE_STR = "UPPER_CASE";
    public static final String CAPITALIZE_STR = "CAPITALIZE";
    public static final String TRIM_STR = "TRIM";
    public static final String TRUNCATE_SILENTLY_STR = "TRUNCATE_SILENTLY";
    public static final String TARGET_DATASOURCE = "TARGET-DATASOURCE";
    public static final String TARGET_RECORD = "TARGET-RECORD";
    public static final String TARGET_FIELD = "TARGET-FIELD";
    
    private int jdbcType;
    private Class jdbcTypeClass;
    private boolean required;
    private boolean foreignKey;
    private boolean primaryKey;
    private int length;
    private int normalization; // Bit mask, to determine normalization rules.
    private RecordMetadata recordMetadata;

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#isForeignKey()
     */
    @Override
    public boolean isForeignKey() {
	return foreignKey;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#setForeignKey(java.lang.String)
     */
    @Override
    public void setForeignKey(String foreignKey) {
	this.foreignKey = StringConstants.YES.equals(foreignKey);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#getJdbcType()
     */
    @Override
    public int getJdbcType() {
	return jdbcType;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#setJdbcType(java.lang.String)
     */
    @Override
    public void setJdbcType(String jdbcType) {
	this.jdbcType = new Integer(jdbcType).intValue();
	this.jdbcTypeClass = SqlUtil.getJavaTypeNameByJdbcType(this.jdbcType);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#isPrimaryKey()
     */
    @Override
    public boolean isPrimaryKey() {
	return primaryKey;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#setPrimaryKey(java.lang.String)
     */
    @Override
    public void setPrimaryKey(String primaryKey) {
	this.primaryKey = StringConstants.YES.equals(primaryKey);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#isRequired()
     */
    @Override
    public boolean isRequired() {
	return required;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#setRequired(java.lang.String)
     */
    @Override
    public void setRequired(String required) {
	this.required = StringConstants.YES.equals(required);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#getLength()
     */
    @Override
    public int getLength() {
	return length;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#setLength(java.lang.String)
     */
    @Override
    public void setLength(String length) {
	if (StringUtils.isNotEmpty(length)) {
	    this.length = Integer.parseInt(length);
	}
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#getJdbcTypeClass()
     */
    @Override
    public Class getJdbcTypeClass() {
	return jdbcTypeClass;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#getNormalization()
     */
    @Override
    public int getNormalization() {
	return normalization;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#addNormalizationRule(java.lang.String)
     */
    @Override
    public void addNormalizationRule(String rule) {
	if (StringUtils.isBlank(rule)) {
	    throw new MetadataException("'RULE' is a required attribute of <NORMALIZE>, which must be supplied for " + this.getClass().getName());
	}

	// Set normalization mask.
	if (LOWER_CASE_STR.equals(rule)) {
	    normalization |= LOWER_CASE;
	} else if (UPPER_CASE_STR.equals(rule)) {
	    normalization |= UPPER_CASE;
	} else if (CAPITALIZE_STR.equals(rule)) {
	    normalization |= CAPITALIZE;
	} else if (TRIM_STR.equals(rule)) {
	    normalization |= TRIM;
	} else if (TRUNCATE_SILENTLY_STR.equals(rule)) {
	    normalization |= TRUNCATE_SILENTLY;
	}
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#normalize(java.lang.String)
     */
    @Override
    public String normalize(String value) {
	int nrmlztn = getNormalization();
	if ((nrmlztn & LOWER_CASE) != 0) {
	    value = value.toLowerCase();
	}
	if ((nrmlztn & UPPER_CASE) != 0) {
	    value = value.toUpperCase();
	}
	if ((nrmlztn & CAPITALIZE) != 0) {
	    value = StringUtils.capitalize(value);
	}
	if ((nrmlztn & TRIM) != 0) {
	    value = value.trim();
	}
	if ((nrmlztn & TRUNCATE_SILENTLY) != 0) {
	    if (value.length() > getLength()) {
		value = value.substring(0, getLength());
	    }
	}
	return value;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#getRecordMetadata()
     */
    @Override
    public RecordMetadata getRecordMetadata() {
	return recordMetadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#setRecordMetadata(org.netflexity.api.orm.RecordMetadata)
     */
    @Override
    public void setRecordMetadata(RecordMetadata recordMetadata) {
	this.recordMetadata = recordMetadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#getProperty(java.lang.Object)
     */
    @Override
    public Object getProperty(Object record) {
	try {
	    return PropertyUtils.getProperty(record, getName().toLowerCase());
	} catch (IllegalAccessException e) {
	    logger.error(e.getMessage(), e);
	    throw new AssertionFailedException(e.getMessage(), e);
	} catch (InvocationTargetException e) {
	    logger.error(e.getMessage(), e);
	    throw new AssertionFailedException(e.getMessage(), e);
	} catch (NoSuchMethodException e) {
	    logger.error(e.getMessage(), e);
	    throw new AssertionFailedException(e.getMessage(), e);
	}
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.FieldMetadata#setProperty(java.lang.Object, java.lang.Object)
     */
    @Override
    public void setProperty(Object record, Object value) {
	try {
	    PropertyUtils.setProperty(record, getName().toLowerCase(), value);
	} catch (IllegalAccessException e) {
	    logger.error(e.getMessage(), e);
	    throw new AssertionFailedException(e.getMessage(), e);
	} catch (InvocationTargetException e) {
	    logger.error(e.getMessage(), e);
	    throw new AssertionFailedException(e.getMessage(), e);
	} catch (NoSuchMethodException e) {
	    logger.error(e.getMessage(), e);
	    throw new AssertionFailedException(e.getMessage(), e);
	}
    }
}
