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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.sql.SqlConstants;

/**
 * @author MAX
 * 
 * Abstract representation of any query builder.
 */
public abstract class AbstractQuery implements Query {
    
    protected List columns = new ArrayList(4);
    protected List criteria = new ArrayList(4);
    protected Set tables = new HashSet(4);
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Query#addColumn(org.netflexity.api.util.sql.criteria.Column)
     */
    public Query addColumn(Column column){
        columns.add(column);
        tables.add(column.getTable());
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Query#addCriteria(org.netflexity.api.util.sql.criteria.Criteria)
     */
    public Query addCriteria(Criteria criterion){
        if(criterion instanceof JoinCriteria){
            tables.add(((JoinCriteria)criterion).getSourceColumn().getTable());
            tables.add(((JoinCriteria)criterion).getDestColumn().getTable());
        }
        else if(criterion instanceof CompoundCriteria){
            processCompoundCriteria(criterion);
        }
        else{
            tables.add(((SimpleCriteria)criterion).getColumn().getTable());
        }
        criteria.add(criterion);
        return this;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Query#getColumns()
     */
    public Iterator getColumns() {
        return Collections.unmodifiableCollection(columns).iterator();
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Query#getCriteria()
     */
    public Iterator getCriteria() {
        return Collections.unmodifiableCollection(criteria).iterator();
    }
    
    /**
     * @param criterion
     */
    private void processCompoundCriteria(Criteria criterion){
        if(criterion instanceof CompoundCriteria){
            Criteria crit1 = ((CompoundCriteria)criterion).lhs;
            Criteria crit2 = ((CompoundCriteria)criterion).rhs;
            
            if(crit1 instanceof JoinCriteria){
                tables.add(((JoinCriteria)crit1).getSourceColumn().getTable());
                tables.add(((JoinCriteria)crit1).getDestColumn().getTable());
            }
            else if(crit1 instanceof SimpleCriteria){
                tables.add(((SimpleCriteria)crit1).getColumn().getTable());
            }
            else if(crit1 instanceof CompoundCriteria){
                processCompoundCriteria(crit1);
            }
            
            if(crit2 instanceof JoinCriteria){
                tables.add(((JoinCriteria)crit2).getSourceColumn().getTable());
                tables.add(((JoinCriteria)crit2).getDestColumn().getTable());
            }
            else if(crit2 instanceof SimpleCriteria){
                tables.add(((SimpleCriteria)crit2).getColumn().getTable());
            }
            else if(crit2 instanceof CompoundCriteria){
                processCompoundCriteria(crit2);
            }
        }
    }
    
    /**
     * @return criteria sql, or empty if no criteria.
     */
    protected String createCriteria(){
        // Find all criteria, other than join.
        List nonjoins = new ArrayList();
        for (Iterator iter = criteria.iterator(); iter.hasNext();) {
            Criteria criteria = (Criteria) iter.next();
            if(!(criteria instanceof JoinCriteria)){
                nonjoins.add(criteria);
            }
        }
        
        // Iterate through all simple and compound criteria.
        Iterator criteriaIter = nonjoins.iterator();
        if(criteriaIter.hasNext()){
            StringBuffer criteriaSql = new StringBuffer(16 * criteria.size());
            do{
                Criteria crit = (Criteria) criteriaIter.next();
                criteriaSql.append(crit.getSql());
                if(criteriaIter.hasNext()){
                    criteriaSql.append(SqlConstants.AND);
                }
            }
            while(criteriaIter.hasNext());
            
            return criteriaSql.toString();
        }
        return StringUtils.EMPTY;
    }
}
