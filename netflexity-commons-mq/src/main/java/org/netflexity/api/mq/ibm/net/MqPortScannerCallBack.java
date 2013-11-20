package org.netflexity.api.mq.ibm.net;

import java.util.List;

import org.netflexity.api.util.net.PortScannerCallback;

public interface MqPortScannerCallBack extends PortScannerCallback {
	
	List<MqQmanagerAddress> getAddresses();
}
