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
package org.netflexity.api.orm.criteria;

import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.util.sql.criteria.Criteria;

public interface CriteriaQueryBuilder {

    /**
     * @return Returns the recordMetadata - table metadata 
     * of the table we do select against.
     */
    RecordMetadata getRecordMetadata();

    /**
     * @param criterion
     * @return
     */
    CriteriaQueryBuilder addCriteria(Criteria criterion);

    /**
     * @param groupByColumn
     * @return
     */
    CriteriaQueryBuilder addGroupByColumn(String groupByColumn);

    /**
     * @param groupByColumns[]
     * @return
     */
    CriteriaQueryBuilder addGroupByColumns(String groupByColumns[]);

    /**
     * @param recordMetadata - another joined table.
     * @param groupByColumn
     * @return
     */
    CriteriaQueryBuilder addGroupByColumn(RecordMetadata recordMetadata, String groupByColumn);

    /**
     * @param havingColumn
     * @return
     */
    CriteriaQueryBuilder addHavingColumn(String havingColumn);

    /**
     * @param havingColumns[]
     * @return
     */
    CriteriaQueryBuilder addHavingColumns(String havingColumns[]);

    /**
     * @param recordMetadata - another joined table.
     * @param havingColumn
     * @return
     */
    CriteriaQueryBuilder addHavingColumn(RecordMetadata recordMetadata, String havingColumn);

    /**
     * @param orderByColumn
     * @return
     */
    CriteriaQueryBuilder addOrderByColumn(String orderByColumn);

    /**
     * @param orderByColumns[]
     * @return
     */
    CriteriaQueryBuilder addOrderByColumns(String orderByColumns[]);

    /**
     * @param recordMetadata - another joined table.
     * @param orderByColumn
     * @return
     */
    CriteriaQueryBuilder addOrderByColumn(RecordMetadata recordMetadata, String orderByColumn);

    /**
     * @param orderByDesc
     */
    void setOrderByDesc(boolean orderByDesc);

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field has the given a value. <BR>
     *         If caseInsensitive is true, the comparisonValue must be a string (of course!)
     */
    Criteria getEqualityCriteria(String column, Object comparisonValue);

    /**
     * @param recordMetadata
     *            Metadata of the joined table.
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field has the given a value. <BR>
     *         If caseInsensitive is true, the comparisonValue must be a string (of course!)
     */
    Criteria getEqualityCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue);

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>not</EM> of the given
     *         value. <BR>
     *         If caseInsensitive is true, the comparisonValue must be a string (of course!)
     */
    Criteria getInequalityCriteria(String column, Object comparisonValue);

    /**
     * @param recordMetadata
     *            Metadata of the joined table.
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>not</EM> of the given
     *         value. <BR>
     *         If caseInsensitive is true, the comparisonValue must be a string (of course!)
     */
    Criteria getInequalityCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue);

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>less than</EM> the
     *         given value.
     */
    Criteria getLessThanCriteria(String column, Object comparisonValue);

    /**
     * @param recordMetadata
     *            Metadata of the joined table.
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>less than</EM> the
     *         given value.
     */
    Criteria getLessThanCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue);

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than</EM> the
     *         given value.
     */
    Criteria getGreaterThanCriteria(String column, Object comparisonValue);

    /**
     * @param recordMetadata
     *            Metadata of the joined table.
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than</EM> the
     *         given value.
     */
    Criteria getGreaterThanCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue);

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>less than or equal to</EM>
     *         the given value.
     */
    Criteria getLessThanEqualsCriteria(String column, Object comparisonValue);

    /**
     * @param recordMetadata
     *            Metadata of the joined table.
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>less than or equal to</EM>
     *         the given value.
     */
    Criteria getLessThanEqualsCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue);

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    Criteria getGreaterThanEqualsCriteria(String column, Object comparisonValue);

    /**
     * @param recordMetadata
     *            Metadata of the joined table.
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The value to compare that field's value with.
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    Criteria getGreaterThanEqualsCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue);

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The string to compare with
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    Criteria getLikeCriteria(String column, String comparisonValue);

    /**
     * @param recordMetadata
     *            Metadata of the joined table.
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The string to compare with
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    Criteria getLikeCriteria(RecordMetadata recordMetadata, String column, String comparisonValue);

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The string to compare with
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    Criteria getNotLikeCriteria(String column, String comparisonValue);

    /**
     * @param recordMetadata
     *            Metadata of the joined table.
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValue
     *            The string to compare with
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    Criteria getNotLikeCriteria(RecordMetadata recordMetadata, String column, String comparisonValue);

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValues[]
     *            The values that will compose IN clause
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    Criteria getInCriteria(String column, Object[] comparisonValues);

    /**
     * @param recordMetadata
     *            Metadata of the joined table.
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValues[]
     *            The values that will compose IN clause
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    Criteria getInCriteria(RecordMetadata recordMetadata, String column, Object[] comparisonValues);

    /**
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValues[]
     *            The values that will compose NOT IN clause
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    Criteria getNotInCriteria(String column, Object[] comparisonValues);

    /**
     * @param recordMetadata
     *            Metadata of the joined table.
     * @param column
     *            The field of the record we are filtering on.
     * @param comparisonValues[]
     *            The values that will compose NOT IN clause
     * @return A criteria that represents all records of a given type whose given field is <EM>greater than or equal to</EM>
     *         the given value.
     */
    Criteria getNotInCriteria(RecordMetadata recordMetadata, String column, Object[] comparisonValues);

    /**
     * @param lhs
     *            left-hand side criteria
     * @param rhs
     *            right-hand side criteria
     * @return A criteria that represents the intersection of the two conditions. A logical AND
     */
    Criteria getIntersection(Criteria lhs, Criteria rhs);

    /**
     * @param lhs
     *            left-hand side criteria
     * @param rhs
     *            right-hand side criteria
     * @return A criteria that represents the union of the two conditions. A logical OR
     */
    Criteria getUnion(Criteria lhs, Criteria rhs);

    /**
     * @param srcColumn
     * @param destColumn
     * @return
     */
    Criteria getJoinCriteria(String column, RecordMetadata destRecordMetadata, String destColumn);

    /**
     * @param srcRecordMetadata
     * @param column
     * @param destRecordMetadata
     * @param destColumn
     * @return
     */
    Criteria getJoinCriteria(RecordMetadata srcRecordMetadata, String column, RecordMetadata destRecordMetadata, String destColumn);

    /**
     * @return sql
     */
    String getQuery();
}