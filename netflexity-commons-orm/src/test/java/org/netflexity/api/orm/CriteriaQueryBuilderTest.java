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

import junit.framework.TestCase;

import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilder;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilderImpl;

public class CriteriaQueryBuilderTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'org.netflexity.api.orm.criteria.CriteriaQueryBuilderImpl.getQuery()'
     */
    public void testGetQuery() {
        RecordMetadata table = null;
        CriteriaQueryBuilder criteriaBuilder = new CriteriaQueryBuilderImpl(table);
        System.out.println(criteriaBuilder.getQuery());
    }
}
