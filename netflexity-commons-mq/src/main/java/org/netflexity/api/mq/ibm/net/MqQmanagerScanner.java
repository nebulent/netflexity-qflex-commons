package org.netflexity.api.mq.ibm.net;

import java.net.InetAddress;
import java.util.List;

import org.netflexity.api.util.net.PortScanner;

public class MqQmanagerScanner {
	
	private MqPortScannerCallBack scannerCallback;
	
	/**
	 * @param address
	 * @param fromPort
	 * @param toPort
	 * @return
	 */
	public List<MqQmanagerAddress> scan(InetAddress address, int fromPort, int toPort) {
		if (scannerCallback == null) {
			throw new IllegalStateException("Scanner callback is not specified");
		}
		PortScanner scanner = new PortScanner(fromPort, toPort);
		scanner.scan(address, scannerCallback);
		return scannerCallback.getAddresses();
	}
		
	/**
	 * @return
	 */
	public MqPortScannerCallBack getScannerCallback() {
		return scannerCallback;
	}

	/**
	 * @param scannerCallback
	 */
	public void setScannerCallback(MqPortScannerCallBack scannerCallback) {
		this.scannerCallback = scannerCallback;
	}
}
