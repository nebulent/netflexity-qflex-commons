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
package org.netflexity.api.mb.ibm.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.netflexity.api.mb.MbMessageFlowStatistics;
import org.netflexity.api.mb.MbStatisticsAccounting;
import org.netflexity.api.util.CommonsDigesterFactory;
import org.netflexity.api.util.PropertiesUtil;
import org.netflexity.api.util.StringConstants;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author FEDORMAX
 *
 * FlowStatisticsAccountingDigester
 */
public class FlowStatisticsAccountingDigester {
    
    public static Logger logger = Logger.getLogger(FlowStatisticsAccountingDigester.class.getPackage().getName());
    public static final String FLOW_STATS_ACCNT_RULES = FlowStatisticsAccountingDigester.class.getPackage().getName().replace('.', '/') + "/flow-statistics-accounting-rules.xml";
    
    /**
     * @param source
     * @return
     */
    public MbStatisticsAccounting digest(String source){
        Digester digester = CommonsDigesterFactory.getInstance().getDigester(FLOW_STATS_ACCNT_RULES, FlowStatisticsAccountingDigester.class.getClassLoader());
        MbStatisticsAccounting flowStatsAccnt = null;
        try {
            flowStatsAccnt = (MbStatisticsAccounting)digester.parse(new InputSource(new StringReader(source)));
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SAXException e) {
            logger.error(e.getMessage(), e);
        }
        return flowStatsAccnt;
    }
    
    /**
     * @param source
     * @return
     */
    public MbStatisticsAccounting digest(InputStream source){
        Digester digester = CommonsDigesterFactory.getInstance().getDigester(FLOW_STATS_ACCNT_RULES, FlowStatisticsAccountingDigester.class.getClassLoader());
        MbStatisticsAccounting flowStatsAccnt = null;
        try {
            flowStatsAccnt = (MbStatisticsAccounting)digester.parse(source);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SAXException e) {
            logger.error(e.getMessage(), e);
        }
        return flowStatsAccnt;
    }
    
    /**
     * @param val
     * @return
     */
    public static Long toLong(String val){
        final Long ZERO_LONG = new Long(0);
        if(val != null && !StringConstants.ZERO_STR.equals(val)){
            try {
                return new Long(val);
            }
            catch (NumberFormatException e) {
                return ZERO_LONG;
            }
        }
        return ZERO_LONG;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        MbStatisticsAccounting flowStats = new FlowStatisticsAccountingDigester().digest(
                        PropertiesUtil.getInputStream(FlowStatisticsAccountingDigester.class.getPackage().getName().replace('.', '/') + "/flow-statistics-accounting.xml"
                                        , FlowStatisticsAccountingDigester.class.getClassLoader()));
        
        for (Iterator iter = flowStats.getFlows().iterator(); iter.hasNext();) {
            MbMessageFlowStatistics flow = (MbMessageFlowStatistics) iter.next();
            System.out.println(flow.getBrokerUUID());
        }
    }
}
