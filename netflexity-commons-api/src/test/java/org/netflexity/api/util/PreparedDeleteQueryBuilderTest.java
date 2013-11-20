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

import junit.framework.TestCase;

import org.netflexity.api.util.sql.criteria.Column;
import org.netflexity.api.util.sql.criteria.Criteria;
import org.netflexity.api.util.sql.criteria.CriteriaBuilder;
import org.netflexity.api.util.sql.criteria.PreparedDeleteQueryBuilder;
import org.netflexity.api.util.sql.criteria.Query;
import org.netflexity.api.util.sql.criteria.Table;

public class PreparedDeleteQueryBuilderTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'org.netflexity.api.util.sql.criteria.PreparedDeleteQueryBuilder.getQuery()'
     */
    public void testGetQuery() {
        Table table = new Table("authors");
        Column AUTH_age = new Column(table, "age");
        List columns = new ArrayList();
        columns.add(new Column(table, "author_id", true));
        Query query = new PreparedDeleteQueryBuilder(table, columns);
        Criteria gtCriteria = CriteriaBuilder.getGreaterThanEqualsCriteria(AUTH_age, new Integer(50));
        query.addCriteria(gtCriteria);
        System.out.println(query.getQuery());
        
        // Combine delete and criteria.
        query = new PreparedDeleteQueryBuilder(table);
        Criteria inCriteria = CriteriaBuilder.getInCriteria(AUTH_age, new Integer[]{new Integer(50), new Integer(51)});
        query.addCriteria(inCriteria);
        System.out.println(query.getQuery());
        
        query = new PreparedDeleteQueryBuilder(table);
        query.addCriteria(gtCriteria);
        System.out.println(query.getQuery());
    }
}
