/*
 *  2006 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.util.sql.criteria;

/**
 * @author MAX
 * 
 * Represents logical OR.
 */
public class OrCriteria extends CompoundCriteria {

    /**
     * @param lhs
     * @param rhs
     */
    public OrCriteria(Criteria lhs, Criteria rhs) {
        super(CompoundComparisonTypeEnum.OR, lhs, rhs);
    }
}
