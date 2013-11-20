package org.netflexity.api.util.sql.criteria;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.StringConstants;

/**
 * @author Max Fedorov
 * 
 * A CompoundCriteria that represents the AND, OR condition
 * on two other criteria.
 */
public class CompoundCriteria extends AbstractCriteria {
    
    protected final Criteria lhs, rhs;
    protected final CompoundComparisonTypeEnum compoundComparisonType;
    
    /**
     * @param operation
     * @param lhs
     * @param rhs
     */
    protected CompoundCriteria(CompoundComparisonTypeEnum compoundComparisonType, Criteria lhs, Criteria rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.compoundComparisonType = compoundComparisonType;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Criteria#getSql()
     */
    public String getSql() {
        String conjunction = (CompoundComparisonTypeEnum.OR.equals(compoundComparisonType)) 
            ? (StringConstants.RP + CompoundComparisonTypeEnum.OR.getName() + StringConstants.LP) 
                    : (StringConstants.RP + CompoundComparisonTypeEnum.AND.getName() + StringConstants.LP);
        String criteria1 = lhs.getSql();
        String criteria2 = rhs.getSql();
        boolean hasCriteria1 = StringUtils.isNotBlank(criteria1);
        boolean hasCriteria2 = StringUtils.isNotBlank(criteria2);
        
        if (!hasCriteria1 && !hasCriteria2){
            return StringUtils.EMPTY;
        }
        else if (!hasCriteria1){
            return criteria2;
        }
        else if (!hasCriteria2){
            return criteria1;
        }
        else if (criteria1.equals(criteria2)){
            return criteria1;
        }
        
        return StringConstants.LP + StringConstants.LP + criteria1 + conjunction + criteria2 + StringConstants.RP + StringConstants.RP;
    }
}