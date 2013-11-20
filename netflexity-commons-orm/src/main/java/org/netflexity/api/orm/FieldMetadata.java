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

public interface FieldMetadata extends Metadata{

    /**
     * @return Returns the foreignKey.
     */
    boolean isForeignKey();

    /**
     * @param foreignKey The foreignKey to set.
     */
    void setForeignKey(String foreignKey);

    /**
     * @return Returns the jdbcType.
     */
    int getJdbcType();

    /**
     * @param jdbcType The jdbcType to set.
     */
    void setJdbcType(String jdbcType);

    /**
     * @return Returns the primaryKey.
     */
    boolean isPrimaryKey();

    /**
     * @param primaryKey The primaryKey to set.
     */
    void setPrimaryKey(String primaryKey);

    /**
     * @return Returns the required.
     */
    boolean isRequired();

    /**
     * @param required The required to set.
     */
    void setRequired(String required);

    /**
     * @return Returns the length.
     */
    int getLength();

    /**
     * @param length The length to set.
     */
    void setLength(String length);

    /**
     * @return
     */
    Class getJdbcTypeClass();

    /**
     * @return Returns normalization mask.
     */
    int getNormalization();

    /**
     * @param rule - The rule to set.
     */
    void addNormalizationRule(String rule);

    /**
     * @param value
     * @return
     */
    String normalize(String value);

    /**
     * @return Returns the recordMetadata.
     */
    RecordMetadata getRecordMetadata();

    /**
     * @param recordMetadata The recordMetadata to set.
     */
    void setRecordMetadata(RecordMetadata recordMetadata);

    /**
     * @param record
     * @return
     */
    Object getProperty(Object record);
    
    /**
     * @param record
     * @param value
     */
    void setProperty(Object record, Object value);
}