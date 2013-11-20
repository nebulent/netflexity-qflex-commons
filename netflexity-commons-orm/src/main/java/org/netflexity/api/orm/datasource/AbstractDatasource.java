package org.netflexity.api.orm.datasource;

import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.util.Assertion;

/**
 * @author Max Fedorov
 *
 * Base implementation of a Datasource.
 */
public abstract class AbstractDatasource implements Datasource {
	
    protected DatasourceMetadata descriptor;
    
    /**
     * Mandatory Constructor
     */
    public AbstractDatasource(DatasourceMetadata descriptor) {
        Assertion.asert(descriptor != null, "Datasource Metadata cannot be null!");
        this.descriptor = descriptor;
        init();
    }

    /**
     * @see org.netflexity.dorm.datasource.Datasource#getMetadata()
     */
    @Override
    public DatasourceMetadata getMetadata() {
        return descriptor;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return getMetadata().getName().hashCode();
    }
}
