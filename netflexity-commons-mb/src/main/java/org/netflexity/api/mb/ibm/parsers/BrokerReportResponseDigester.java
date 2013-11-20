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
package org.netflexity.api.mb.ibm.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.netflexity.api.mb.MbExecGroup;
import org.netflexity.api.mb.MbMessageBroker;
import org.netflexity.api.mb.MbMessageFlow;
import org.netflexity.api.util.CommonsDigesterFactory;
import org.netflexity.api.util.PropertiesUtil;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author FEDORMAX
 *
 * BrokerReportResponseDigester
 */
public class BrokerReportResponseDigester {
    
    public static Logger logger = Logger.getLogger(BrokerReportResponseDigester.class.getPackage().getName());
    public static final String BROKER_REPORT_RESPONSE_RULES = BrokerReportResponseDigester.class.getPackage().getName().replace('.', '/') + "/broker-report-response-rules.xml";
    
    /**
     * @param source
     * @return
     */
    public MbMessageBroker digest(String source){
        Digester digester = CommonsDigesterFactory.getInstance().getDigester(BROKER_REPORT_RESPONSE_RULES, BrokerReportResponseDigester.class.getClassLoader());
        MbMessageBroker broker = null;
        try {
            broker = (MbMessageBroker)digester.parse(new InputSource(new StringReader(source)));
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SAXException e) {
            logger.error(e.getMessage(), e);
        }
        return broker;
    }
    
    /**
     * @param source
     * @return
     */
    public MbMessageBroker digest(InputStream source){
        Digester digester = CommonsDigesterFactory.getInstance().getDigester(BROKER_REPORT_RESPONSE_RULES, BrokerReportResponseDigester.class.getClassLoader());
        MbMessageBroker broker = null;
        try {
            broker = (MbMessageBroker)digester.parse(source);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SAXException e) {
            logger.error(e.getMessage(), e);
        }
        return broker;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        MbMessageBroker broker = new BrokerReportResponseDigester().digest(
                        PropertiesUtil.getInputStream(BrokerReportResponseDigester.class.getPackage().getName().replace('.', '/') + "/broker-report-response.xml"
                                        , BrokerReportResponseDigester.class.getClassLoader()));
        
        for (Iterator iter = broker.getExecGroups().iterator(); iter.hasNext();) {
            MbExecGroup eg = (MbExecGroup) iter.next();
            System.out.println("ExecGroup=" + eg.getExecGroupName());
            for (Iterator iterator = eg.getMessageFlows().iterator(); iterator.hasNext();) {
                MbMessageFlow f = (MbMessageFlow) iterator.next();
                System.out.println("Flow=" + f.getFlowName());
            }
        }
    }
}
