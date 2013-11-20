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
package org.netflexity.api.orm.commands.aggregate;

import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.util.sql.criteria.CountColumn;
import org.netflexity.api.util.sql.criteria.Criteria;
import org.netflexity.api.util.sql.criteria.Table;
import org.netflexity.api.util.sql.criteria.WildCardColumn;

/**
 * @author MAX
 *
 */
public class CountColumnCommand extends AbstractAggregateCommand {

    /**
     * @param recordMetadata
     * @param columnName
     * @param criteria
     */
    public CountColumnCommand(RecordMetadata recordMetadata, String columnName, Criteria criteria) {
        super(recordMetadata, new CountColumn(new Table(recordMetadata.getName()), columnName), criteria);
    }

    /**
     * @param recordMetadata
     * @param columnName
     */
    public CountColumnCommand(RecordMetadata recordMetadata, String columnName) {
        this(recordMetadata, columnName, null);
    }
    
    /**
     * @param recordMetadata
     * @param criteria
     */
    public CountColumnCommand(RecordMetadata recordMetadata, Criteria criteria) {
        super(recordMetadata, new CountColumn(new WildCardColumn(new Table(recordMetadata.getName()))), criteria);
    }
    
    /**
     * @param recordMetadata
     */
    public CountColumnCommand(RecordMetadata recordMetadata) {
        this(recordMetadata, (Criteria)null);
    }
}
