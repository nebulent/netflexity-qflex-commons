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
import org.netflexity.api.util.sql.criteria.Criteria;
import org.netflexity.api.util.sql.criteria.MinimumColumn;
import org.netflexity.api.util.sql.criteria.Table;

/**
 * @author MAX
 *
 */
public class MinColumnCommand extends AbstractAggregateCommand {

    /**
     * @param recordMetadata
     * @param columnName
     * @param criteria
     */
    public MinColumnCommand(RecordMetadata recordMetadata, String columnName, Criteria criteria) {
        super(recordMetadata, new MinimumColumn(new Table(recordMetadata.getName()), columnName), criteria);
    }
    
    /**
     * @param recordMetadata
     * @param columnName
     */
    public MinColumnCommand(RecordMetadata recordMetadata, String columnName) {
        super(recordMetadata, new MinimumColumn(new Table(recordMetadata.getName()), columnName), null);
    }
}
