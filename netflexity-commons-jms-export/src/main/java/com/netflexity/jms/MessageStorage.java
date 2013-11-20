package com.netflexity.jms;

import javax.jms.Message;


/**
 * 
 * Designed for separating message persistance logic from queue browsing logic 
 * that happens in {@link QueueSnapshotJob}.
 * Storage can be in three states: NOT_OPENED, OPENED, CLOSED. It is illegal to call:
 * <ul>
 * <li>openStorage when storage is in OPENED or CLOSED state</li>
 * <li>saveMessage when storage is NOT_OPENED or CLOSED</li>
 * <li>closeStorage when storage is NOT_OPENED or CLOSED</li>
 * </ul>
 * Method openStorage() should put it into OPENED state and  closeStorage() in CLOSED state.
 * 
 * @author Orient
 *
 */
public interface MessageStorage {

	public enum State {
		NOT_OPENED, OPENED, CLOSED;
	}

	void openStorage();
	
	public MessageStats saveMessage(Message message);
	
	void closeStorage();
	
	State getState();

}
