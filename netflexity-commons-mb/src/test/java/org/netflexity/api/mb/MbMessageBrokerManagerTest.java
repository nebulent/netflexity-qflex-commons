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
package org.netflexity.api.mb;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import javax.jms.BytesMessage;

import javax.jms.JMSException;
import javax.jms.Message;

import org.netflexity.api.mb.ibm.IBMMbMessageBroker;
import org.netflexity.api.mb.ibm.IBMMbMessageBrokerManagerImpl;
import org.netflexity.api.mq.MqException;
import org.netflexity.api.mq.MqQmanagerManager;
import org.netflexity.api.mq.ibm.IBMPCFMqQmanagerManager;

import org.junit.Test;
import org.netflexity.api.mb.ibm.IBMMbSubscription;
import org.netflexity.api.mb.ibm.enums.SnapshotNodeDataLevelEnum;
import org.netflexity.api.mb.ibm.enums.SubscriptionTypeEnum;
import org.netflexity.api.mq.jms.JMSMessageListener;
import org.netflexity.api.mq.jms.JMSMessageListenerContext;

/**
 * @author MAX
 *
 */
public class MbMessageBrokerManagerTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        MbMessageBroker broker = new IBMMbMessageBroker("DWMB10", "7e2c2f6c-0a01-0000-0080-bd5b2c1703c7", "1", "SYSTEM.JMS.D.QFLEX.SUB.Q3", "DWMQ10", "127.0.0.1", 1415, "QFLEX1", null);
        MqQmanagerManager manager = null;
        MbMessageBrokerManager mbManager = new IBMMbMessageBrokerManagerImpl(broker);
        try {
            manager = new IBMPCFMqQmanagerManager("DWMQ10", "127.0.0.1", 1415, "QFLEX1", null);
            broker = mbManager.getBrokerTopology(manager);

            for (Iterator iter = broker.getExecGroups().iterator(); iter.hasNext();) {
                MbExecGroup eg = (MbExecGroup) iter.next();
                System.out.println("ExecGroup=" + eg.getExecGroupName());
                for (Iterator iterator = eg.getMessageFlows().iterator(); iterator.hasNext();) {
                    MbMessageFlow f = (MbMessageFlow) iterator.next();
                    System.out.println("Flow=" + f.getFlowName());
                }
            }
        } catch (MqException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (manager != null) {
                try {
                    manager.close();
                } catch (MqException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void brokerStatisticsTest() {
        String brokerName = "NF01BKR";
        String qmanagerName = "NF01";
        String qmanagerHost = "75.101.144.188";
        int qmanagerPort = 1414;
        String channelName = "QFLEX01.SVRCONN";
        String brokerDurSubQueueName = "SYSTEM.JMS.D.QFLEX01.QUEUE";

        MbMessageBroker broker = new IBMMbMessageBroker(
                brokerName,
                "24d898f5-4537-4dd1-a8ec-efb3edf66d44",
                "1",
                brokerDurSubQueueName,
                qmanagerName,
                qmanagerHost,
                qmanagerPort,
                channelName,
		null
	);
        System.out.println("broker created: " + brokerName + "(" + qmanagerHost + ":" + qmanagerPort + ")");
        MqQmanagerManager manager = null;
        MbMessageBrokerManager mbManager = new IBMMbMessageBrokerManagerImpl(broker);

        try {
            manager = new IBMPCFMqQmanagerManager(
                    qmanagerName,
                    qmanagerHost,
                    qmanagerPort,
                    channelName,
                    null);
            System.out.println("Qmanager created.");
            MbSubscription subscription = new IBMMbSubscription(
                    broker,
                    SubscriptionTypeEnum.BROKER_TYPE,
                    SnapshotNodeDataLevelEnum.NONE,
                    "4654892801085709312");

            final JMSMessageListenerContext context = new JMSMessageListenerContext();

            JMSMessageListener listener = new JMSMessageListener() {

                protected boolean completed;

                public boolean isCompleted() {
                    return completed;
                }

                public JMSMessageListenerContext getContext() {
                    return context;
                }

                public void onMessage(Message msg) {
                    if (msg instanceof BytesMessage) {
                        BytesMessage bytesMsg = (BytesMessage) msg;
                        try {
                            String xmlSource = toString(bytesMsg);
                            System.out.println(xmlSource);
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }

                protected String toString(BytesMessage bytesMsg) throws JMSException {
                    int n = -1;
                    byte[] bytes = new byte[1024];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
                    while ((n = bytesMsg.readBytes(bytes, bytes.length)) != -1) {
                        baos.write(bytes, 0, n);
                    }
                    return baos.toString();
                }
            };

            mbManager.enableFlowStats(subscription, listener, manager);

            System.out.println("--------------- waiting for messages for 2 minutes ---------------");
            Thread.sleep(2 * 1000 * 60);
            System.out.println("---------------     completed     ---------------");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MqException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (manager != null) {
                try {
                    manager.close();
                } catch (MqException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
