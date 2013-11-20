package org.netflexity.api.orm.datasource;

import java.io.Serializable;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.command.TransactionResourceException;
import org.netflexity.api.orm.DatasourceMetadata;

/**
 * @author Max Fedorov
 *
 * Behavior for classes that will implement real 
 * datasource connection provider such as JDBC.
 */
public interface Datasource extends Serializable {
    /**
     * Initialize datasource
     */
    void init();
    
    /**
     * Finalize datasource.
     */
    void close();
    
    /**
     * @return
     */
    DatasourceMetadata getMetadata();
    
    /**
     * Returns transactional resource.
     * 
     * @return
     */
    TransactionResource getResource() throws TransactionResourceException;
}
