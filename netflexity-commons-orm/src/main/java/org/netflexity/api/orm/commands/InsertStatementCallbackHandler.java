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
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.orm.FieldMetadata;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.util.jdbc.AbstractStatementCallbackHandler;

/**
 * @author MAX
 *
 */
public class InsertStatementCallbackHandler extends AbstractStatementCallbackHandler {

    private Object record;
    private RecordMetadata recordMetadata;

    /**
     * @param recordMetadata
     * @param record
     */
    public InsertStatementCallbackHandler(RecordMetadata recordMetadata, Object record) {
	this.recordMetadata = recordMetadata;
	this.record = record;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementCallbackHandler#processStatement(java.sql.Statement)
     */
    @Override
    public void processStatement(Statement st) throws SQLException {
	PreparedStatement pst = (PreparedStatement) st;
	int ind = 1;
	for (Iterator iter = recordMetadata.getFieldMetadata().iterator(); iter.hasNext(); ind++) {
	    FieldMetadata fmd = (FieldMetadata) iter.next();
	    Object val = fmd.getProperty(record);
	    if (val instanceof String) {
		String strVal = (String) val;
		if (StringUtils.isBlank(strVal)) {
		    val = null;
		} else {
		    val = fmd.normalize(strVal);
		}
	    }

	    if (val != null) {
		pst.setObject(ind, val);
	    } else {
		pst.setNull(ind, fmd.getJdbcType());
	    }

	    // Collect parameters.
	    parameters.add(val);
	}
    }
}
