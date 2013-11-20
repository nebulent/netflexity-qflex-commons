package org.netflexity.api.orm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.netflexity.api.command.AbstractCommandEventListener;
import org.netflexity.api.command.CommandEvent;
import org.netflexity.api.command.CommandEventListenerRegistryImpl;
import org.netflexity.api.command.CommandException;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.DatasourceMetadataFactory;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.RecordValidationException;
import org.netflexity.api.orm.command.OrmCommandHelper;
import org.netflexity.api.orm.commands.BatchInsertCommand;
import org.netflexity.api.orm.commands.BatchUpdateCommand;
import org.netflexity.api.orm.commands.SelectQueryCommand;
import org.netflexity.api.orm.commands.UpdateCommand;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilder;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilderImpl;
import org.netflexity.api.orm.jdbc.RecordRowCallbackHandler;
import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.jdbc.AbstractStatementCallbackHandler;
import org.netflexity.api.util.jdbc.CachedRowCallbackHandler;
import org.netflexity.api.util.jdbc.PreparedStatementExecutor;
import org.netflexity.api.util.jdbc.StandardStatementExecutor;
import org.netflexity.api.util.jdbc.StatementExecutor;
import org.netflexity.api.util.sql.criteria.Criteria;

/**
 * @author Max Fedorov
 *
 * Test entire ORM framework.
 */
public class OrmTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Configure log4j.
        BasicConfigurator.configure();
        
        // Get metadata.
        String packagePath = StringUtils.replace(MetadataTest.class.getPackage().getName(), StringConstants.DOT, StringConstants.FORWARD_SLASH);  
        DatasourceMetadata dmd = DatasourceMetadataFactory.getInstance(packagePath + "/dorm.xml").getDatasourceMetadataByName("sample");
		MetadataTest.printDatasourceMetadataContents(dmd);
		
		// Get alert type.
		RecordMetadata alertTypeMeta = dmd.getRecordMetadataByClass(AlertTypes.class);

		// Get datasource.
		// Datasource ds = DatabaseDatasourceFactory.getInstance().getDatasource(dmd);
		
		// Set listener.
		CommandEventListenerRegistryImpl.getInstance().registerCommandEventListener(new AbstractCommandEventListener(UpdateCommand.class) {
            public void handleEvent(CommandEvent event) {
                System.err.println("Update event happened!!!" + event.getTimeStamp());
            }
            public void init(Properties properties) {
            }
        }
		);
		
        // 
        
		// Select all alert types.
		
		
		// Insert alert type.
		AlertTypes rec = new AlertTypes();
		rec.setDisplay_seq_no(new Integer(40));
		rec.setDescription("new alert type");
		try {
            rec = (AlertTypes) OrmCommandHelper.insert(dmd, rec); 
            System.out.println("Inserted " + alertTypeMeta.getName() + " with pk=" + alertTypeMeta.getPrimaryKeyMetadata());
        } 
		catch (RecordValidationException e3) {
            // Catch validation exceptions.
            for (Iterator iter = e3.getErrors().iterator(); iter.hasNext();) {
                String error = (String) iter.next();
                System.err.println("Validation Exception: " + error);
            }
        }
        catch (CommandException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		// Select record by predicate.
        CriteriaQueryBuilder queryBuilder = new CriteriaQueryBuilderImpl(alertTypeMeta);
		Criteria crit = queryBuilder.getEqualityCriteria(AlertTypes.ALERT_TYPE_ID_COL, rec.getAlert_type_id());
        queryBuilder.addCriteria(crit);
		try {
            rec = (AlertTypes) OrmCommandHelper.findFirstRecord(queryBuilder);
            System.out.println("Found record "+ alertTypeMeta.getName() + " with pk=" + alertTypeMeta.getPrimaryKeyMetadata() + " ; " + rec.toString());
        }
        catch (CommandException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
		
		// Try to update record, causes validation error.
		rec.setDescription("--------------15");
		try {
            OrmCommandHelper.update(dmd, rec);
            System.out.println("Updated " + alertTypeMeta.getName());
        } 
		catch (RecordValidationException e1) {
		    // Catch validation exceptions.
            for (Iterator iter = e1.getErrors().iterator(); iter.hasNext();) {
                String error = (String) iter.next();
                System.err.println("Validation Exception: " + error);
            }
        }
        catch (CommandException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		// Update successfully.
		rec.setDescription("alert type");
		try {
            OrmCommandHelper.update(dmd, rec);
            System.out.println("Updated " + alertTypeMeta.getName());
        } 
		catch (RecordValidationException e2) {
            //Catch validation exceptions.
            for (Iterator iter = e2.getErrors().iterator(); iter.hasNext();) {
                String error = (String) iter.next();
                System.err.println("Validation Exception: " + error);
            }
        }
        catch (CommandException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		// Select updated record by pk.
		try {
            rec = (AlertTypes) OrmCommandHelper.findByPK(alertTypeMeta, rec.getAlert_type_id());
            System.out.println("Found updated record "+ alertTypeMeta.getName() + " with pk=" + alertTypeMeta.getPrimaryKeyMetadata() + " ; " + rec.toString());
        }
        catch (CommandException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
		
		// Delete record.
		try {
            OrmCommandHelper.delete(dmd, rec);
        }
        catch (CommandException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
		
        try {
            AlertTypes deleted = (AlertTypes) OrmCommandHelper.findByPK(alertTypeMeta, rec.getAlert_type_id());
            System.out.println((deleted == null ? "Deleted record->" : "Failed to delete record->") + rec.toString());
        }
        catch (CommandException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
		
		// Test QueryCommand with custom bean.
        StatementExecutor standardExecutor = new StandardStatementExecutor(dmd.getQueryByName("numberOfAlertTypes"), new CachedRowCallbackHandler(NumberOfAlertTypesRowSetBean.class));
		SelectQueryCommand queryCommand = new SelectQueryCommand(dmd, standardExecutor);
		try {
		    queryCommand = (SelectQueryCommand)queryCommand.run();
            List results = queryCommand.getResults();
            for (Iterator iter = results.iterator(); iter.hasNext();) {
                NumberOfAlertTypesRowSetBean rs = (NumberOfAlertTypesRowSetBean) iter.next();
                System.out.println("Found (" + rs.getNumberOfAlertTypes() + ") " + alertTypeMeta.getName() + " record(s).");
            }
            
        } catch (CommandException e) {
            e.printStackTrace();
        }
        
        // Test QueryCommand.
        StatementExecutor queryExec = new PreparedStatementExecutor(dmd.getQueryByName("alertTypeOne"), new RecordRowCallbackHandler(alertTypeMeta),
        new AbstractStatementCallbackHandler() {
            /* (non-Javadoc)
             * @see org.netflexity.api.util.jdbc.StatementCallbackHandler#processStatement(java.sql.Statement)
             */
            public void processStatement(Statement st) throws SQLException {
                PreparedStatement pst = (PreparedStatement) st;
                pst.setLong(1, 1);
            }
        });

		queryCommand = new SelectQueryCommand(dmd, queryExec);
		try {
            queryCommand = (SelectQueryCommand)queryCommand.run();
            List results = queryCommand.getResults();
            for (Iterator iter = results.iterator(); iter.hasNext();) {
                AlertTypes newRec = (AlertTypes) iter.next();
                System.out.println("Found record "+ alertTypeMeta.getName() + " with pk=" + alertTypeMeta.getPrimaryKeyMetadata() + " ; " + newRec.toString());
            }
        } catch (CommandException e) {
            e.printStackTrace();
        }
        
        // Test select.
        /*queryExec = new StandardStatementExecutor("select * from alert_types");
        queryCommand = new QueryCommand(dmd.getName(), queryExec);
		try {
            queryCommand = (QueryCommand)queryCommand.run();
            CachedRowSet rs = queryCommand.getResults();
            System.out.println("!!!!!!! Found :" + getRowCount(rs) + " record(s).");
            while(rs.next()){
                System.out.println("Pk = " + rs.getLong(1));
            }
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        
        // Test batch insert.
        List batchRecords = new ArrayList(3);
        rec = new AlertTypes();
        rec.setDisplay_seq_no(new Integer(41));
        rec.setDescription("41");
        batchRecords.add(rec);
        
        rec = new AlertTypes();
        rec.setDisplay_seq_no(new Integer(42));
        rec.setDescription("42");
        batchRecords.add(rec);
        
        rec = new AlertTypes();
        rec.setDisplay_seq_no(new Integer(43));
        rec.setDescription("43");
        batchRecords.add(rec);
        
        BatchInsertCommand batchInsertCommand = new BatchInsertCommand(alertTypeMeta, batchRecords, 3, false);
        try {
            batchInsertCommand = (BatchInsertCommand)batchInsertCommand.run();
            List results = batchInsertCommand.getResults();
            for (Iterator iter = results.iterator(); iter.hasNext();) {
                System.out.println("Batch insert results:" + iter.next());
            }
            
        } catch (CommandException e) {
            e.printStackTrace();
        }
        
        // Test batch update command.
        queryBuilder = new CriteriaQueryBuilderImpl(alertTypeMeta);
        queryBuilder.addCriteria(queryBuilder.getGreaterThanEqualsCriteria(AlertTypes.DISPLAY_SEQ_NO_COL, new Integer(40)));
        try {
            batchRecords = OrmCommandHelper.findRecords(queryBuilder);
            for (Iterator iter = batchRecords.iterator(); iter.hasNext();) {
                AlertTypes at = (AlertTypes) iter.next();
                at.setDescription("123");
            }
        }
        catch (CommandException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        BatchUpdateCommand batchUpdateCommand = new BatchUpdateCommand(alertTypeMeta, batchRecords, 3, false);
        try {
            batchUpdateCommand = (BatchUpdateCommand)batchUpdateCommand.run();
            List results = batchUpdateCommand.getResults();
            for (Iterator iter = results.iterator(); iter.hasNext();) {
                System.out.println("Batch update results:" + iter.next());
            }
            
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
    
    /**
	 * @param rs
	 * @return
	 *//*
	public static int getRowCount(CachedRowSet rs){
	    int rows = 0;
	    try {
            rs.last();
            rows = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	    return rows;
	}*/
}
