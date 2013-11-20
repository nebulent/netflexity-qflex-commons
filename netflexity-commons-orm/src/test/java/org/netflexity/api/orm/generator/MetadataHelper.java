package org.netflexity.api.orm.generator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.netflexity.api.orm.FieldMetadata;
import org.netflexity.api.orm.FieldMetadataImpl;
import org.netflexity.api.orm.MetadataException;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.RecordMetadataImpl;
import org.netflexity.api.util.StringConstants;

/**
 * @author Max Fedorov
 *
 * Class that retrieves all the database metadata.
 */
public class MetadataHelper {
    
    public static final String COLUMN_SIZE = "COLUMN_SIZE";
    public static final String ANY = "%";
    public static final String TABLE = "TABLE";
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String PKTABLE_NAME = "PKTABLE_NAME";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
    public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
    public static final String DATA_TYPE = "DATA_TYPE";
    public static final String TYPE_NAME = "TYPE_NAME";
    public static final String IS_NULLABLE = "IS_NULLABLE";
    public static final String YES = "YES";
    public static final String AND = "AND";
    
    private Connection _conn;
    private String _catalog;
    private String _schema;
    
    /**
     * @param conn
     * @param catalog
     * @param schema
     */
    public MetadataHelper(Connection conn, String catalog, String schema) {
        _conn = conn;
        _catalog = catalog;
        _schema = schema;
    }

    /**
     * @return a list of obtained metadata for each table in the schema.
     */
    public List getMetadata() {
        List tableList = new ArrayList();
        ResultSet tables = null;
        try {
            // Obtain database metadata.
            DatabaseMetaData databaseMetaData = _conn.getMetaData();
            
            // Get all tables by catalog and schema.
            tables = databaseMetaData.getTables(getCatalog(), getSchema(), ANY, new String[]{TABLE});
            while (tables.next()) {
                String tableName = tables.getString(TABLE_NAME);
                RecordMetadata table = new RecordMetadataImpl();
                table.setName(tableName);
                
                //log("Info: processing table :" + tableName);
                
                // COLUMNS.
                processColumns(databaseMetaData, table);

                // PRIMARY KEYS.
                boolean hasPk = processPrimaryKeys(databaseMetaData, table);

                // IMPORTED KEYS.
                processForeignKeys(databaseMetaData, table);

                // Check if table defines a primary key.
                if (!hasPk) {
                    log("Warning: table :" + tableName + " doesn't define any primary key.");
                }

                // Add table to a list.
                tableList.add(table);
            }
            return tableList;
        } 
        catch (SQLException e) {
            throw new MetadataException(e);
        } 
        finally {
            try {
                if(tables != null)tables.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @return
     */
    public String getCatalog() {
        return _catalog;
    }

    /**
     * @return
     */
    public String getSchema() {
        return _schema;
    }

    /**
     * @param databaseMetaData
     * @param table
     * @throws SQLException
     */
    private void processForeignKeys(DatabaseMetaData databaseMetaData, RecordMetadata table) throws SQLException {
        ResultSet importedKeys = databaseMetaData.getImportedKeys(_catalog, _schema, table.getName());
        try {
            while (importedKeys.next()) {
                final String name = importedKeys.getString(FKCOLUMN_NAME);
                FieldMetadata column = table.getFieldMetadataByName(name);
                if(column != null){
                    column.setForeignKey(StringConstants.YES);
                    column.addProperty(FieldMetadataImpl.TARGET_DATASOURCE, _catalog);
                    column.addProperty(FieldMetadataImpl.TARGET_FIELD, importedKeys.getString(PKCOLUMN_NAME));
                    column.addProperty(FieldMetadataImpl.TARGET_RECORD, importedKeys.getString(PKTABLE_NAME));
                }
            }
        } 
        finally{
            if(importedKeys != null) importedKeys.close();
        }
    }

    /**
     * @param databaseMetaData
     * @param table
     * @return true if a table contains a pk
     * @throws SQLException
     */
    private boolean processPrimaryKeys(DatabaseMetaData databaseMetaData, RecordMetadata table) throws SQLException {
        boolean hasPK = false;
        ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(getCatalog(), getSchema(), table.getName());
        try {
            while (primaryKeys.next()) {
                final String name = primaryKeys.getString(COLUMN_NAME);
                FieldMetadata column = table.getFieldMetadataByName(name);
                if(column != null){
                    column.setPrimaryKey(StringConstants.YES);
                    hasPK = true;
                }
            }
        }
        finally {
            if(primaryKeys != null) primaryKeys.close();
        }
        return hasPK;
    }

    /**
     * @param databaseMetaData
     * @param table
     * @throws SQLException
     */
    private void processColumns(DatabaseMetaData databaseMetaData, RecordMetadata table) throws SQLException {
        ResultSet columns = databaseMetaData.getColumns(getCatalog(), getSchema(), table.getName(), ANY);
        try {
            while (columns.next()) {
                String columnName = columns.getString(COLUMN_NAME);
                int dataType = columns.getInt(DATA_TYPE);
                // String dataTypeName = columns.getString(TYPE_NAME);
                boolean nullable = YES.equals(columns.getString(IS_NULLABLE));
                
                // Create field metadata object based on retrieved column values.
                FieldMetadata column = new FieldMetadataImpl();
                column.setName(columnName);
                column.setJdbcType(String.valueOf(dataType));
                column.setRequired(nullable ? StringConstants.NO : StringConstants.YES);
                column.setLength(columns.getString(COLUMN_SIZE));
                column.setRecordMetadata(table);

                // Add column to table.
                table.addFieldMetadata(column);
            }
        } 
        finally {
            if(columns != null)columns.close();
        }
    }

    /**
     * @param msg
     */
    protected void log (String msg) {
        System.out.println(msg);
    }
}
