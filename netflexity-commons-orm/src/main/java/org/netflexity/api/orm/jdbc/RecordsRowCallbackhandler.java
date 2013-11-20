package org.netflexity.api.orm.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.util.jdbc.DefaultRowCallbackHandler;

/**
 * @author Max Fedorov
 *
 * Default implementation of row callback handler that will return a
 * list of metadata records.
 *
 */
public class RecordsRowCallbackhandler extends DefaultRowCallbackHandler {

    private RecordMetadata metadata;

    /**
     * @param metadata
     */
    public RecordsRowCallbackhandler(RecordMetadata metadata) {
	this.metadata = metadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.RowCallbackHandler#processRows(java.sql.ResultSet)
     */
    @Override
    public List processRows(ResultSet rs) throws SQLException {
	List results = new ArrayList();
	while (rs.next()) {
	    // Create and populate record.
	    Object record = metadata.getNewRecord();
	    metadata.populate(record, rs);
	    results.add(record);
	}
	return results;
    }
}
