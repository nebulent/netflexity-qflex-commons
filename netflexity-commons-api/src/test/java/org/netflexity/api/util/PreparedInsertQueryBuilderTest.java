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
package org.netflexity.api.util;

import java.util.ArrayList;
import java.util.List;

import org.netflexity.api.util.sql.criteria.Column;
import org.netflexity.api.util.sql.criteria.PreparedInsertQueryBuilder;
import org.netflexity.api.util.sql.criteria.Query;
import org.netflexity.api.util.sql.criteria.Table;

import junit.framework.TestCase;

public class PreparedInsertQueryBuilderTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'org.netflexity.api.util.sql.criteria.InsertQueryBuilder.getQuery()'
     */
    public void testGetQuery() {
        Table table = new Table("authors");
        List columns = new ArrayList();
        columns.add(new Column(table, "author_id"));
        columns.add(new Column(table, "author_name"));
        columns.add(new Column(table, "copies"));
        Query query = new PreparedInsertQueryBuilder(table, columns);
        System.out.println(query.getQuery());
    }
}
