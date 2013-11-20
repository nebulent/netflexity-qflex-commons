package org.netflexity.api.util.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PortScanner {

	private static Logger logger = LoggerFactory.getLogger(PortScanner.class);
	
	private int from, to;
	
	/**
	 * @param from
	 * @param to
	 */
	public PortScanner(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	/**
	 * @param address
	 * @param callback
	 */
	public void scan(InetAddress address, PortScannerCallback callback) {
		for (int port = from; port <= to; port++) {
			Socket socket = null;
			try {
				logger.debug("Scaning " + address.getHostName() + ":" + port);
				socket = new Socket(address, port);
				logger.debug("Connection established");
				if(callback != null) {
					callback.doWithSocket(socket);
				}
			} 
			catch (IOException e) {
				//do nothing here since this is normal event when socket is closed
			} 
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			} 
			finally {
				try {
					if (socket != null){
						socket.close();
					}
				} 
				catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * @param range
	 * @param callback
	 */
	public void scan(IPAddressRange range, PortScannerCallback callback) {
		for (InetAddress address : range) {
			scan(address, callback);
		}
	}
}
