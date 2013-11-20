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
package org.netflexity.api.orm.commands;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;

import org.netflexity.api.orm.FieldMetadata;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.util.jdbc.AbstractStatementCallbackHandler;

/**
 * @author MAX
 *
 */
public class DeleteStatementCallbackHandler extends AbstractStatementCallbackHandler {

    private Object record;
    private RecordMetadata recordMetadata;
    
    /**
     * @param recordMetadata
     * @param record
     */
    public DeleteStatementCallbackHandler(RecordMetadata recordMetadata, Object record) {
        this.record = record;
        this.recordMetadata = recordMetadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementCallbackHandler#processStatement(java.sql.Statement)
     */
    @Override
    public void processStatement(Statement st) throws SQLException {
        PreparedStatement pst = (PreparedStatement)st;
        
        int ind = 1;
        Collection primaryKeyMeta = recordMetadata.getPrimaryKeyMetadata();
        for (Iterator iter = primaryKeyMeta.iterator(); iter.hasNext(); ind++) {
            FieldMetadata fmd = (FieldMetadata) iter.next();
            Object val = fmd.getProperty(record);
            pst.setObject(ind, val);
            
            // Collect parameters.
            parameters.add(val);
        }
    }
}
