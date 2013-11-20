package org.netflexity.api.orm;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

/**
 * @author Max Fedorov
 *
 * Contains all the objects and attributes that belong to 
 * &lt;DATASORCE&gt; XML element.
 */
public class DatasourceMetadataImpl extends AbstractMetadata implements DatasourceMetadata{
	
	private Map<String, RecordMetadata> recordMetadata = new HashMap<String, RecordMetadata>(10);
	private Properties queries = new Properties();
	
	/* (non-Javadoc)
	 * @see org.netflexity.api.orm.DatasourceMetadata#getQueries()
	 */
	@Override
	public Collection getQueries() {
		return Collections.unmodifiableCollection(queries.values());
	}
    
	/* (non-Javadoc)
	 * @see org.netflexity.api.orm.DatasourceMetadata#addQuery(java.lang.String, java.lang.String)
	 */
	@Override
	public void addQuery(String name, String value) {
		if(StringUtils.isBlank(name)){
			throw new MetadataException("'NAME' is a required attribute of <QUERY>, which must be supplied for " + this.getClass().getName());
		}
		this.queries.setProperty(name, value);
	}
    
	/* (non-Javadoc)
	 * @see org.netflexity.api.orm.DatasourceMetadata#getQueryByName(java.lang.String)
	 */
	@Override
	public String getQueryByName(String name) {
	    if(StringUtils.isNotBlank(name)){
	        return (String)queries.get(name);
	    }
	    return null;
	}
    
	/* (non-Javadoc)
	 * @see org.netflexity.api.orm.DatasourceMetadata#geRecordMetadata()
	 */
	@Override
	public Collection<RecordMetadata> geRecordMetadata(){
		return Collections.unmodifiableCollection(recordMetadata.values());
	}
    
	/* (non-Javadoc)
	 * @see org.netflexity.api.orm.DatasourceMetadata#addRecordMetadata(org.netflexity.api.orm.RecordMetadata)
	 */
	@Override
	public void addRecordMetadata(RecordMetadata recordMeta) {
	    recordMeta.setDatasourceMetadata(this);
		this.recordMetadata.put(recordMeta.getClassName(), recordMeta);
	}
    
	/* (non-Javadoc)
	 * @see org.netflexity.api.orm.DatasourceMetadata#getRecordMetadataByClass(java.lang.Class)
	 */
	@Override
	public RecordMetadata getRecordMetadataByClass(Class recordClass) {
	    return recordMetadata.get(recordClass.getName());
	}
}
