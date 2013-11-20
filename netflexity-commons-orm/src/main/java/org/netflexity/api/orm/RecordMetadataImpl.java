package org.netflexity.api.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.validation.BeanValidationService;
import org.netflexity.api.util.validation.BeanValidationServiceFactory;

/**
 * @author Max Fedorov
 *
 * Contains all the objects and attributes that belong to &lt;RECORD&gt;
 * XML element.
 */
public class RecordMetadataImpl extends AbstractMetadata implements RecordMetadata {

    private Map<String, FieldMetadata> fieldMetadataByName = new HashMap<String, FieldMetadata>();
    private List fieldMetadata = new ArrayList();
    private List primaryKeys = new ArrayList(2);
    private List foreignKeys = new ArrayList(2);
    private DatasourceMetadata datasourceMetadata;
    // Should record be validated by Apache Commons Validator?
    private boolean validating;

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#isValidating()
     */
    @Override
    public boolean isValidating() {
	return validating;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#setValidating(java.lang.String)
     */
    @Override
    public void setValidating(String validating) {
	this.validating = StringConstants.YES.equals(validating);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#getDatasourceMetadata()
     */
    @Override
    public DatasourceMetadata getDatasourceMetadata() {
	return datasourceMetadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#setDatasourceMetadata(org.netflexity.api.orm.DatasourceMetadata)
     */
    @Override
    public void setDatasourceMetadata(DatasourceMetadata datasourceMetadata) {
	this.datasourceMetadata = datasourceMetadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#addFieldMetadata(org.netflexity.api.orm.FieldMetadata)
     */
    @Override
    public void addFieldMetadata(FieldMetadata fieldMeta) {
	fieldMeta.setRecordMetadata(this);
	this.fieldMetadata.add(fieldMeta);
	this.fieldMetadataByName.put(fieldMeta.getName(), fieldMeta);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#getFieldMetadataByName(java.lang.String)
     */
    @Override
    public FieldMetadata getFieldMetadataByName(String name) {
	if (StringUtils.isNotBlank(name)) {
	    return fieldMetadataByName.get(name);
	}
	return null;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#getFieldMetadata()
     */
    @Override
    public Collection getFieldMetadata() {
	return Collections.unmodifiableCollection(fieldMetadata);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#getForeignKeyMetadata()
     */
    @Override
    public Collection getForeignKeyMetadata() {
	synchronized (foreignKeys) {
	    if (foreignKeys.isEmpty()) {
		for (Iterator iter = fieldMetadata.iterator(); iter.hasNext();) {
		    FieldMetadata fmd = (FieldMetadata) iter.next();
		    if (fmd.isForeignKey()) {
			foreignKeys.add(fmd);
		    }
		}
	    }
	    return Collections.unmodifiableCollection(foreignKeys);
	}
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#getPrimaryKeyMetadata()
     */
    @Override
    public Collection getPrimaryKeyMetadata() {
	synchronized (primaryKeys) {
	    if (primaryKeys.isEmpty()) {
		for (Iterator iter = fieldMetadata.iterator(); iter.hasNext();) {
		    FieldMetadata fmd = (FieldMetadata) iter.next();
		    if (fmd.isPrimaryKey()) {
			primaryKeys.add(fmd);
		    }
		}
	    }
	    return Collections.unmodifiableCollection(primaryKeys);
	}
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#validate(java.lang.Object)
     */
    @Override
    public final void validate(Object record) throws RecordValidationException {
	if (isValidating()) {
	    // Perform validation.
	    BeanValidationService service = BeanValidationServiceFactory.createValidationService();
	    List errors = service.validate(record, record.getClass().getClassLoader());
	    if (!errors.isEmpty()) {
		RecordValidationException exception = new RecordValidationException();
		for (Iterator iter = errors.iterator(); iter.hasNext();) {
		    String err = (String) iter.next();
		    exception.addError(err);
		}
		throw exception;
	    }
	}
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#populate(java.lang.Object, java.sql.ResultSet)
     */
    @Override
    public void populate(Object record, ResultSet rs) throws SQLException {
	for (Iterator iter = getFieldMetadata().iterator(); iter.hasNext();) {
	    FieldMetadata fieldMeta = (FieldMetadata) iter.next();
	    Object value = DefaultFieldMapper.DEFAULT_FIELD_MAPPER.getValueFromResultSet(rs, fieldMeta.getJdbcType(), fieldMeta.getName());
	    fieldMeta.setProperty(record, value);
	}
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.RecordMetadata#getNewRecord()
     */
    @Override
    public Object getNewRecord() {
	try {
	    return ConstructorUtils.invokeConstructor(getJavaClass(), new Object[]{});
	} catch (Throwable e) {
	    throw new MetadataException("Failed to create instance of RECORD " + getClassName());
	}
    }
}
