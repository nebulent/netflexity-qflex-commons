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

import java.util.Collection;

/**
 * @author MAX
 *
 */
public interface DatasourceMetadata extends Metadata{
    
    /**
     * @return Returns the queries.
     */
    Collection getQueries();
    
    /**
     * @param name
     * @param query The query to set.
     */
    void addQuery(String name, String value);
    
    /**
     * @param name
     * @return
     */
    String getQueryByName(String name);
    
    /**
     * @return Returns the records.
     */
    Collection geRecordMetadata();
    
    /**
     * @param records The records to set.
     */
    void addRecordMetadata(RecordMetadata recordMeta);
    
    /**
     * @param recordClass - class of the record
     * @return
     */
    RecordMetadata getRecordMetadataByClass(Class recordClass);
}
