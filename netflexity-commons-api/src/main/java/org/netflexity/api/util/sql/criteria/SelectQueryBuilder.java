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
package org.netflexity.api.util.sql.criteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.sql.SqlConstants;

/**
 * @author MAX
 *
 */
public class SelectQueryBuilder extends AbstractQuery implements SelectQuery{
    
    protected List groupByColumns = new ArrayList(5);
    protected List havingColumns = new ArrayList(5);
    protected List orderByColumns = new ArrayList(5);
    protected boolean orderByDesc;
    
    public SelectQueryBuilder(){}
    
    /**
     * @param column
     */
    public SelectQueryBuilder(Column column){
        addColumn(column);
    }
    
    /**
     * @param columns
     */
    public SelectQueryBuilder(List columns){
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            addColumn(column);
        }
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.SelectQuery#getGroupByColumns()
     */
    public Iterator getGroupByColumns() {
        return Collections.unmodifiableCollection(groupByColumns).iterator();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.SelectQuery#addGroupByColumn(org.netflexity.api.util.sql.criteria.Column)
     */
    public SelectQuery addGroupByColumn(Column groupByColumn) {
        groupByColumns.add(groupByColumn);
        return this;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.SelectQuery#getHavingColumns()
     */
    public Iterator getHavingColumns() {
        return Collections.unmodifiableCollection(havingColumns).iterator();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.SelectQuery#addHavingColumn(org.netflexity.api.util.sql.criteria.Column)
     */
    public SelectQuery addHavingColumn(Column havingColumn) {
        havingColumns.add(havingColumn);
        return this;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.SelectQuery#getOrderByColumns()
     */
    public Iterator getOrderByColumns() {
        return Collections.unmodifiableCollection(orderByColumns).iterator();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.SelectQuery#addOrderByColumns(org.netflexity.api.util.sql.criteria.Column)
     */
    public SelectQuery addOrderByColumn(Column orderByColumn) {
        orderByColumns.add(orderByColumn);
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.SelectQuery#isOrderByDesc()
     */
    public boolean isOrderByDesc() {
        return orderByDesc;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.SelectQuery#setOrderByDesc(boolean)
     */
    public void setOrderByDesc(boolean orderByDesc) {
        this.orderByDesc = orderByDesc;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Query#getQuery()
     */
    public String getQuery(){
        StringBuffer sql = new StringBuffer(256);
        
        // Create selection list.
        sql.append(SqlConstants.SELECT).append(createColumnList());
        
        // Create from clause.
        sql.append(SqlConstants.FROM).append(createTableList());
        
        // Construct join sql if exists.
        String joinSql = createJoin();
        String criteriaSql = createCriteria();
        boolean hasJoinSql = StringUtils.isNotBlank(joinSql);
        boolean hasCriteriaSql = StringUtils.isNotBlank(criteriaSql);
        
        // Create where clause if exists.
        if(hasJoinSql || hasCriteriaSql){
            sql.append(SqlConstants.WHERE);
            
            // Add joins first.
            if(hasJoinSql && hasCriteriaSql){
                sql.append(joinSql).append(SqlConstants.AND).append(criteriaSql);
            }
            else if(hasJoinSql){
                sql.append(joinSql);
            }
            else if(hasCriteriaSql){
                sql.append(criteriaSql);
            }
        }
        
        // Add group by if exists.
        String groupBySql = createGroupByList();
        if(StringUtils.isNotBlank(groupBySql)){
            sql.append(SqlConstants.GROUP_BY).append(groupBySql);
        }
        
        // Add having if exists.
        String havingSql = createHavingList();
        if(StringUtils.isNotBlank(havingSql)){
            sql.append(SqlConstants.HAVING).append(havingSql);
        }
        
        // Add order by if exists.
        String orderBySql = createOrderByList();
        if(StringUtils.isNotBlank(orderBySql)){
            sql.append(SqlConstants.ORDER_BY).append(orderBySql);
        }
        
        return sql.toString();
    }
    
    /**
     * @return column list.
     */
    private String createColumnList(){
        if(!columns.isEmpty()){
            StringBuffer sql = new StringBuffer(10 * columns.size());
            boolean first = true;
            for (Iterator iter = columns.iterator(); iter.hasNext();) {
                Column column = (Column) iter.next();
                if (!first) {
                    sql.append(StringConstants.COMMA);
                }
                else{
                    first = false;
                }
                sql.append(column.getFullName());
            }
            return sql.toString();
        }
        return StringUtils.EMPTY;
    }
    
    /**
     * @return column list.
     */
    private String createGroupByList(){
        if(!groupByColumns.isEmpty()){
            StringBuffer sql = new StringBuffer(10 * groupByColumns.size());
            boolean first = true;
            for (Iterator iter = groupByColumns.iterator(); iter.hasNext();) {
                Column column = (Column) iter.next();
                if(!(column instanceof WildCardColumn) && !(column instanceof AggregateColumn)){
                    if (!first) {
                        sql.append(StringConstants.COMMA);
                    }
                    else{
                        first = false;
                    }
                    sql.append(column.getFullName());
                }
            }
            return sql.toString();
        }
        return StringUtils.EMPTY;
    }
    
    /**
     * @return column list.
     */
    private String createOrderByList(){
        if(!orderByColumns.isEmpty()){
            StringBuffer sql = new StringBuffer(10 * orderByColumns.size());
            boolean first = true;
            for (Iterator iter = orderByColumns.iterator(); iter.hasNext();) {
                Column column = (Column) iter.next();
                if(!(column instanceof WildCardColumn) && !(column instanceof AggregateColumn)){
                    if (!first) {
                        sql.append(StringConstants.COMMA);
                    }
                    else{
                        first = false;
                    }
                    sql.append(column.getFullName());
                }
            }
            return sql.toString() + ((isOrderByDesc()) ? SqlConstants.DESC : StringUtils.EMPTY);
        }
        return StringUtils.EMPTY;
    }
    
    /**
     * @return column list.
     */
    private String createHavingList(){
        if(!havingColumns.isEmpty()){
            StringBuffer sql = new StringBuffer(10 * havingColumns.size());
            boolean first = true;
            for (Iterator iter = havingColumns.iterator(); iter.hasNext();) {
                Column column = (Column) iter.next();
                if(!(column instanceof WildCardColumn) && !(column instanceof AggregateColumn)){
                    if (!first) {
                        sql.append(StringConstants.COMMA);
                    }
                    else{
                        first = false;
                    }
                    sql.append(column.getFullName());
                }
            }
            return sql.toString();
        }
        return StringUtils.EMPTY;
    }
    
    /**
     * @return list of utilized tables.
     */
    private String createTableList(){
        if(!tables.isEmpty()){
            StringBuffer sql = new StringBuffer(15 * tables.size());
            boolean first = true;
            for (Iterator iter = tables.iterator(); iter.hasNext();) {
                Table table = (Table) iter.next();
                if (!first) {
                    sql.append(StringConstants.COMMA);
                }
                else{
                    first = false;
                }
                sql.append(table.getName());
            }
            return sql.toString();
        }
        return StringUtils.EMPTY;
    }
    
    /**
     * @return join sql, or empty if no joins.
     */
    private String createJoin(){
        // Find join criteria.
        List joins = new ArrayList();
        for (Iterator iter = criteria.iterator(); iter.hasNext();) {
            Criteria criteria = (Criteria) iter.next();
            if(criteria instanceof JoinCriteria){
                joins.add(criteria);
            }
        }
        
        // Iterate over joins.
        Iterator joinIter = joins.iterator();
        if(joinIter.hasNext()){
            StringBuffer joinSql = new StringBuffer(48 * joins.size());
            joinSql.append(StringConstants.LP);
            do{
                JoinCriteria join = (JoinCriteria) joinIter.next();
                joinSql.append(join.getSql());
                if(joinIter.hasNext()){
                    joinSql.append(SqlConstants.AND);
                }
            }
            while(joinIter.hasNext());
            joinSql.append(StringConstants.RP);
            
            return joinSql.toString();
        }
        return StringUtils.EMPTY;
    }
}
