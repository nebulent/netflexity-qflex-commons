package org.netflexity.api.mq.ibm.net;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;

/**
 * @author netflexity
 *
 */
public class ScientificPortScannerCallback implements MqPortScannerCallBack {

    private final static String[] ALL_CHANNELS_TO_BE_TESTED = {
        "SYSTEM.DEF.SVRCONN",
        "SYSTEM.AUTO.SVRCONN",
        "SYSTEM.ADMIN.SVRCONN",
        // User defined Channels
        "ADMIN.SVRCONN",
        "SSL.SVRCONN",
        "TEST.SVRCONN",
        "TST.SVRCONN",
        "DEV.SVRCONN",
        "QA.SVRCONN",
        "PROD.SVRCONN",
        "SVRCONN.CHL",
        "ADMIN.SVRCONN.CHL",
        "SSL.SVRCONN.CHL",
        "TEST.SVRCONN.CHL",
        "TST.SVRCONN.CHL",
        "DEV.SVRCONN.CHL",
        "QA.SVRCONN.CHL",
        "PROD.SVRCONN.CHL"
    };
    private List<MqQmanagerAddress> addresses = new ArrayList<MqQmanagerAddress>();

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.ibm.net.MqPortScannerCallBack#getAddresses()
     */
    @Override
    public List<MqQmanagerAddress> getAddresses() {
        return addresses;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.net.PortScannerCallback#doWithSocket(java.net.Socket)
     */
    @Override
    public void doWithSocket(Socket socket) {
//        for (int j = 0; j < ALL_CHANNELS_TO_BE_TESTED.length; j++) {
	    for(String channelName : ALL_CHANNELS_TO_BE_TESTED){
//            String channelName = ALL_CHANNELS_TO_BE_TESTED[j];
            String qmanagerName = testConn(channelName, socket.getInetAddress().getHostName(), socket.getPort());
            if ("?".equals(qmanagerName)) {
                // Found something.
            } else if (qmanagerName != null) {
                addresses.add(new MqQmanagerAddress(socket.getInetAddress(), socket.getPort(), qmanagerName, channelName));
                return;
            } else {
                // No queue manager.
                return;
            }
        }
    }

    /**
     * Attempt to connect to a queue manager. If successful, output the info and disconnect.
     *
     * @param channelName Channel Name
     * @param hostname host name or IP address
     * @param port port number
     */
    private String testConn(String channelName, String hostname, int port) {
        MQQueueManager qmanager = null;
        try {
            MQEnvironment.channel = channelName;
            MQEnvironment.hostname = hostname;
            MQEnvironment.port = port;
            qmanager = new MQQueueManager("");
            return qmanager.getAttributeString(CMQC.MQCA_Q_MGR_NAME, 48).trim();
        } catch (MQException e) {
            // logger.debug(dateFormat.format(new java.util.Date()) + " : " + "Completion Code = " + e.completionCode + " : Reason Code = " + e.reasonCode);
            if (e.reasonCode == MQConstants.MQRC_Q_MGR_NOT_AVAILABLE) {
                return null;
            } else if ((e.reasonCode == MQConstants.MQRC_CONNECTION_BROKEN)
                    || (e.reasonCode == MQConstants.MQRC_NOT_AUTHORIZED)
                    || (e.reasonCode == MQConstants.MQRC_SECURITY_ERROR)) {
                return "?";
            } else {
                return null;
            }
        } finally {
            try {
                if (qmanager != null) {
                    qmanager.disconnect();
                }
            } catch (MQException e) {
            }
        }
    }
}
