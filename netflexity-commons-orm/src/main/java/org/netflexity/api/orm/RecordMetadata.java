/*
 *  2005 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public interface RecordMetadata extends Metadata{

    /**
     * @return
     */
    boolean isValidating();

    /**
     * @param validating
     */
    void setValidating(String validating);

    /**
     * @return Returns the datasourceMetadata.
     */
    DatasourceMetadata getDatasourceMetadata();

    /**
     * @param datasourceMetadata The datasourceMetadata to set.
     */
    void setDatasourceMetadata(DatasourceMetadata datasourceMetadata);

    /**
     * @param fieldMetadata The fieldMetadata to set.
     */
    void addFieldMetadata(FieldMetadata fieldMeta);

    /**
     * @param name
     * @return
     */
    FieldMetadata getFieldMetadataByName(String name);

    /**
     * @return Returns the fieldMetadata.
     */
    Collection getFieldMetadata();

    /**
     * @return Returns all foreignKey metadata.
     */
    Collection getForeignKeyMetadata();

    /**
     * @return Returns all primaryKey metadata.
     */
    Collection getPrimaryKeyMetadata();
    
    /**
     * Check if the record's data is valid.
     * 
     * @param record
     * @throws RecordValidationException - The appropriate error message is encoded in the exception.
     */
    void validate(Object record) throws RecordValidationException;
    
    /**
     * Populate record with data from resultset.
     * @param record
     * @param rs
     * @throws SQLException
     */
    void populate(Object record, ResultSet rs) throws SQLException;
    
    /**
     * @return new instance of a java class that represents
     * a database record.
     */
    Object getNewRecord();
}