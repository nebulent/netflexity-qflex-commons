package org.netflexity.api.orm.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.util.jdbc.RowCallbackHandler;

/**
 * @author Max Fedorov
 *
 * Default implementation of row callback handler that will return a
 * single metadata record.
 *
 */
public class RecordRowCallbackHandler implements RowCallbackHandler {

    private RecordMetadata metadata;
    private Object record;

    /**
     * @param metadata
     */
    public RecordRowCallbackHandler(RecordMetadata metadata) {
	this.metadata = metadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.RowCallbackHandler#processRows(java.sql.ResultSet)
     */
    @Override
    public List processRows(ResultSet rs) throws SQLException {
	List rows = Collections.EMPTY_LIST;
	if (rs.next()) {
	    // Create and populate record.
	    record = metadata.getNewRecord();
	    metadata.populate(record, rs);

	    // Add to result.
	    rows = new ArrayList(1);
	    rows.add(record);
	}
	return rows;
    }
}
