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
package org.netflexity.api.orm;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.netflexity.api.command.CommandException;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.DatasourceMetadataFactory;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.command.OrmCommandHelper;
import org.netflexity.api.orm.commands.aggregate.CountColumnCommand;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilder;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilderImpl;
import org.netflexity.api.orm.util.RecordUtil;
import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.sql.criteria.Criteria;

public class OrmCommandsTest extends TestCase {

    private RecordMetadata alertTypeMeta, monitorsAlertTypeMeta, configAttributeTypeMeta; // alertStatusTypeMeta
    private DatasourceMetadata dmd;
    private Logger logger;
    
    public OrmCommandsTest(){
        // Configure log4j.
        BasicConfigurator.configure();
        logger = Logger.getLogger(this.getClass().getName());
        
        // Get metadata.
        String packagePath = StringUtils.replace(MetadataTest.class.getPackage().getName(), StringConstants.DOT, StringConstants.FORWARD_SLASH);  
        dmd = DatasourceMetadataFactory.getInstance(packagePath + "/dorm.xml").getDatasourceMetadataByName("sample");
        MetadataTest.printDatasourceMetadataContents(dmd);
        
        // Get alert type.
        alertTypeMeta = dmd.getRecordMetadataByClass(AlertTypes.class);
        // alertStatusTypeMeta = dmd.getRecordMetadataByClass(AlertStatusTypes.class);
        monitorsAlertTypeMeta = dmd.getRecordMetadataByClass(MonitorsAlertType.class);
        configAttributeTypeMeta = dmd.getRecordMetadataByClass(ConfigAttributeType.class);
    }
    
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * 
     */
    public void testCountAll(){
        logger.debug("->COUNT (*) ALERT_TYPES");
        // Count all alert types.
        try {
            Criteria crit = null;
            CountColumnCommand cmd = new CountColumnCommand(alertTypeMeta, crit);
            cmd = (CountColumnCommand)cmd.run();
            Double count = cmd.getResult();
            assertTrue("count(*) from alert_types cannot return NULL", (count != null));
            logger.debug("Found " + count + " alert types");
        }
        catch (CommandException e) {
            logger.error(e.getMessage(), e);
            assertTrue(e.getMessage(), false);
        }
        
        logger.debug("->SELECT COUNT(alert_type_id) ALERT_TYPES");
        // Count all alert type ids.
        try {
            CountColumnCommand cmd = new CountColumnCommand(alertTypeMeta, AlertTypes.ALERT_TYPE_ID_COL);
            cmd = (CountColumnCommand)cmd.run();
            Double count = cmd.getResult();
            assertTrue("count(alert_type_id) from alert_types cannot return NULL", (count != null));
            logger.debug("Found " + count + " alert type ids");
        }
        catch (CommandException e) {
            logger.error(e.getMessage(), e);
            assertTrue(e.getMessage(), false);
        }
    }
    
    /**
     * 
     */
    public void testSelectAll() {
        logger.debug("->SELECT ALL ALERT_TYPES");
        
        int index = 0;
        try {
            List alertTypes = OrmCommandHelper.findAll(alertTypeMeta, new String[]{}, false);
            for (Iterator iter = alertTypes.iterator(); iter.hasNext(); index++) {
                AlertTypes rec = (AlertTypes) iter.next();
                logger.debug("--->" + index + "<->" + RecordUtil.toString(alertTypeMeta, rec));
            }
        }
        catch (CommandException e) {
            logger.error(e.getMessage(), e);
            assertTrue(e.getMessage(), false);
        }
        
        logger.debug("->SELECT ALL CONFIG_ATTRIBUTE_TYPES");
        
        try {
            List configAttributeTypes = OrmCommandHelper.findAll(configAttributeTypeMeta, new String[]{}, false);
            for (Iterator iter = configAttributeTypes.iterator(); iter.hasNext(); index++) {
                ConfigAttributeType rec = (ConfigAttributeType) iter.next();
                logger.debug("--->" + index + "<->" + RecordUtil.toString(configAttributeTypeMeta, rec));
            }
        }
        catch (CommandException e) {
            logger.error(e.getMessage(), e);
            assertTrue(e.getMessage(), false);
        }
    }
    
    /**
     * 
     */
    public void testSelectByCriteria(){
        logger.debug("->SELECT BY CRITERIA");
        
        // Select record by criteria.
        CriteriaQueryBuilder queryBuilder = new CriteriaQueryBuilderImpl(alertTypeMeta);
        Criteria crit = queryBuilder.getEqualityCriteria(AlertTypes.ALERT_TYPE_ID_COL, new Integer(2));
        queryBuilder.addCriteria(crit);
        try {
            AlertTypes rec = (AlertTypes) OrmCommandHelper.findFirstRecord(queryBuilder);
            assertTrue("Failed to find record by criteria with alert_type_id = 2", (rec != null));
            logger.debug(RecordUtil.toString(alertTypeMeta, rec));
        }
        catch (CommandException e) {
            logger.error(e.getMessage(), e);
            assertTrue(e.getMessage(), false);
        }
    }
    
    /**
     * 
     */
    public void testJoinedSelectByCriteria(){
        logger.debug("->JOINED SELECT BY CRITERIA");
        
        // Select record by criteria.
        CriteriaQueryBuilder queryBuilder = new CriteriaQueryBuilderImpl(alertTypeMeta);
        queryBuilder.addCriteria(queryBuilder.getJoinCriteria(AlertTypes.ALERT_TYPE_ID_COL, monitorsAlertTypeMeta, MonitorsAlertType.ALERT_TYPE_ID_COL));
        queryBuilder.addCriteria(queryBuilder.getEqualityCriteria(AlertTypes.ALERT_TYPE_ID_COL, new Integer(2)));
        try {
            List recs = OrmCommandHelper.findRecords(queryBuilder);
            assertTrue("Failed to find joined records by criteria with alert_type_id = 2", (recs != null && !recs.isEmpty()));
            logger.debug("Found " + recs.size() + " records.");
        }
        catch (CommandException e) {
            logger.error(e.getMessage(), e);
            assertTrue(e.getMessage(), false);
        }
    }
}
