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

import junit.framework.TestCase;

import org.netflexity.api.util.sql.criteria.Column;
import org.netflexity.api.util.sql.criteria.Criteria;
import org.netflexity.api.util.sql.criteria.CriteriaBuilder;
import org.netflexity.api.util.sql.criteria.SelectQuery;
import org.netflexity.api.util.sql.criteria.SelectQueryBuilder;
import org.netflexity.api.util.sql.criteria.SumColumn;
import org.netflexity.api.util.sql.criteria.Table;
import org.netflexity.api.util.sql.criteria.WildCardColumn;

public class SelectQueryBuilderTest extends TestCase {

    Table publisher = new Table("publisher");
    Table author = new Table("author");
    Column PUB_publisher_id = new Column(publisher, "publisher_id");
    Column AUTH_publisher_id = new Column(author, "publisher_id");
    Column PUB_copies = new Column(publisher, "copies");
    
    
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'org.netflexity.api.util.sql.criteria.AbstractQueryBuilder.addColumn(Column)'
     */
    public void testAddColumn() {
        SelectQuery builder = new SelectQueryBuilder();
        builder.addColumn(new WildCardColumn(publisher));
        System.out.println(builder.getQuery());
        
        builder = new SelectQueryBuilder();
        builder.addColumn(new Column(publisher, "publisher_id"));
        builder.addColumn(new Column(publisher, "publisher_name"));
        System.out.println(builder.getQuery());
    }

    /*
     * Test method for 'org.netflexity.api.util.sql.criteria.AbstractQueryBuilder.addCriteria(Criteria)'
     */
    public void testAddCriteria() {
        SelectQuery builder = new SelectQueryBuilder();
        builder.addColumn(new WildCardColumn(publisher));
        builder.addCriteria(CriteriaBuilder.getEqualityCriteria(new Column(publisher, "publisher_id"), new Long(12233)));
        System.out.println(builder.getQuery());
    }

    /*
     * Test method for 'org.netflexity.api.util.sql.criteria.AbstractQueryBuilder.addGroupByColumn(Column)'
     */
    public void testAddGroupByColumn() {

    }

    /*
     * Test method for 'org.netflexity.api.util.sql.criteria.AbstractQueryBuilder.addHavingColumn(Column)'
     */
    public void testAddHavingColumn() {

    }

    /*
     * Test method for 'org.netflexity.api.util.sql.criteria.AbstractQueryBuilder.addOrderByColumn(Column)'
     */
    public void testAddOrderByColumn() {

    }

    /*
     * Test method for 'org.netflexity.api.util.sql.criteria.AbstractQueryBuilder.addJoin(JoinCriteria)'
     */
    public void testAddJoin() {
        SelectQuery builder = new SelectQueryBuilder();
        //builder.addColumn(new WildCardColumn(publisher));
        builder.addColumn(new SumColumn(PUB_copies));
        
        // Add criteria.
        Criteria equalCriteria = CriteriaBuilder.getEqualityCriteria(PUB_publisher_id, new Long(12233));
        Criteria gtCriteria = CriteriaBuilder.getGreaterThanEqualsCriteria(PUB_copies, new Integer(10));
        Criteria or = CriteriaBuilder.getUnion(equalCriteria, gtCriteria);
        builder.addCriteria(or);
        
        // Add join.
        builder.addCriteria(CriteriaBuilder.getJoinCriteria(PUB_publisher_id, AUTH_publisher_id));
        
        // Add order by.
        builder.addOrderByColumn(PUB_publisher_id);
        
        System.out.println(builder.getQuery());
    }
}
