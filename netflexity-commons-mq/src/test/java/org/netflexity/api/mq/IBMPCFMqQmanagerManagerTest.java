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
package org.netflexity.api.mq;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.netflexity.api.mq.ibm.IBMPCFMqQmanagerManager;

import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
import java.util.UUID;
import org.netflexity.api.mq.ibm.IBMMqMessage;
import org.netflexity.api.mq.ibm.IBMMqMessageHeader;

public class IBMPCFMqQmanagerManagerTest extends PCFMessageAgent {

    private IBMPCFMqQmanagerManager manager;
    protected MQQueueManager connection;
    // private String qManagerName = "LD01";
    // private String hostName = "ec2-75-101-185-250.compute-1.amazonaws.com";
    // private String channelName = "SYSTEM.DEF.SVRCONN";
    // private String hostName = "ec2-75-101-131-40.compute-1.amazonaws.com"; //75.101.131.40";
    // private String hostName = "184.73.219.202";
    // private String qManagerName = "FP01";
    // private String channelName = "SYSTEM.DEF.SVRCONN";
    private String hostName = "mturk.nebulent.com";
    private String qManagerName = "FP02";
    private String channelName = "SYSTEM.DEF.SVRCONN";
    private int port = 1415;
    private String queueName = "SYSTEM.ADMIN.CONFIG.EVENT";

    @Before
    public void setUp() {
	try {
	    System.out.println("setUp");
	    // manager = new IBMPCFMqQmanagerManager("LD02", "127.0.0.1", 1415, "SYSTEM.DEF.SVRCONN", null);
	    manager = new IBMPCFMqQmanagerManager(qManagerName, hostName, port, channelName, null);
	    connection = new MQQueueManager(qManagerName);
	    System.out.println("setup ok");
	} catch (Throwable e) {
	    System.out.println("setup failed");
	    e.printStackTrace();
	    Assert.assertTrue(e.getMessage(), false);
	}
    }

    @After
    public void tearDown() {
	try {
	    manager.close();
	} catch (MqException e) {
	    Assert.assertTrue(e.getMessage(), false);
	}
    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMPCFMqQmanagerManager.getAllQueues()'
     */
    // @Test
    public void testGetPermissions() {
	try {
	    List permissions = manager.getPermissions();
	    for (Iterator iter = permissions.iterator(); iter.hasNext();) {
		// MqQueue q = (MqQueue) iter.next();
		// System.out.println(q);
	    }
	} catch (MqException e) {
	    System.err.println(e);
	    Assert.assertTrue(e.getMessage(), false);
	}
    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMPCFMqQmanagerManager.getAllQueues()'
     */
    // @Test
    public void testGetAllQueues() {
	try {
	    List queues = manager.getAllQueues();
	    for (Iterator iter = queues.iterator(); iter.hasNext();) {
		MqQueue q = (MqQueue) iter.next();
		System.out.println(q);
	    }
	} catch (MqException e) {
	    System.err.println(e);
	    Assert.assertTrue(e.getMessage(), false);
	}
    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMPCFMqQmanagerManager.getAllChannels()'
     */

    /*
     * After discovering and connecting to one queue manager, many new queue
     * managers can be quickly discovered by scanning sender channels of just
     * added queue manager. Call getchannel on all sender channels and lookup
     * hostname port parameters. Use those parameters in combination with
     * frequently used list of channel names to try to connect to the queue
     * managers.
     * Once all queue managers connected, a chart should be created where all
     * queue managers are drawn and connected using lines.
     */
    // @Test
    public void getAllChannels() {
	System.out.println("<getAllChannels>");

	try {
	    // List<MqChannel> channels = filterChannels(manager);
	    List<MqChannel> channels = getChannels(manager);
	} catch (MqException e) {
	    Assert.assertTrue(e.getMessage(), false);
	}
    }

    // @Test
    public void getAllTopics() {
	System.out.println("<getAllTopics>");

	try {
	    List<MqTopic> topics = manager.getAllTopics();
	    if (topics != null) {
		for (MqTopic topic : topics) {
		    System.out.println(topic.getTopicName() + " [" + topic.getTopicStatus() + "] : "
			    + topic.getTopicDesc());
		}
	    }
	} catch (MqException e) {
	    Assert.assertTrue(e.getMessage(), false);
	}
    }

    // @Test
    public void getTopology() {
	System.out.println("<getTopology>");

	try {
	    // List<MqChannel> channels = filterChannels(manager);
	    List<MqChannel> channels = manager.getTopology();
	    for (MqChannel ch : channels) {
		// if(ch.getChannelType() == 1){
		describeChannel(ch);
		// }
	    }
	} catch (MqException e) {
	    Assert.assertTrue(e.getMessage(), false);
	}
    }

    private List<MqChannel> getChannels(IBMPCFMqQmanagerManager mng) throws MqException {
	List<MqChannel> channels = mng.getAllChannels();
	if (channels != null) {

	    for (MqChannel ch : channels) {
		// if(ch.getChannelType() == 1){
		describeChannel(ch);
		// }
	    }
	}
	return channels;
    }

    private void describeChannel(MqChannel ch) {
	System.out.println("----------------------------------------------------------------------");
	System.out.println("server channel : " + ch.getChannelName());
	// System.out.println("getAlterationDate :\t" + ch.getAlterationDate());
	// System.out.println("getAlterationTime: \t" + ch.getAlterationTime());
	// System.out.println("getBatchInterval: \t" + ch.getBatchInterval());
	// System.out.println("getBatchSize: \t" + ch.getBatchSize());
	// System.out.println("getChannelDesc: \t" + ch.getChannelDesc());
	// System.out.println("getChannelInstanceType: \t" + ch.getChannelInstanceType());
	// System.out.println("getChannelName: \t" + ch.getChannelName());
	// System.out.println("getChannelStartDate: \t" + ch.getChannelStartDate());
	// System.out.println("getChannelStartTime: \t" + ch.getChannelStartTime());
	// System.out.println("getChannelStatus: \t" + ch.getChannelStatus());
	// System.out.println("getChannelType: \t" + ch.getChannelType());
	// System.out.println("getClusterName: \t" + ch.getClusterName());
	// System.out.println("getClusterNamelist: \t" + ch.getClusterNamelist());
	// System.out.println("getConnectionCount: \t" + ch.getConnectionCount());
	System.out.println("getConnectionName: \t" + ch.getConnectionName());
	// System.out.println("getDataConversion: \t" + ch.getDataConversion());
	// System.out.println("getDiscInterval: \t" + ch.getDiscInterval());
	// System.out.println("getHeartbeatInterval: \t" + ch.getHeartbeatInterval());
	// System.out.println("getLocalAddress: \t" + ch.getLocalAddress());
	// System.out.println("getLongRetryCount: \t" + ch.getLongRetryCount());
	// System.out.println("getLongRetryInterval: \t" + ch.getLongRetryInterval());
	// System.out.println("getMaxMsgLength: \t" + ch.getMaxMsgLength());
	// System.out.println("getMcaName: \t" + ch.getMcaName());
	// System.out.println("getMcaType: \t" + ch.getMcaType());
	// System.out.println("getMcaUserIdentifier: \t" + ch.getMcaUserIdentifier());
	// System.out.println("getModeName: \t" + ch.getModeName());
	// System.out.println("getMsgExit: \t" + ch.getMsgExit());
	// System.out.println("getMsgRetryCount: \t" + ch.getMsgRetryCount());
	// System.out.println("getMsgRetryExit: \t" + ch.getMsgRetryExit());
	// System.out.println("getMsgRetryInterval: \t" + ch.getMsgRetryInterval());
	// System.out.println("getMsgRetryUserData: \t" + ch.getMsgRetryUserData());
	// System.out.println("getMsgUserData: \t" + ch.getMsgUserData());
	// System.out.println("getNetworkPriority: \t" + ch.getNetworkPriority());
	// System.out.println("getNonPersistentMsgSpeed: \t" + ch.getNonPersistentMsgSpeed());
	// System.out.println("getPassword: \t" + ch.getPassword());
	// System.out.println("getQmanagerName: \t" + ch.getQmanagerName());
	// System.out.println("getReceiveExit: \t" + ch.getReceiveExit());
	// System.out.println("getReceiveUserData: \t" + ch.getReceiveUserData());
	// System.out.println("getSecurityExit: \t" + ch.getSecurityExit());
	// System.out.println("getSecurityUserData: \t" + ch.getSecurityUserData());
	// System.out.println("getSendExit: \t" + ch.getSendExit());
	// System.out.println("getSendUserData: \t" + ch.getSendUserData());
	// System.out.println("getSeqNumberWrap: \t" + ch.getSeqNumberWrap());
	// System.out.println("getShortRetryCount: \t" + ch.getShortRetryCount());
	// System.out.println("getShortRetryInterval: \t" + ch.getShortRetryInterval());
	// System.out.println("getSslCipherSpec: \t" + ch.getSslCipherSpec());
	// System.out.println("getSslClientAuth: \t" + ch.getSslClientAuth());
	// System.out.println("getSslPeerName: \t" + ch.getSslPeerName());
	// System.out.println("getTpName: \t" + ch.getTpName());
	// System.out.println("getTransportType: \t" + ch.getTransportType());
	// System.out.println("getUserIdentifier: \t" + ch.getUserIdentifier());
	// System.out.println("getXmitQueueName: \t" + ch.getXmitQueueName());
	System.out.println("----------------------------------------------------------------------");
    }

    private List<MqChannel> filterChannels(IBMPCFMqQmanagerManager mng) throws MqException {
	System.out.println("--- getting topology for: " + mng.getHost() + ":" + mng.getPort() + " "
		+ mng.getQmanagerName() + " / " + mng.getChannelName());
	List<MqChannel> channels = mng.getTopology();
	List<MqChannel> rv = new ArrayList<MqChannel>();
	if (channels != null) {
	    System.out.println("Found: " + channels.size() + " channel(s)");
	    for (MqChannel ch : channels) {
		int channelType = ch.getChannelType().intValue();
		if (channelType == 2) {
		    rv.add(ch);
		    System.out.println("adding channel: " + ch.getChannelName());

		    IBMPCFMqQmanagerManager m = new IBMPCFMqQmanagerManager(qManagerName, hostName, port,
			    ch.getChannelName(), null);

		    filterChannels(m);
		}
	    }
	}
	System.out.println("Filtered: " + rv.size() + " channel(s)");
	return rv;
    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMPCFMqQmanagerManager.getQueueStats(String[], String[])'
     */
    // @Test
    public void testGetQueueStats() {
	try {
	    List qstats = manager.getQueueStats(null, null);
	    for (Iterator iter = qstats.iterator(); iter.hasNext();) {
		MqQueueStat qs = (MqQueueStat) iter.next();
		System.out.println(qs);
	    }
	} catch (MqException e) {
	    Assert.assertTrue(e.getMessage(), false);
	}
    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMPCFMqQmanagerManager.getQueue(String)'
     */
//    @Test
    public void testGetQueue() {
	//XXX
	try {
	    IBMPCFMqQmanagerManager mngr = new IBMPCFMqQmanagerManager(qManagerName, hostName, port, channelName, null);
	    MqQueue q = manager.getQueue("SYSTEM.ADMIN.CONFIG.EVENT");

	    List<MQMessage> list = mngr.getConfigChangeEventMessages();

	    PCFMessage previousMsg = null;

	    for (MQMessage msg : list) {
		try {

//				    MQCFH header = new MQCFH(
//					    new DataInputStream(
//						new ByteArrayInputStream(msg.getData().getBytes("UTF-8"))
//					    )
//				    );
//				    int reason = header.getReason();




		    PCFMessage pcf = new PCFMessage(msg);

		    System.out.println("-----------------------------------");

		    String qm = pcf.getStringParameterValue(CMQCFC.MQCACF_EVENT_Q_MGR);
		    System.out.println("QManager: " + qm);

		    int reason = pcf.getReason();
		    switch (reason) {
			case CMQC.MQRC_CONFIG_CREATE_OBJECT:
//						case 2367:
			    System.out.print("Create");
			    break;
//						case 2368:
			case CMQC.MQRC_CONFIG_CHANGE_OBJECT:
			    System.out.print("Change " + (pcf.getControl() == CMQCFC.MQCFC_LAST ? "(end) ": "(init) "));
			    break;
//						case 2369:
			case CMQC.MQRC_CONFIG_DELETE_OBJECT:
			    System.out.print("Delete");
			    break;
//						case 2370:
			case CMQC.MQRC_CONFIG_REFRESH_OBJECT:
			    System.out.print("Refresh");
			    break;
		    }



		    int objectType = pcf.getIntParameterValue(CMQCFC.MQIACF_OBJECT_TYPE);
//		    System.out.print("Object type: " + objectType);

		    String objectName = null;

		    System.out.print(" ");

		    switch (objectType) {
			case CMQC.MQOT_CHANNEL:
			    System.out.print("Channel");
			    objectName = pcf.getStringParameterValue(CMQCFC.MQCACH_CHANNEL_NAME);
			    break;
			case CMQC.MQOT_Q:
			    System.out.print("Queue");
			    objectName = pcf.getStringParameterValue(CMQC.MQCA_Q_NAME);
			    break;
			case CMQC.MQOT_TOPIC:
			    System.out.print("Topic");
			    objectName = pcf.getStringParameterValue(CMQC.MQCA_TOPIC_NAME);
			    break;
			case CMQC.MQOT_Q_MGR:
			    System.out.print("QManager");
			    objectName = pcf.getStringParameterValue(CMQC.MQCA_Q_MGR_NAME);
			    break;

		    }

		    System.out.println(" : " + objectName);
		    String alterationDate = pcf.getStringParameterValue(CMQC.MQCA_ALTERATION_DATE);
		    System.out.println("Alteration date: " + alterationDate);
		    String alterationTime = pcf.getStringParameterValue(CMQC.MQCA_ALTERATION_TIME);
		    System.out.println("Alteration time: " + alterationTime);
		    System.out.println("-----------------------------------");

//		    if(reason == CMQC.MQRC_CONFIG_CHANGE_OBJECT){
//			if(pcf.getControl() == CMQCFC.MQCFC_NOT_LAST){
//			    previousMsg = pcf;
//			}else if(pcf.getControl() == CMQCFC.MQCFC_LAST){
//			    compareMessages(previousMsg, pcf);
//			}
//		    }

		} catch (MQException mde) {
		    mde.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	} catch (MqException e) {
	    e.printStackTrace();
	    Assert.assertTrue(e.getMessage(), false);
	}
    }

//    @Test
//    public void testDoubleCall(){
//        testBrowseMarkedMessages();
//	testBrowseMarkedMessages();
//    }

    
//    public void testBrowseMarkedMessages(){
//    	System.out.println("-----------------------------------------------");
//	System.out.println("     executing test queue message browsing");
//	System.out.println("-----------------------------------------------");
//
//	try{
//	    IBMPCFMqQmanagerManager mgr =  new IBMPCFMqQmanagerManager(qManagerName, hostName, port, channelName, null);
//	    if (mgr.isOpen() && mgr.isCommandServerOpen()) {
//		List<MQMessage> messages = mgr.browseAndMarkQueueMessages("TEST.333", 0);
//		if(messages != null){
//		    System.out.println("*** Total entries: " + messages.size());
//		}else{
//		    System.out.println("no messages found");
//		}
//	    }else{
//		System.out.println("QMAnager is closed");
//	    }
//	}catch(Exception e){
//	    System.out.println("Shit happened");
//	    e.printStackTrace();
//	}
//
//	System.out.println("-----------------------------------------------");
//	System.out.println("     complete");
//	System.out.println("-----------------------------------------------");
//    }

//@Test
    public void fillQueue() {

	try {
//	    IBMPCFMqQmanagerManager mngr = new IBMPCFMqQmanagerManager(qManagerName, hostName, port, channelName, null);
	    for (int i = 0; i < 4000; i++) {
		IBMMqMessage message = new IBMMqMessage();
		message.setData("Messages " + i);
		message.setMessageHeader(new IBMMqMessageHeader());
		System.out.println("put " + message.getData());
		manager.putMessage("TEST.333", message);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMPCFMqQmanagerManager.getChannel(String)'
     */
    // @Test
    public void testGetChannel() {
    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMPCFMqQmanagerManager.getQmanager()'
     */
    // @Test
    public void testGetQmanager() {
    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMPCFMqQmanagerManager.setChannel(MqChannel)'
     */
    // @Test
    public void testSetChannel() {
    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMPCFMqQmanagerManager.setQmanager(MqQmanager)'
     */
    // @Test
    public void testSetQmanager() {
    }

    /*
     * Test method for 'org.netflexity.api.mq.impl.IBMPCFMqQmanagerManager.setQueue(MqQueue)'
     */
    // @Test
    public void testSetQueue() {
    }
}
