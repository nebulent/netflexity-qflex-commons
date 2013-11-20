package org.netflexity.api.orm.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.orm.FieldMetadata;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilder;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilderImpl;
import org.netflexity.api.util.sql.SqlConstants;
import org.netflexity.api.util.sql.criteria.Column;
import org.netflexity.api.util.sql.criteria.Criteria;
import org.netflexity.api.util.sql.criteria.PreparedDeleteQueryBuilder;
import org.netflexity.api.util.sql.criteria.PreparedInsertQueryBuilder;
import org.netflexity.api.util.sql.criteria.PreparedUpdateQueryBuilder;
import org.netflexity.api.util.sql.criteria.Query;
import org.netflexity.api.util.sql.criteria.SelectQueryBuilder;
import org.netflexity.api.util.sql.criteria.Table;
import org.netflexity.api.util.sql.criteria.WildCardColumn;

/**
 * @author Max Fedorov
 * A holder for some JDBC/SQL-related convenience routines.
 */
final public class SqlUtil implements SqlConstants{
    
    /**
     * @param recordMetadata
     * @param primaryKeys[]
     * @return CriteriaQueryBuilder
     */
    public static CriteriaQueryBuilder buildSelectByPrimaryKeyQuery(RecordMetadata recordMetadata, Object primaryKeys[]) {
        CriteriaQueryBuilder queryBuilder = new CriteriaQueryBuilderImpl(recordMetadata);
        Collection primaryKeyMetadata = recordMetadata.getPrimaryKeyMetadata();
        int i = 0;
        for (Iterator iter = primaryKeyMetadata.iterator(); iter.hasNext(); i++) {
            FieldMetadata fmd = (FieldMetadata) iter.next();
            Object value = primaryKeys[i];
            queryBuilder.addCriteria(queryBuilder.getEqualityCriteria(fmd.getName(), value));
        }
        return queryBuilder;
    }

    /**
     * @param recordMetadata
     * @return
     */
    public static String buildSelectAllQuery(RecordMetadata recordMetadata) {
        Table table = new Table(recordMetadata.getName());
        Query query = new SelectQueryBuilder(new WildCardColumn(table));
        return query.getQuery();
    }
    
    /**
     * @param recordMetadata
     * @return insert sql
     */
    public static String buildInsertQuery(RecordMetadata recordMetadata) {
        Table table = new Table(recordMetadata.getName());
        Collection fieldMetadata = recordMetadata.getFieldMetadata();
        List<Column> columns = new ArrayList<Column>(fieldMetadata.size());
        for (Iterator iter = fieldMetadata.iterator(); iter.hasNext();) {
            FieldMetadata fmd = (FieldMetadata) iter.next();
            columns.add(new Column(table, fmd.getName()));
        }
        Query query = new PreparedInsertQueryBuilder(table, columns);
        return query.getQuery();
    }

    /**
     * @param recordMetadata
     * @return update sql
     */
    public static String buildUpdateQuery(RecordMetadata recordMetadata) {
        Table table = new Table(recordMetadata.getName());
        Collection fieldMetadata = recordMetadata.getFieldMetadata();
        List columns = new ArrayList(fieldMetadata.size());
        for (Iterator iter = fieldMetadata.iterator(); iter.hasNext();) {
            FieldMetadata fmd = (FieldMetadata) iter.next();
            columns.add(new Column(table, fmd.getName(), fmd.isPrimaryKey()));
        }
        Query query = new PreparedUpdateQueryBuilder(table, columns);
        return query.getQuery();
    }
    
    /**
     * @param recordMetadata
     * @param oldRecord
     * @param newRecord
     * @return update sql
     */
    public static String buildUpdateQuery(RecordMetadata recordMetadata, Object oldRecord, Object newRecord) {
        Table table = new Table(recordMetadata.getName());
        Collection fieldMetadata = recordMetadata.getFieldMetadata();
        List columns = new ArrayList(fieldMetadata.size());
        boolean columnsChanged = false;
        for (Iterator iter = fieldMetadata.iterator(); iter.hasNext();) {
            FieldMetadata fmd = (FieldMetadata) iter.next();
            
            // Ignore primary key(s) during change check cause it is
            // not supposed to change at any time.
            if(!fmd.isPrimaryKey()){
                // See if there was a change.
                Object oldVal = fmd.getProperty(oldRecord);
                Object newVal = fmd.getProperty(newRecord);
                if(isFieldValueChanged(fmd, oldVal, newVal)){
                    columnsChanged = true;
                    columns.add(new Column(table, fmd.getName()));
                }
            }
            else{
                // Set primary key(s) anyway.
                columns.add(new Column(table, fmd.getName(), true));
            }
        }
        
        // If none of the fields changed, return null;
        if(!columnsChanged){
            return null;
        }
        
        Query query = new PreparedUpdateQueryBuilder(table, columns);
        return query.getQuery();
    }
    
    /**
     * See if there was a change.
     * 
     * @param fmd
     * @param oldVal
     * @param newVal
     * @return
     */
    public static boolean isFieldValueChanged(FieldMetadata fmd, Object oldVal, Object newVal){
        if(!fmd.isPrimaryKey()){
            if(oldVal == null && newVal == null){
                return false; // no change.
            }
            else if(oldVal == null){
                if (newVal instanceof String) {
                    String strVal = (String) newVal;
                    if(StringUtils.isNotBlank(strVal)){
                        return true;
                    }
                    else{
                        if(!fmd.isRequired()){
                            return true;
                        }
                    }
                }
                else{
                    return true;
                }
            }
            else if(newVal == null){
                if(!fmd.isRequired()){
                    return true;
                }
            }
            else{ // both are not null.
                if(!newVal.equals(oldVal)){
                    if (newVal instanceof String) {
                        String strVal = (String) newVal;
                        if(StringUtils.isNotBlank(strVal)){
                            return true;
                        }
                        else{
                            if(!fmd.isRequired()){
                                return true;
                            }
                        }
                    }
                    else{
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * @param recordMetadata
     * @return
     */
    public static String buildDeleteQuery(RecordMetadata recordMetadata) {
        Table table = new Table(recordMetadata.getName());
        Collection primaryKeyMetadata = recordMetadata.getPrimaryKeyMetadata();
        List columns = new ArrayList(primaryKeyMetadata.size());
        for (Iterator iter = primaryKeyMetadata.iterator(); iter.hasNext();) {
            FieldMetadata fmd = (FieldMetadata) iter.next();
            columns.add(new Column(table, fmd.getName(), true));
        }
        Query query = new PreparedDeleteQueryBuilder(table, columns);
        return query.getQuery();
    }
    
    /**
     * @param recordMetadata
     * @param criterion
     * @return
     */
    public static String buildDeleteByCriteriaQuery(RecordMetadata recordMetadata, Criteria criterion) {
        Table table = new Table(recordMetadata.getName());
        Query query = new PreparedDeleteQueryBuilder(table);
        query.addCriteria(criterion);
        return query.getQuery();
    }
    
    /**
     * @param jdbcType
     * @return Class
     */
    public static Class getJavaTypeNameByJdbcType(int jdbcType){
        switch (jdbcType) {
	        case Types.BIT: {
	            return Boolean.class;
	        }
	        case Types.TINYINT: {
	        	return Byte.class;
	        }
	        case Types.SMALLINT: {
	        	return Short.class;
	        }
	        case Types.INTEGER: {
	        	return Integer.class;
	        }
	        case Types.BIGINT: {
	        	return Long.class;
	        }
	        case Types.DOUBLE:
	        case Types.FLOAT: {
	        	return Double.class;
	        }
	        case Types.REAL: {
	        	return Float.class;
	        }
	        case Types.NUMERIC: {
	        	return Boolean.class;
	        }
	        case Types.DECIMAL: {
	        	return BigDecimal.class;
	        }
	        case Types.CHAR:
	        case Types.VARCHAR:
	        case Types.LONGVARCHAR: {
	            return String.class;
	        }
	        case Types.DATE: {
	            return Date.class;
	        }
	        case Types.TIME: {
	            return Time.class;
	        }
	        case Types.TIMESTAMP: {
	            return Timestamp.class;
	        }
	        case Types.BINARY:
	        case Types.VARBINARY:
	        case Types.LONGVARBINARY:
	        case Types.CLOB:
	        case Types.BLOB:
	        case Types.STRUCT: {
	        	return Object.class;
	        }
	        default: {
	        	return Object.class;
	        }
        }
    }
    
    /**
     * Finds appropriate SQL type for the given value.
     * Returns Types.OTHER if not found.
     * 
     * @param value
     * @return
     */
    public static int getSqlTypeByValue(Object value) {
        if (value instanceof Integer) {
            return Types.INTEGER;
        }
        else if (value instanceof java.math.BigDecimal) {
            return Types.NUMERIC;
        }
        else if (value instanceof String) {
            return Types.VARCHAR;
        }
        else if (value instanceof Byte) {
            return Types.TINYINT;
        }
        else if (value instanceof Short) {
            return Types.SMALLINT;
        }
        else if (value instanceof Long) {
            return Types.BIGINT;
        }
        else if (value instanceof Float) {
            return Types.REAL;
        }
        else if (value instanceof Double) {
            return Types.DOUBLE;
        }
        else if (value instanceof byte[]) {
            return Types.VARBINARY;
        }
        else if (value instanceof java.sql.Date) {
            return Types.DATE;
        }
        else if (value instanceof java.sql.Time) {
            return Types.TIME;
        }
        else if (value instanceof java.sql.Timestamp) {
            return Types.TIMESTAMP;
        }
        else if (value instanceof Boolean) {
            return Types.BIT;
        }
        else if (value instanceof java.sql.Struct) {
            return Types.STRUCT;
        }
        else {
            return Types.OTHER;
        }
    }
}
