package org.netflexity.api.orm.command;

import java.util.List;

import org.netflexity.api.command.CommandException;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.OptimisticLockException;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.RecordValidationException;
import org.netflexity.api.orm.commands.BatchDeleteCommand;
import org.netflexity.api.orm.commands.BatchInsertCommand;
import org.netflexity.api.orm.commands.BatchUpdateCommand;
import org.netflexity.api.orm.commands.DeleteByCriteriaCommand;
import org.netflexity.api.orm.commands.DeleteCommand;
import org.netflexity.api.orm.commands.InsertCommand;
import org.netflexity.api.orm.commands.SelectByCriteriaCommand;
import org.netflexity.api.orm.commands.UpdateCommand;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilder;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilderImpl;
import org.netflexity.api.orm.util.SqlUtil;
import org.netflexity.api.util.Assertion;
import org.netflexity.api.util.sql.criteria.Criteria;

/**
 * @author Max Fedorov
 *
 * Collection of reusable functions that utilize common Dorm commands.
 */
public class OrmCommandHelper {

    /**
     * Method findByPK.
     *
     * @param recordMetadata
     * @param pk
     * @return Object
     * @throws CommandException
     */
    public static Object findByPK(RecordMetadata recordMetadata, Object pk) throws CommandException {
	return findByPK(recordMetadata, new Object[]{pk});
    }

    /**
     * @param recordMetadata
     * @param pk
     * @return
     * @throws CommandException
     */
    public static Object findByPK(RecordMetadata recordMetadata, Object pk[]) throws CommandException {
	Assertion.asert((pk != null && pk.length > 0), "Primary Key must be supplied for record " + recordMetadata.getName());

	// Create and excute command.
	CriteriaQueryBuilder queryBuilder = SqlUtil.buildSelectByPrimaryKeyQuery(recordMetadata, pk);
	SelectByCriteriaCommand cmd = new SelectByCriteriaCommand(queryBuilder);
	cmd = (SelectByCriteriaCommand) cmd.run();
	return cmd.getFirstResult();
    }

    /**
     * Method findAll.
     *
     * @param recordMetadata
     * @param orderby
     * @param desc
     * @return List
     * @throws CommandException
     */
    public static List findAll(RecordMetadata recordMetadata, String orderby[], boolean desc) throws CommandException {
	CriteriaQueryBuilder criteriaBuilder = new CriteriaQueryBuilderImpl(recordMetadata);

	// Create order by clause.
	buildOrderByClause(criteriaBuilder, orderby, desc);

	// Create command.
	SelectByCriteriaCommand cmd = new SelectByCriteriaCommand(criteriaBuilder);

	// Execute command.
	cmd = (SelectByCriteriaCommand) cmd.run();
	return cmd.getResults();
    }

    /**
     * @param recordMetadata
     * @param columnName
     * @param values[]
     * @param criteria - additional criteria.
     * @param orderby
     * @param desc
     * @return
     * @throws CommandException
     */
    public static List findAllIn(RecordMetadata recordMetadata, String columnName, Object[] values, Criteria criteria, String orderby[], boolean desc) throws CommandException {
	Assertion.asert((values != null && values.length > 0), "IN() values cannot be null or empty");

	CriteriaQueryBuilder criteriaBuilder = new CriteriaQueryBuilderImpl(recordMetadata);

	// Add in() clause criteria.
	criteriaBuilder.addCriteria(criteriaBuilder.getInCriteria(columnName, values));

	// Add additional criteria if exists.
	if (criteria != null) {
	    criteriaBuilder.addCriteria(criteria);
	}

	// Create order by clause.
	buildOrderByClause(criteriaBuilder, orderby, desc);

	// Create command.
	SelectByCriteriaCommand cmd = new SelectByCriteriaCommand(criteriaBuilder);

	// Execute command.
	cmd = (SelectByCriteriaCommand) cmd.run();
	return cmd.getResults();
    }

    /**
     * @param recordMetadata
     * @param columnName
     * @param values
     * @param orderby
     * @param desc
     * @return
     * @throws CommandException
     */
    public static List findAllIn(RecordMetadata recordMetadata, String columnName, Object[] values, String orderby[], boolean desc) throws CommandException {
	return findAllIn(recordMetadata, columnName, values, null, orderby, desc);
    }

    /**
     * Method insert.
     *
     * @param datasourceMetadata
     * @param record
     * @return Object
     * @throws CommandException
     */
    public static Object insert(DatasourceMetadata datasourceMetadata, Object record) throws RecordValidationException, CommandException {
	InsertCommand cmd = new InsertCommand(datasourceMetadata, record);
	cmd = (InsertCommand) cmd.run();
	return cmd.getRecord();
    }

    /**
     * Batch insert.
     *
     * @param recordMetadata,
     * @param records
     * @return a list of lists describing all batch activity that took
     * place.
     * @throws CommandException
     */
    public static List insertBatch(RecordMetadata recordMetadata, List records) throws RecordValidationException, CommandException {
	BatchInsertCommand cmd = new BatchInsertCommand(recordMetadata, records);
	cmd = (BatchInsertCommand) cmd.run();
	return cmd.getResults();
    }

    /**
     * @param datasourceMetadata
     * @param record
     * @return
     * @throws OptimisticLockException
     * @throws RecordValidationException
     * @throws CommandException
     */
    public static Object update(DatasourceMetadata datasourceMetadata, Object record) throws OptimisticLockException, RecordValidationException, CommandException {
	UpdateCommand cmd = new UpdateCommand(datasourceMetadata, record);
	cmd = (UpdateCommand) cmd.run();
	return cmd.getRecord();
    }

    /**
     * @param datasourceMetadata
     * @param oldRec
     * @param newRec
     * @return
     * @throws OptimisticLockException
     * @throws RecordValidationException
     * @throws CommandException
     */
    public static Object update(DatasourceMetadata datasourceMetadata, Object oldRec, Object newRec) throws OptimisticLockException, RecordValidationException, CommandException {
	UpdateCommand cmd = new UpdateCommand(datasourceMetadata, oldRec, newRec);
	cmd = (UpdateCommand) cmd.run();
	return cmd.getRecord();
    }

    /**
     * Batch update.
     *
     * @param recordMetadata
     * @param records
     * @return a list of lists describing all batch activity that took
     * place.
     * @throws CommandException
     */
    public static List updateBatch(RecordMetadata recordMetadata, List records) throws RecordValidationException, CommandException {
	BatchUpdateCommand cmd = new BatchUpdateCommand(recordMetadata, records);
	cmd = (BatchUpdateCommand) cmd.run();
	return cmd.getResults();
    }

    /**
     * Method delete.
     *
     * @param datasourceMetadata
     * @param record
     * @throws CommandException
     */
    public static void delete(DatasourceMetadata datasourceMetadata, Object record) throws CommandException {
	DeleteCommand cmd = new DeleteCommand(datasourceMetadata, record);
	cmd = (DeleteCommand) cmd.run();
    }

    /**
     * Batch delete.
     *
     * @param recordMetadata
     * @param records
     * @return a list of lists describing all batch activity that took
     * place.
     * @throws CommandException
     */
    public static List deleteBatch(RecordMetadata recordMetadata, List records) throws RecordValidationException, CommandException {
	BatchDeleteCommand cmd = new BatchDeleteCommand(recordMetadata, records);
	cmd = (BatchDeleteCommand) cmd.run();
	return cmd.getResults();
    }

    /**
     * Method deleteRecords.
     *
     * @param recordMetadata
     * @param criteria
     * @return int
     * @throws CommandException
     */
    public static int deleteRecords(RecordMetadata recordMetadata, Criteria criteria) throws CommandException {
	Assertion.asert((criteria != null), "Criteria cannot be null.");
	DeleteByCriteriaCommand cmd = new DeleteByCriteriaCommand(recordMetadata, criteria);
	cmd = (DeleteByCriteriaCommand) cmd.run();
	return cmd.getResult();
    }

    /**
     * Method findRecords will find all records without any sorting.
     *
     * @param recordMetadata
     * @param criteria
     * @return List
     * @throws CommandException
     */
    public static List findRecords(RecordMetadata recordMetadata, Criteria criteria) throws CommandException {
	return findRecords(recordMetadata, criteria, null, false);
    }

    /**
     * Method findRecords will find all records and apply given sorting.
     *
     * @param recordMetadata
     * @param criteria
     * @param orderby
     * @param desc
     * @return List
     * @throws CommandException
     */
    public static List findRecords(RecordMetadata recordMetadata, Criteria criteria, String orderby[], boolean desc) throws CommandException {
	return findRecords(recordMetadata, criteria, orderby, desc, 0);
    }

    /**
     * @param recordMetadata
     * @param criteria
     * @param orderby
     * @param desc
     * @param maxRows
     * @return
     * @throws CommandException
     */
    public static List findRecords(RecordMetadata recordMetadata, Criteria criteria, String orderby[], boolean desc, int maxRows) throws CommandException {
	Assertion.asert((criteria != null), "Criteria cannot be null.");

	// Create query builder and add criteria.
	CriteriaQueryBuilder criteriaBuilder = new CriteriaQueryBuilderImpl(recordMetadata);
	criteriaBuilder.addCriteria(criteria);

	// Construct order by clause.
	buildOrderByClause(criteriaBuilder, orderby, desc);

	// Execute query.
	return findRecords(criteriaBuilder, maxRows);
    }

    /**
     * @param criteriaBuilder
     * @return
     * @throws CommandException
     */
    public static List findRecords(CriteriaQueryBuilder criteriaBuilder) throws CommandException {
	return findRecords(criteriaBuilder, 0);
    }

    /**
     * @param criteriaBuilder
     * @param maxRows
     * @return
     * @throws CommandException
     */
    public static List findRecords(CriteriaQueryBuilder criteriaBuilder, int maxRows) throws CommandException {
	Assertion.asert((criteriaBuilder != null), "CriteriaQueryBuilder cannot be null.");

	// Execute command.
	SelectByCriteriaCommand cmd = new SelectByCriteriaCommand(criteriaBuilder, maxRows);
	cmd = (SelectByCriteriaCommand) cmd.run();
	return cmd.getResults();
    }

    /**
     * Method findFirstRecord will find first record without any
     * sorting.
     *
     * @param recordMetadata
     * @param criteria
     * @return Object
     * @throws CommandException
     */
    public static Object findFirstRecord(RecordMetadata recordMetadata, Criteria criteria) throws CommandException {
	return findFirstRecord(recordMetadata, criteria, null, false);
    }

    /**
     * Method findRecords will find first record and apply given
     * sorting.
     *
     * @param recordMetadata
     * @param criteria
     * @param orderby
     * @param desc
     * @return Object
     * @throws CommandException
     */
    public static Object findFirstRecord(RecordMetadata recordMetadata, Criteria criteria, String orderby[], boolean desc) throws CommandException {
	Assertion.asert((criteria != null), "Criteria cannot be null.");

	// Create query builder and add criteria.
	CriteriaQueryBuilder criteriaBuilder = new CriteriaQueryBuilderImpl(recordMetadata);
	criteriaBuilder.addCriteria(criteria);

	// Construct order by clause.
	buildOrderByClause(criteriaBuilder, orderby, desc);

	// Execute query.
	return findFirstRecord(criteriaBuilder);
    }

    /**
     * @param criteriaBuilder
     * @return
     * @throws CommandException
     */
    public static Object findFirstRecord(CriteriaQueryBuilder criteriaBuilder) throws CommandException {
	Assertion.asert((criteriaBuilder != null), "CriteriaQueryBuilder cannot be null.");

	// Execute command.
	SelectByCriteriaCommand cmd = new SelectByCriteriaCommand(criteriaBuilder);
	cmd = (SelectByCriteriaCommand) cmd.run();
	return cmd.getFirstResult();
    }

    /**
     * Method buildOrderByClause creates an order by clause.
     *
     * @param criteriaBuilder
     * @param orderby
     * @param desc
     */
    public static void buildOrderByClause(CriteriaQueryBuilder criteriaBuilder, String orderby[], boolean desc) {
	if (orderby != null) {
	    int len = orderby.length;
	    for (int i = 0; i < len; i++) {
		criteriaBuilder.addOrderByColumn(orderby[i]);
	    }
	    criteriaBuilder.setOrderByDesc(desc);
	}
    }
}
