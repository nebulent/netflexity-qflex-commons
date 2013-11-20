package org.netflexity.api.orm.datasource.db;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.command.TransactionResourceException;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.MetadataException;
import org.netflexity.api.orm.datasource.AbstractDatasource;

/**
 * @author Max Fedorov
 *
 * A class that is essentially a facade in front of an external relational
 * database. This is just one of various possible ways to implement this.
 */
public class JdbcDatasource extends AbstractDatasource {

    private static Logger logger = Logger.getLogger(JdbcDatasource.class.getPackage().getName());
    public static final String JDBC_DRIVER_CLASS = "JDBC_DRIVER_CLASS";
    public static final String JDBC_URL = "JDBC_URL";
    public static final String JDBC_TEST_SQL = "JDBC_TEST_SQL";
    public static final String DBCP_URL = "DBCP_URL";
    public static final String CONNECTION_POOL_NAME = "CONNECTION_POOL_NAME";
    public static final String CONNECTION_AUTO_COMMIT = "CONNECTION_AUTO_COMMIT";
    public static final String CONNECTION_READONLY = "CONNECTION_READONLY";
    public static final String MAX_ACTIVE_CONNECTIONS = "MAX_ACTIVE_CONNECTIONS";
    public static final String WHEN_EXHAUSTED_ACTION = "WHEN_EXHAUSTED_ACTION";
    public static final String MAX_WAIT = "MAX_WAIT";
    public static final String MAX_IDLE = "MAX_IDLE";
    public static final String TEST_ON_BORROW = "TEST_ON_BORROW";
    public static final String TEST_ON_RETURN = "TEST_ON_RETURN";
    public static final String TIME_BETWEEN_EVICTION_RUNS = "TIME_BETWEEN_EVICTION_RUNS";
    public static final String NUMBER_OF_TESTS_PER_EVICTION = "NUMBER_OF_TESTS_PER_EVICTION";
    public static final String MIN_EVICTABLE_IDLE = "MIN_EVICTABLE_IDLE";
    public static final String TEST_WHILE_IDLE = "TEST_WHILE_IDLE";
    // Connection pool properties.
    private int maxActive;
    private byte whenExhaustedAction;
    private long maxWait;
    private int maxIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private long timeBetweenEvictionRunsMillis;
    private int numTestsPerEvictionRun;
    private long minEvictableIdleTimeMillis;
    private boolean testWhileIdle;
    private boolean autoCommit;
    private boolean readOnly;
    private String poolName, dbcpUrl, driverClassName, jdbcURL, testSQL;
    private PoolingDriver poolingDriver;
    private static String TRUE = Boolean.TRUE.toString();
    private static String FALSE = Boolean.FALSE.toString();

    /**
     * @param descriptor
     */
    public JdbcDatasource(DatasourceMetadata descriptor) {
        super(descriptor);
    }

    /**
     * @see org.netflexity.dorm.datasource.Datasource#init()
     */
    @Override
    public void init() {

        // Set database properties.
        dbcpUrl = descriptor.getProperty(DBCP_URL, null);
        driverClassName = descriptor.getProperty(JDBC_DRIVER_CLASS, null);
        jdbcURL = descriptor.getProperty(JDBC_URL, null);
        testSQL = descriptor.getProperty(JDBC_TEST_SQL, null);
        poolName = descriptor.getProperty(CONNECTION_POOL_NAME, "test");
        autoCommit = testWhileIdle = Boolean.parseBoolean(descriptor.getProperty(CONNECTION_AUTO_COMMIT, FALSE));
        readOnly = testWhileIdle = Boolean.parseBoolean(descriptor.getProperty(CONNECTION_READONLY, FALSE));

        // Obtain connection pool settings.
        maxActive = Integer.parseInt(descriptor.getProperty(MAX_ACTIVE_CONNECTIONS, "5"));
        whenExhaustedAction = Byte.parseByte(descriptor.getProperty(WHEN_EXHAUSTED_ACTION, "1"));
        maxWait = Long.parseLong(descriptor.getProperty(MAX_WAIT, "-1"));
        maxIdle = Integer.parseInt(descriptor.getProperty(MAX_IDLE, "0"));
        testOnBorrow = Boolean.parseBoolean(descriptor.getProperty(TEST_ON_BORROW, TRUE));
        testOnReturn = Boolean.parseBoolean(descriptor.getProperty(TEST_ON_RETURN, TRUE));
        timeBetweenEvictionRunsMillis = Long.parseLong(descriptor.getProperty(TIME_BETWEEN_EVICTION_RUNS, "10000"));
        numTestsPerEvictionRun = Integer.parseInt(descriptor.getProperty(NUMBER_OF_TESTS_PER_EVICTION, "2"));
        minEvictableIdleTimeMillis = Long.parseLong(descriptor.getProperty(MIN_EVICTABLE_IDLE, "5000"));
        testWhileIdle = Boolean.parseBoolean(descriptor.getProperty(TEST_WHILE_IDLE, TRUE));

        // Initialize specified JDBC driver.
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new MetadataException(e.getMessage());
        }

        // Create connection pool.
        DriverManagerConnectionFactory cf = new DriverManagerConnectionFactory(jdbcURL, null);
        GenericObjectPool pool = new GenericObjectPool(null, maxActive, whenExhaustedAction, maxWait, maxIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle);
        GenericKeyedObjectPoolFactory opf = new GenericKeyedObjectPoolFactory(null, maxActive, whenExhaustedAction, maxWait, maxIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle);
        new PoolableConnectionFactory(cf, pool, opf, testSQL, readOnly, autoCommit);
//	PoolingDriver poolingDriver = new PoolingDriver();
        poolingDriver = new PoolingDriver();
        poolingDriver.registerPool(poolName, pool);
        PoolingDriver.setAccessToUnderlyingConnectionAllowed(true);
        try {
            DriverManager.registerDriver(poolingDriver);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new MetadataException(e.getMessage());
        }
    }

    /**
     * @see org.netflexity.dorm.datasource.Datasource#close()
     */
    @Override
    public void close() {
        try {
            poolingDriver.closePool(poolName);
        } catch (Exception e) {
            logger.error("JdbcDatasource.close() failed:", e);
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.datasource.Datasource#getResource()
     */
    @Override
    public TransactionResource getResource() throws TransactionResourceException {
        try {
            return new DatabaseTransactionResource(DriverManager.getConnection(dbcpUrl));
        } catch (Exception e) {
            logger.error("JdbcDatasource.getResource() failed:", e);
            throw new TransactionResourceException(e);
        }
    }
}
