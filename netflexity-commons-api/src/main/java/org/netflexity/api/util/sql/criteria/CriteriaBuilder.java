package org.netflexity.api.util.sql.criteria;

/**
 * @author Max Fedorov
 * 
 * Holder for factory methods for criteria objects.
 */
public class CriteriaBuilder {

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field has the given a value. <BR>
     *         If caseInsensitive is true, the comparisonValue must be a string (of course!)
     */
    public static Criteria getEqualityCriteria(Column column, Object comparisonValue) {
        return new SimpleCriteria(ComparisonTypeEnum.EQUALS, column, comparisonValue);
    }

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>not</EM> of the given
     *         value. <BR>
     *         If caseInsensitive is true, the comparisonValue must be a string (of course!)
     */
    public static Criteria getInequalityCriteria(Column column, Object comparisonValue) {
        return new SimpleCriteria(ComparisonTypeEnum.NOT_EQUALS, column, comparisonValue);
    }

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>less than</EM> the
     *         given value.
     */
    public static Criteria getLessThanCriteria(Column column, Object comparisonValue) {
        return new SimpleCriteria(ComparisonTypeEnum.LESS_THAN, column, comparisonValue);
    }

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than</EM> the
     *         given value.
     */
    public static Criteria getGreaterThanCriteria(Column column, Object comparisonValue) {
        return new SimpleCriteria(ComparisonTypeEnum.GREATER_THAN, column, comparisonValue);
    }

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>less than or equal to</EM>
     *         the given value.
     */
    public static Criteria getLessThanEqualsCriteria(Column column, Object comparisonValue) {
        return new SimpleCriteria(ComparisonTypeEnum.LESS_THAN_EQUALS, column, comparisonValue);
    }

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    public static Criteria getGreaterThanEqualsCriteria(Column column, Object comparisonValue) {
        return new SimpleCriteria(ComparisonTypeEnum.GREATER_THAN_EQUALS, column, comparisonValue);
    }

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The string to compare with
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    public static Criteria getLikeCriteria(Column column, String comparisonValue) {
        return new SimpleCriteria(ComparisonTypeEnum.LIKE, column, comparisonValue);
    }

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The string to compare with
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    public static Criteria getNotLikeCriteria(Column column, String comparisonValue) {
        return new SimpleCriteria(ComparisonTypeEnum.NOT_LIKE, column, comparisonValue);
    }

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValues[]
     *            The values that will compose IN clause
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    public static Criteria getInCriteria(Column column, Object[] comparisonValues) {
        return new SimpleCriteria(ComparisonTypeEnum.IN, column, comparisonValues);
    }
    
    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValues[]
     *            The values that will compose NOT IN clause
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    public static Criteria getNotInCriteria(Column column, Object[] comparisonValues) {
        return new SimpleCriteria(ComparisonTypeEnum.NOT_IN, column, comparisonValues);
    }
    
    /**
     * @param lhs
     *            left-hand side criteria
     * @param rhs
     *            right-hand side criteria
     * @return A criteria that represents the intersection of the two conditions. A logical AND
     */
    public static Criteria getIntersection(Criteria lhs, Criteria rhs) {
        return new CompoundCriteria(CompoundComparisonTypeEnum.AND, lhs, rhs);
    }

    /**
     * @param lhs
     *            left-hand side criteria
     * @param rhs
     *            right-hand side criteria
     * @return A criteria that represents the union of the two conditions. A logical OR
     */
    public static Criteria getUnion(Criteria lhs, Criteria rhs) {
        return new CompoundCriteria(CompoundComparisonTypeEnum.OR, lhs, rhs);
    }
    
    /**
     * @param srcColumn
     * @param destColumn
     * @return
     */
    public static Criteria getJoinCriteria(Column srcColumn, Column destColumn) {
        return new JoinCriteria(srcColumn, destColumn);
    }
}