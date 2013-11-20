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
package org.netflexity.api.mb.ibm.util;

import java.io.Serializable;

import org.netflexity.api.mb.MbExecGroup;
import org.netflexity.api.mb.MbMessageBroker;
import org.netflexity.api.mb.MbMessageFlow;
import org.netflexity.api.mb.ibm.IBMMbExecGroup;
import org.netflexity.api.mb.ibm.IBMMbMessageBroker;
import org.netflexity.api.mb.ibm.IBMMbMessageFlow;
import org.netflexity.api.mb.ibm.enums.SnapshotNodeDataLevelEnum;
import org.netflexity.api.util.StringConstants;


/**
 * @author MAX
 *
 */
public class IBMMbMqsiChangeFlowStats implements Serializable{

    public static final String VERSION = "version";
    public static final String LABEL = "label";
    public static final String UUID = "uuid";
    public static final String STATS_SNAP_OUTPUT_FORMAT = "StatsSnapOutputFormat";
    public static final String STATS_SNAP_THREAD_DATA_LEVEL = "StatsSnapThreadDataLevel";
    public static final String STATS_SNAP_NODE_DATA_LEVEL = "StatsSnapNodeDataLevel";
    public static final String STATS_SNAP_PUBLICATION_ON = "StatsSnapPublicationOn";
    public static final String ACTIVE = "active";
    public static final String INACTIVE = "inactive";
    public static final String ADVANCED = "advanced";
    public static final String BASIC = "basic";
    public static final String NONE = "none";
    public static final String XML = "xml";
    
    public static final String ALL_MESSAGE_FLOWS_EMPTY = "<AllMessageFlows/>";
    public static final String ALL_EXEC_GROUPS_EMPTY = "<AllExecutionGroups/>";
    public static final String ALL_MESSAGE_FLOWS_START = "<AllMessageFlows ";
    public static final String ALL_EXEC_GROUPS_START = "<AllExecutionGroups>";
    public static final String ALL_EXEC_GROUPS_END = "</AllExecutionGroups>";
    public static final String CHANGE_START = "<Change>";
    public static final String CHANGE_END = "</Change>";
    public static final String BROKER_START = "<Broker ";
    public static final String BROKER_END = "</Broker>";
    public static final String EXEC_GROUP_START = "<ExecutionGroup ";
    public static final String EXEC_GROUP_END = "</ExecutionGroup>";
    public static final String FLOW_START = "<MessageFlow ";
    public static final String REPORT_START = "<Report recursive='no'>";
    public static final String REPORT_END = "</Report>";
    
    private MbMessageBroker broker;
    private MbExecGroup execGroup;
    private MbMessageFlow flow;
    
    private boolean isSnapshotPublicationOn;
    private SnapshotNodeDataLevelEnum snapshotNodeDataLevel = SnapshotNodeDataLevelEnum.ADVANCED;
    private String snapshotThreadDataLevel = BASIC;
    private String snapshotOutputFormat = XML;
    
    /**
     * @param broker
     */
    public IBMMbMqsiChangeFlowStats(MbMessageBroker broker){
        this.broker = broker;
    }
    
    /**
     * @param execGroup
     */
    public IBMMbMqsiChangeFlowStats(MbExecGroup execGroup){
        this(execGroup.getBroker());
        this.execGroup = execGroup;
    }
    
    /**
     * @param broker
     * @param isSnapshotPublicationOn
     * @param snapshotNodeDataLevel
     */
    public IBMMbMqsiChangeFlowStats(MbMessageBroker broker, boolean isSnapshotPublicationOn, SnapshotNodeDataLevelEnum snapshotNodeDataLevel) {
        this.isSnapshotPublicationOn = isSnapshotPublicationOn;
        this.broker = broker;
        this.snapshotNodeDataLevel = snapshotNodeDataLevel;
    }
    
    /**
     * @param execGroup
     * @param isSnapshotPublicationOn
     * @param snapshotNodeDataLevel
     */
    public IBMMbMqsiChangeFlowStats(MbExecGroup execGroup, boolean isSnapshotPublicationOn, SnapshotNodeDataLevelEnum snapshotNodeDataLevel) {
        this(execGroup.getBroker(), isSnapshotPublicationOn, snapshotNodeDataLevel);
        this.execGroup = execGroup;
    }
    
    /**
     * @param flow
     * @param isSnapshotPublicationOn
     * @param snapshotNodeDataLevel
     */
    public IBMMbMqsiChangeFlowStats(MbMessageFlow flow, boolean isSnapshotPublicationOn, SnapshotNodeDataLevelEnum snapshotNodeDataLevel) {
        this(flow.getExecGroup(), isSnapshotPublicationOn, snapshotNodeDataLevel);
        this.flow = flow;
    }
    
    /**
     * @return Returns the isSnapshotPublicationOn.
     */
    public boolean isSnapshotPublicationOn() {
        return isSnapshotPublicationOn;
    }

    /**
     * @param isSnapshotPublicationOn The isSnapshotPublicationOn to set.
     */
    public void setSnapshotPublicationOn(boolean isSnapshotPublicationOn) {
        this.isSnapshotPublicationOn = isSnapshotPublicationOn;
    }

    /**
     * @return Returns the snapshotNodeDataLevel.
     */
    public SnapshotNodeDataLevelEnum getSnapshotNodeDataLevel() {
        return snapshotNodeDataLevel;
    }

    /**
     * @param snapshotNodeDataLevel The snapshotNodeDataLevel to set.
     */
    public void setSnapshotNodeDataLevel(SnapshotNodeDataLevelEnum snapshotNodeDataLevel) {
        this.snapshotNodeDataLevel = snapshotNodeDataLevel;
    }

    /**
     * @return Returns the snapshotOutputFormat.
     */
    public String getSnapshotOutputFormat() {
        return snapshotOutputFormat;
    }

    /**
     * @param snapshotOutputFormat The snapshotOutputFormat to set.
     */
    public void setSnapshotOutputFormat(String snapshotOutputFormat) {
        this.snapshotOutputFormat = snapshotOutputFormat;
    }

    /**
     * @return Returns the snapshotThreadDataLevel.
     */
    public String getSnapshotThreadDataLevel() {
        return snapshotThreadDataLevel;
    }
    
    /**
     * @param snapshotThreadDataLevel The snapshotThreadDataLevel to set.
     */
    public void setSnapshotThreadDataLevel(String snapshotThreadDataLevel) {
        this.snapshotThreadDataLevel = snapshotThreadDataLevel;
    }

    /**
     * @return Xml representation of the command that 
     * would give out the whole broker topology.
     */
    public String toTopologyXml(){
        StringBuffer xml = new StringBuffer(320);
        xml.append(BROKER_START)
            .append(UUID).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(broker.getBrokerUuid()).append(StringConstants.SINGLE_QUOTE)
            .append(StringConstants.SPACE).append(LABEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(broker.getBrokerName()).append(StringConstants.SINGLE_QUOTE)
            .append(StringConstants.SPACE).append(VERSION).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(broker.getBrokerVersion()).append(StringConstants.SINGLE_QUOTE)
            .append(">");
        
            if(execGroup == null){
            xml.append(REPORT_START)
                .append(ALL_EXEC_GROUPS_EMPTY)
            .append(REPORT_END);
            }
            else{
                xml.append(EXEC_GROUP_START)
                    .append(UUID).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(execGroup.getExecGroupUuid()).append(StringConstants.SINGLE_QUOTE)
                    .append(StringConstants.SPACE).append(LABEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(execGroup.getExecGroupName()).append(StringConstants.SINGLE_QUOTE)
                    .append(">")
                    .append(REPORT_START)
                        .append(ALL_MESSAGE_FLOWS_EMPTY)
                    .append(REPORT_END)
                .append(EXEC_GROUP_END);
            }
            
        xml.append(BROKER_END);
        return xml.toString();
    }
    
    /**
     * @return Xml representation of the command.
     */
    public String toXml(){
        StringBuffer xml = new StringBuffer(320);
        xml.append(BROKER_START)
            .append(UUID).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(broker.getBrokerUuid()).append(StringConstants.SINGLE_QUOTE)
            .append(StringConstants.SPACE).append(LABEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(broker.getBrokerName()).append(StringConstants.SINGLE_QUOTE)
            .append(StringConstants.SPACE).append(VERSION).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(broker.getBrokerVersion()).append(StringConstants.SINGLE_QUOTE)
            .append(">");
        
        if(execGroup == null){
            /*
            <Broker uuid='04cfef48-0a01-MISHA-0080-ad4c6b86410c' label='NFX1MB' version='1'>
                <AllExecutionGroups>
                    <Change>
                        <AllMessageFlows StatsSnapPublicationOn="active" StatsSnapNodeDataLevel="basic" StatsSnapThreadDataLevel="basic" StatsSnapOutputFormat="xml"/>
                    </Change>
                </AllExecutionGroups>
            </Broker>
            */
            xml.append(ALL_EXEC_GROUPS_START)
                .append(CHANGE_START)
                    .append(ALL_MESSAGE_FLOWS_START)
                            .append(STATS_SNAP_PUBLICATION_ON).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append((isSnapshotPublicationOn ? ACTIVE : INACTIVE)).append(StringConstants.SINGLE_QUOTE)
                            .append(StringConstants.SPACE).append(STATS_SNAP_NODE_DATA_LEVEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(getSnapshotNodeDataLevel().getName()).append(StringConstants.SINGLE_QUOTE)
                            .append(StringConstants.SPACE).append(STATS_SNAP_THREAD_DATA_LEVEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(BASIC).append(StringConstants.SINGLE_QUOTE)
                            .append(StringConstants.SPACE).append(STATS_SNAP_OUTPUT_FORMAT).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(XML).append(StringConstants.SINGLE_QUOTE)
                            .append("/>")
                .append(CHANGE_END)
            .append(ALL_EXEC_GROUPS_END);
        }
        else{
            /*
            <Broker uuid='04cfef48-0a01-MISHA-0080-ad4c6b86410c' label='NFX1MB' version='1'>
                <ExecutionGroup uuid='05cfef48-0a01-0000-0080-ad4c6b86410c' label='default'>
                    <Change>
                        <AllMessageFlows StatsSnapPublicationOn="active" StatsSnapNodeDataLevel="basic" StatsSnapThreadDataLevel="basic" StatsSnapOutputFormat="xml"/>
                    </Change>
                </ExecutionGroup>
            </Broker>
            */
            xml.append(EXEC_GROUP_START)
                .append(UUID).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(execGroup.getExecGroupUuid()).append(StringConstants.SINGLE_QUOTE)
                .append(StringConstants.SPACE).append(LABEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(execGroup.getExecGroupName()).append(StringConstants.SINGLE_QUOTE)
                .append(">")
                        
                .append(CHANGE_START);
            if(flow == null){
                    xml.append(ALL_MESSAGE_FLOWS_START)
                        .append(STATS_SNAP_PUBLICATION_ON).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append((isSnapshotPublicationOn ? ACTIVE : INACTIVE)).append(StringConstants.SINGLE_QUOTE)
                        .append(StringConstants.SPACE).append(STATS_SNAP_NODE_DATA_LEVEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(getSnapshotNodeDataLevel().getName()).append(StringConstants.SINGLE_QUOTE)
                        .append(StringConstants.SPACE).append(STATS_SNAP_THREAD_DATA_LEVEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(BASIC).append(StringConstants.SINGLE_QUOTE)
                        .append(StringConstants.SPACE).append(STATS_SNAP_OUTPUT_FORMAT).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(XML).append(StringConstants.SINGLE_QUOTE)
                        .append("/>");
            }
            else{
                /*
                <MessageFlow uuid='7e2c2f6c-0a01-0000-0080-bd5b2c1703c7' label='SystemPublish' 
                    StatsSnapPublicationOn="active"
                    StatsSnapNodeDataLevel="basic"
                    StatsSnapThreadDataLevel="basic"
                    StatsSnapOutputFormat="xml"/>
                */
                    xml.append(FLOW_START)
                        .append(UUID).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(flow.getFlowName()).append(StringConstants.SINGLE_QUOTE)
                        .append(StringConstants.SPACE).append(LABEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(flow.getFlowName()).append(StringConstants.SINGLE_QUOTE)
                        .append(StringConstants.SPACE).append(STATS_SNAP_PUBLICATION_ON).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append((isSnapshotPublicationOn ? ACTIVE : INACTIVE)).append(StringConstants.SINGLE_QUOTE)
                        .append(StringConstants.SPACE).append(STATS_SNAP_NODE_DATA_LEVEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(getSnapshotNodeDataLevel().getName()).append(StringConstants.SINGLE_QUOTE)
                        .append(StringConstants.SPACE).append(STATS_SNAP_THREAD_DATA_LEVEL).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(BASIC).append(StringConstants.SINGLE_QUOTE)
                        .append(StringConstants.SPACE).append(STATS_SNAP_OUTPUT_FORMAT).append(StringConstants.EQ).append(StringConstants.SINGLE_QUOTE).append(XML).append(StringConstants.SINGLE_QUOTE)
                        .append("/>");
            }
                xml.append(CHANGE_END)
            .append(EXEC_GROUP_END);
        }
        xml.append(BROKER_END);
        return xml.toString();
    }

    /* 
     * mqsichangeflowstats DWMB10 -s -c active -g -j -n advanced -t basic -b basic -o xml
     * 
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String cmd = "mqsichangeflowstats " + broker.getBrokerName() + " -s -c " + (isSnapshotPublicationOn ? ACTIVE : INACTIVE) + " -n " + getSnapshotNodeDataLevel().getName() + " -t " + BASIC + " -b " + BASIC + " -o " + XML;
        if(execGroup == null){
            cmd += " -g -j";
        }
        else{
            cmd += " -e " + execGroup.getExecGroupName();
            if(flow == null){
                cmd += " -j";
            }
            else{
                cmd += " -f " + flow.getFlowName();
            }
        }
        return cmd;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // BROKER.
        MbMessageBroker broker = new IBMMbMessageBroker("DWMB10", "7e2c2f6c-0a01-0000-0080-bd5b2c1703c7", "1", "QUEUE.SUB", "QM01", "localhost", 1414, "CHANNEL.1", null);
        System.out.println(new IBMMbMqsiChangeFlowStats(broker, true, SnapshotNodeDataLevelEnum.NONE).toTopologyXml());
        
        MbExecGroup eg = new IBMMbExecGroup(broker, "default", "7e2c2f6c-0a01-0000-0080-bd5b2c1703c7");
        System.out.println(new IBMMbMqsiChangeFlowStats(eg, true, SnapshotNodeDataLevelEnum.NONE).toTopologyXml());
        
        System.out.println(new IBMMbMqsiChangeFlowStats(broker, true, SnapshotNodeDataLevelEnum.NONE).toXml());
        
        // EXEC GROUP.
        MbExecGroup execGroup = new IBMMbExecGroup(broker, "default", "05cfef48-0a01-0000-0080-ad4c6b86410c");
        System.out.println(new IBMMbMqsiChangeFlowStats(execGroup, true, SnapshotNodeDataLevelEnum.NONE).toXml());
        
        // FLOW.
        MbMessageFlow flow = new IBMMbMessageFlow(execGroup, "Flow1", "05cfef48-0a01-0000-0080-ad4c6b86410c");
        System.out.println(new IBMMbMqsiChangeFlowStats(flow, true, SnapshotNodeDataLevelEnum.NONE).toXml());
    }
}
