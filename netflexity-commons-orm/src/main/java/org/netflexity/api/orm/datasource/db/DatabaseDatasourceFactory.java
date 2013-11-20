package org.netflexity.api.orm.datasource.db;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.datasource.Datasource;
import org.netflexity.api.orm.datasource.DatasourceFactory;
import org.netflexity.api.util.Assertion;

/**
 * @author Max Fedorov
 *
 * This factory will be used on the server side only within 
 * the CommandExecutor context.
 * 
 */
public class DatabaseDatasourceFactory implements DatasourceFactory {

    // Singleton instance of the factory class.
    private static DatasourceFactory instance = new DatabaseDatasourceFactory();

    /* Map, containing instances of concrete datasource implementations. 
    * Assume no more that 2 datasources for most cases.
    */
    private Map<String, Datasource> datasources = Collections.synchronizedMap(new HashMap<String, Datasource>(2));

    /**
     * Private Singleton Constructor
     */
    private DatabaseDatasourceFactory() {
    }

    /**
     * @return
     */
    public static DatasourceFactory getInstance() {
        return instance;
    }

    /* (non-Javadoc)
     * @see org.netflexity.dorm.IDatasourceFactory#getDatasource(org.netflexity.dorm.DatasourceMetadata)
     */
    public Datasource getDatasource(DatasourceMetadata datasourceMetadata) {
        String name = datasourceMetadata.getName();
        synchronized (datasources) {
            Datasource ds = datasources.get(name);
            if (ds == null) {
                Class dsClass = datasourceMetadata.getJavaClass();
                try {
                    ds = (Datasource) ConstructorUtils.invokeConstructor(dsClass, new Object[] { datasourceMetadata });
                }
                catch (Throwable e) {
                    Assertion.asert(false, "Failed to create instance of Datasource " + dsClass);
                }

                // Preserve this datasource.
                datasources.put(name, ds);
            }
           return ds;
        }
    }
}
