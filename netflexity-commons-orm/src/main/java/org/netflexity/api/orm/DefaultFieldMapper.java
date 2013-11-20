package org.netflexity.api.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


/**
 * @author Max Fedorov
 * 
 * Default implementation of FieldMapper interface to retrieve ResultSet
 * values.
 */
public class DefaultFieldMapper implements FieldMapper {

    public static FieldMapper DEFAULT_FIELD_MAPPER = new DefaultFieldMapper();
    
    /**
     * Private constructor to disobey creation of more Default Mapper instances.
     */
    private DefaultFieldMapper(){
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.netflexity.dorm.meta.IFieldMapper#getValueFromResultSet(java.sql.ResultSet, int, int)
     */
    @Override
    public Object getValueFromResultSet(ResultSet rs, int jdbcType, int fieldIndex) throws SQLException {
        Object result = null;
        switch (jdbcType) {
	        case Types.BIT: {
	            boolean boolVal = rs.getBoolean(fieldIndex);
	            result = (rs.wasNull() ? null : new Boolean(boolVal));
	            break;
	        }
	        case Types.TINYINT: {
	            byte byteVal = rs.getByte(fieldIndex);
	            result = (rs.wasNull() ? null : new Byte(byteVal));
	            break;
	        }
	        case Types.SMALLINT: {
	            short shortVal = rs.getShort(fieldIndex);
	            result = (rs.wasNull() ? null : new Short(shortVal));
	            break;
	        }
	        case Types.INTEGER: {
	            int intVal = rs.getInt(fieldIndex);
	            result = (rs.wasNull() ? null : new Integer(intVal));
	            break;
	        }
	        case Types.BIGINT: {
	            long longVal = rs.getLong(fieldIndex);
	            result = (rs.wasNull() ? null : new Long(longVal));
	            break;
	        }
	        case Types.DOUBLE:
	        case Types.FLOAT: {
	            double doubleVal = rs.getDouble(fieldIndex);
	            result = (rs.wasNull() ? null : new Double(doubleVal));
	            break;
	        }
	        case Types.REAL: {
	            float floatVal = rs.getFloat(fieldIndex);
	            result = (rs.wasNull() ? null : new Float(floatVal));
	            break;
	        }
	        case Types.NUMERIC: {
	            result = rs.getBigDecimal(fieldIndex);
	            break;
	        }
	        case Types.DECIMAL: {
	            result = rs.getBigDecimal(fieldIndex);
	            break;
	        }
	        case Types.CHAR: {
	            result = rs.getString(fieldIndex);
	            break;
	        }
	        case Types.VARCHAR: {
	            result = rs.getString(fieldIndex);
	            break;
	        }
	        case Types.LONGVARCHAR: {
	            result = rs.getString(fieldIndex);
	            break;
	        }
	        case Types.DATE: {
	            result = rs.getDate(fieldIndex);
	            break;
	        }
	        case Types.TIME: {
	            result = rs.getTime(fieldIndex);
	            break;
	        }
	        case Types.TIMESTAMP: {
	            result = rs.getTimestamp(fieldIndex);
	            break;
	        }
	        case Types.BINARY: {
	            result = rs.getBytes(fieldIndex);
	            break;
	        }
	        case Types.VARBINARY: {
	            result = rs.getBytes(fieldIndex);
	            break;
	        }
	        case Types.LONGVARBINARY: {
	            result = rs.getBytes(fieldIndex);
	            break;
	        }
	        case Types.CLOB: {
	            java.sql.Clob aClob = rs.getClob(fieldIndex);
	            result = (rs.wasNull() ? null : aClob.getSubString(1L, (int) aClob.length()));
	            break;
	        }
	        case Types.BLOB: {
	            java.sql.Blob aBlob = rs.getBlob(fieldIndex);
	            result = (rs.wasNull() ? null : aBlob.getBytes(1L, (int) aBlob.length()));
	            break;
	        }
	        case Types.STRUCT: {
	            java.sql.Struct aStruct = (java.sql.Struct) rs.getObject(fieldIndex);
	            result = (rs.wasNull() ? null : aStruct);
	            break;
	        }
	        default: {
	            throw new SQLException("The type " + jdbcType + " for field("
	                    + fieldIndex + ") is an invalid type of java.sql.Types.");
	        }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.netflexity.dorm.meta.IFieldMapper#getValueFromResultSet(java.sql.ResultSet, int, java.lang.String)
     */
    @Override
    public Object getValueFromResultSet(ResultSet rs, int jdbcType, String fieldName) throws SQLException {
        Object result = null;
        switch (jdbcType) {
	        case Types.BIT: {
	            boolean boolVal = rs.getBoolean(fieldName);
	            result = rs.wasNull() ? null : Boolean.valueOf(boolVal);
	            break;
	        }
	        case Types.TINYINT: {
	            byte byteVal = rs.getByte(fieldName);
	            result = (rs.wasNull() ? null : new Byte(byteVal));
	            break;
	        }
	        case Types.SMALLINT: {
	            short shortVal = rs.getShort(fieldName);
	            result = (rs.wasNull() ? null : new Short(shortVal));
	            break;
	        }
	        case Types.INTEGER: {
	            int intVal = rs.getInt(fieldName);
	            result = (rs.wasNull() ? null : new Integer(intVal));
	            break;
	        }
	        case Types.BIGINT: {
	            long longVal = rs.getLong(fieldName);
	            result = (rs.wasNull() ? null : new Long(longVal));
	            break;
	        }
	        case Types.DOUBLE:
	        case Types.FLOAT: {
	            double doubleVal = rs.getDouble(fieldName);
	            result = (rs.wasNull() ? null : new Double(doubleVal));
	            break;
	        }
	        case Types.REAL: {
	            float floatVal = rs.getFloat(fieldName);
	            result = (rs.wasNull() ? null : new Float(floatVal));
	            break;
	        }
	        case Types.NUMERIC: {
	            result = rs.getBigDecimal(fieldName);
	            break;
	        }
	        case Types.DECIMAL: {
	            result = rs.getBigDecimal(fieldName);
	            break;
	        }
	        case Types.CHAR: {
	            result = rs.getString(fieldName);
	            break;
	        }
	        case Types.VARCHAR: {
	            result = rs.getString(fieldName);
	            break;
	        }
	        case Types.LONGVARCHAR: {
	            result = rs.getString(fieldName);
	            break;
	        }
	        case Types.DATE: {
	            result = rs.getDate(fieldName);
	            break;
	        }
	        case Types.TIME: {
	            result = rs.getTime(fieldName);
	            break;
	        }
	        case Types.TIMESTAMP: {
	            result = rs.getTimestamp(fieldName);
	            break;
	        }
	        case Types.BINARY: {
	            result = rs.getBytes(fieldName);
	            break;
	        }
	        case Types.VARBINARY: {
	            result = rs.getBytes(fieldName);
	            break;
	        }
	        case Types.LONGVARBINARY: {
	            result = rs.getBytes(fieldName);
	            break;
	        }
	        case Types.CLOB: {
	            java.sql.Clob aClob = rs.getClob(fieldName);
	            result = (rs.wasNull() ? null : aClob.getSubString(1L, (int) aClob.length()));
	            break;
	        }
	        case Types.BLOB: {
	            java.sql.Blob aBlob = rs.getBlob(fieldName);
	            result = (rs.wasNull() ? null : aBlob.getBytes(1L, (int) aBlob.length()));
	            break;
	        }
	        case Types.STRUCT: {
	            java.sql.Struct aStruct = (java.sql.Struct) rs.getObject(fieldName);
	            result = (rs.wasNull() ? null : aStruct);
	            break;
	        }
	        default: {
	            throw new SQLException("The type " + jdbcType + " for field '"
	                    + fieldName + "' is an invalid type of java.sql.Types.");
	        }
        }
        return result;
    }

}