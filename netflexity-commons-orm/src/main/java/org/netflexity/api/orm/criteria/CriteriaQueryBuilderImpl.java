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
import org.netflexity.api.util.sql.criteria.Column;
import org.netflexity.api.util.sql.criteria.Criteria;
import org.netflexity.api.util.sql.criteria.CriteriaBuilder;
import org.netflexity.api.util.sql.criteria.SelectQueryBuilder;
import org.netflexity.api.util.sql.criteria.Table;
import org.netflexity.api.util.sql.criteria.WildCardColumn;

/**
 * @author MAX
 * Allows to build (select *) queries from a single table,
 * which could be joined to any number of other tables.
 */
public class CriteriaQueryBuilderImpl implements CriteriaQueryBuilder {

    private final RecordMetadata recordMetadata;
    private final SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
    private final Table table;
    
    /**
     * @param recordMetadata
     */
    public CriteriaQueryBuilderImpl(RecordMetadata recordMetadata) {
        this.recordMetadata = recordMetadata;
        this.table = new Table(recordMetadata.getName());
        this.queryBuilder.addColumn(new WildCardColumn(table));
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getRecordMetadata()
     */
    @Override
    public RecordMetadata getRecordMetadata() {
        return recordMetadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#addCriteria(org.netflexity.api.util.sql.criteria.Criteria)
     */
    @Override
    public CriteriaQueryBuilder addCriteria(Criteria criterion){
        queryBuilder.addCriteria(criterion);
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#addGroupByColumn(java.lang.String)
     */
    @Override
    public CriteriaQueryBuilder addGroupByColumn(String groupByColumn) {
        queryBuilder.addGroupByColumn(new Column(table, groupByColumn));
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#addGroupByColumns(java.lang.String[])
     */
    @Override
    public CriteriaQueryBuilder addGroupByColumns(String groupByColumns[]) {
        for (int i = 0; i < groupByColumns.length; i++) {
            queryBuilder.addGroupByColumn(new Column(table, groupByColumns[i]));
        }
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#addGroupByColumn(org.netflexity.api.orm.RecordMetadata, java.lang.String)
     */
    @Override
    public CriteriaQueryBuilder addGroupByColumn(RecordMetadata recordMetadata, String groupByColumn) {
        queryBuilder.addGroupByColumn(new Column(new Table(recordMetadata.getName()), groupByColumn));
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#addHavingColumn(java.lang.String)
     */
    @Override
    public CriteriaQueryBuilder addHavingColumn(String havingColumn) {
        queryBuilder.addHavingColumn(new Column(table, havingColumn));
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#addHavingColumns(java.lang.String[])
     */
    @Override
    public CriteriaQueryBuilder addHavingColumns(String havingColumns[]) {
        for (int i = 0; i < havingColumns.length; i++) {
            queryBuilder.addHavingColumn(new Column(table, havingColumns[i]));
        }
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#addHavingColumn(org.netflexity.api.orm.RecordMetadata, java.lang.String)
     */
    @Override
    public CriteriaQueryBuilder addHavingColumn(RecordMetadata recordMetadata, String havingColumn) {
        queryBuilder.addHavingColumn(new Column(new Table(recordMetadata.getName()), havingColumn));
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#addOrderByColumn(java.lang.String)
     */
    @Override
    public CriteriaQueryBuilder addOrderByColumn(String orderByColumn) {
        queryBuilder.addOrderByColumn(new Column(table, orderByColumn));
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#addOrderByColumns(java.lang.String[])
     */
    @Override
    public CriteriaQueryBuilder addOrderByColumns(String orderByColumns[]) {
	for(String column : orderByColumns){
            queryBuilder.addOrderByColumn(new Column(table, column));
        }
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#addOrderByColumn(org.netflexity.api.orm.RecordMetadata, java.lang.String)
     */
    @Override
    public CriteriaQueryBuilder addOrderByColumn(RecordMetadata recordMetadata, String orderByColumn) {
        queryBuilder.addOrderByColumn(new Column(new Table(recordMetadata.getName()), orderByColumn));
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#setOrderByDesc(boolean)
     */
    @Override
    public void setOrderByDesc(boolean orderByDesc) {
        queryBuilder.setOrderByDesc(orderByDesc);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getEqualityCriteria(java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getEqualityCriteria(String column, Object comparisonValue) {
        return CriteriaBuilder.getEqualityCriteria(new Column(table, column), comparisonValue);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getEqualityCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getEqualityCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue) {
        return CriteriaBuilder.getEqualityCriteria(new Column(new Table(recordMetadata.getName()), column), comparisonValue);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getInequalityCriteria(java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getInequalityCriteria(String column, Object comparisonValue) {
        return CriteriaBuilder.getInequalityCriteria(new Column(table, column), comparisonValue);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getInequalityCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getInequalityCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue) {
        return CriteriaBuilder.getInequalityCriteria(new Column(new Table(recordMetadata.getName()), column), comparisonValue);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getLessThanCriteria(java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getLessThanCriteria(String column, Object comparisonValue) {
        return CriteriaBuilder.getLessThanCriteria(new Column(table, column), comparisonValue);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getLessThanCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getLessThanCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue) {
        return CriteriaBuilder.getLessThanCriteria(new Column(new Table(recordMetadata.getName()), column), comparisonValue);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getGreaterThanCriteria(java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getGreaterThanCriteria(String column, Object comparisonValue) {
        return CriteriaBuilder.getGreaterThanCriteria(new Column(table, column), comparisonValue);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getGreaterThanCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getGreaterThanCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue) {
        return CriteriaBuilder.getGreaterThanCriteria(new Column(new Table(recordMetadata.getName()), column), comparisonValue);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getLessThanEqualsCriteria(java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getLessThanEqualsCriteria(String column, Object comparisonValue) {
        return CriteriaBuilder.getLessThanEqualsCriteria(new Column(table, column), comparisonValue);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getLessThanEqualsCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getLessThanEqualsCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue) {
        return CriteriaBuilder.getLessThanEqualsCriteria(new Column(new Table(recordMetadata.getName()), column), comparisonValue);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getGreaterThanEqualsCriteria(java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getGreaterThanEqualsCriteria(String column, Object comparisonValue) {
        return CriteriaBuilder.getGreaterThanEqualsCriteria(new Column(table, column), comparisonValue);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getGreaterThanEqualsCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, java.lang.Object)
     */
    @Override
    public Criteria getGreaterThanEqualsCriteria(RecordMetadata recordMetadata, String column, Object comparisonValue) {
        return CriteriaBuilder.getGreaterThanEqualsCriteria(new Column(new Table(recordMetadata.getName()), column), comparisonValue);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getLikeCriteria(java.lang.String, java.lang.String)
     */
    @Override
    public Criteria getLikeCriteria(String column, String comparisonValue) {
        return CriteriaBuilder.getLikeCriteria(new Column(table, column), comparisonValue);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getLikeCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, java.lang.String)
     */
    @Override
    public Criteria getLikeCriteria(RecordMetadata recordMetadata, String column, String comparisonValue) {
        return CriteriaBuilder.getLikeCriteria(new Column(new Table(recordMetadata.getName()), column), comparisonValue);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getNotLikeCriteria(java.lang.String, java.lang.String)
     */
    @Override
    public Criteria getNotLikeCriteria(String column, String comparisonValue) {
        return CriteriaBuilder.getNotLikeCriteria(new Column(table, column), comparisonValue);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getNotLikeCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, java.lang.String)
     */
    @Override
    public Criteria getNotLikeCriteria(RecordMetadata recordMetadata, String column, String comparisonValue) {
        return CriteriaBuilder.getNotLikeCriteria(new Column(new Table(recordMetadata.getName()), column), comparisonValue);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getInCriteria(java.lang.String, java.lang.Object[])
     */
    @Override
    public Criteria getInCriteria(String column, Object[] comparisonValues) {
        return CriteriaBuilder.getInCriteria(new Column(table, column), comparisonValues);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getInCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, java.lang.Object[])
     */
    @Override
    public Criteria getInCriteria(RecordMetadata recordMetadata, String column, Object[] comparisonValues) {
        return CriteriaBuilder.getInCriteria(new Column(new Table(recordMetadata.getName()), column), comparisonValues);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getNotInCriteria(java.lang.String, java.lang.Object[])
     */
    @Override
    public Criteria getNotInCriteria(String column, Object[] comparisonValues) {
        return CriteriaBuilder.getNotInCriteria(new Column(table, column), comparisonValues);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getNotInCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, java.lang.Object[])
     */
    @Override
    public Criteria getNotInCriteria(RecordMetadata recordMetadata, String column, Object[] comparisonValues) {
        return CriteriaBuilder.getNotInCriteria(new Column(new Table(recordMetadata.getName()), column), comparisonValues);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getIntersection(org.netflexity.api.util.sql.criteria.Criteria, org.netflexity.api.util.sql.criteria.Criteria)
     */
    @Override
    public Criteria getIntersection(Criteria lhs, Criteria rhs) {
        return CriteriaBuilder.getIntersection(lhs, rhs);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getUnion(org.netflexity.api.util.sql.criteria.Criteria, org.netflexity.api.util.sql.criteria.Criteria)
     */
    @Override
    public Criteria getUnion(Criteria lhs, Criteria rhs) {
        return CriteriaBuilder.getUnion(lhs, rhs);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.predicate.SelectQuery#getJoinCriteria(java.lang.String, org.netflexity.api.orm.RecordMetadata, java.lang.String)
     */
    @Override
    public Criteria getJoinCriteria(String column, RecordMetadata destRecordMetadata, String destColumn) {
        return CriteriaBuilder.getJoinCriteria(new Column(table, column), new Column(new Table(destRecordMetadata.getName()), destColumn));
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.orm.criteria.CriteriaQueryBuilder#getJoinCriteria(org.netflexity.api.orm.RecordMetadata, java.lang.String, org.netflexity.api.orm.RecordMetadata, java.lang.String)
     */
    @Override
    public Criteria getJoinCriteria(RecordMetadata srcRecordMetadata, String column, RecordMetadata destRecordMetadata, String destColumn) {
        return CriteriaBuilder.getJoinCriteria(new Column(new Table(srcRecordMetadata.getName()), column), new Column(new Table(destRecordMetadata.getName()), destColumn));
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.criteria.CriteriaQueryBuilder#getQuery()
     */
    @Override
    public String getQuery(){
        return queryBuilder.getQuery();
    }
}