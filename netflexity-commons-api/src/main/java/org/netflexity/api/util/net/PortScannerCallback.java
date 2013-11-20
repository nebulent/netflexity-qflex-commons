package org.netflexity.api.util.net;

import java.net.Socket;

public interface PortScannerCallback {
	/**
	 * @param socket
	 */
	public void doWithSocket(Socket socket);
}
