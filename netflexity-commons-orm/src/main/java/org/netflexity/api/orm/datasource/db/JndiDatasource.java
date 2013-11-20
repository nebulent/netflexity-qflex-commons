package org.netflexity.api.orm.datasource.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.command.TransactionResourceException;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.MetadataException;
import org.netflexity.api.orm.datasource.AbstractDatasource;

/**
 * @author Max Fedorov
 *
 * AbstractDatasource that obtains database connections through JNDI.
 */
public class JndiDatasource extends AbstractDatasource {

    private static Logger logger = Logger.getLogger(JndiDatasource.class.getPackage().getName());
    public static final String JNDI_NAME = "JNDI_NAME";
    private String jndiName;
    private DataSource ds;

    /**
     * Constructor for JndiDatasource.
     *
     * @param descriptor
     */
    public JndiDatasource(DatasourceMetadata descriptor) {
        super(descriptor);
        logger.debug("JNDI datasource created");

    }

    /**
     * @see org.netflexity.dorm.datasource.Datasource#close()
     */
    @Override
    public void close() {
        // Internal connection pool deallocation is handled internally.
    }

    /**
     * @see org.netflexity.dorm.datasource.Datasource#getResource()
     */
    @Override
    public TransactionResource getResource() throws TransactionResourceException {
        return new DatabaseTransactionResource(getConnection());
    }

    /**
     * @see org.netflexity.dorm.datasource.Datasource#init()
     */
    @Override
    public void init() {

        logger.debug("Initializing JNDI datasource");

        this.jndiName = descriptor.getProperty(JNDI_NAME);

        ds = getDataSource(jndiName);
    }

    /**
     * Method getDataSource.
     *
     * @param jndiName
     * @return DataSource
     */
    private static DataSource getDataSource(String jndiName) {
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:comp/env");
            return (DataSource) envCtx.lookup(jndiName);
        } catch (NamingException e) {
            logger.error("Failed to find datasource at " + jndiName, e);
            return null;
        }
    }

    /**
     * Method getConnection.
     *
     * @return Connection
     */
    private Connection getConnection() {
        if (ds == null) {
            ds = getDataSource(jndiName);
        }
        if (ds != null) {
            try {
                return ds.getConnection();
            } catch (SQLException e) {
                logger.error("Failed to obtain Connection ", e);
                return null;
            }
        }
        throw new MetadataException("DataSource is not found for " + jndiName);
    }
}
