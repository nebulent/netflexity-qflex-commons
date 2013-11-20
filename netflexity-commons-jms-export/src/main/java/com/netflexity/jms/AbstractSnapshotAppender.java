package com.netflexity.jms;

/**
 * 
 * Redirects calls from other methods to {@link SnapshotAppender#append(String)} 
 * 
 * @author Orient
 *
 */
public abstract class AbstractSnapshotAppender implements SnapshotAppender {

	
	public SnapshotAppender append(short data) {
		return append(String.valueOf(data));
	}

	public SnapshotAppender append(int data) {
		return append(String.valueOf(data));
	}

	public SnapshotAppender append(long data) {
		return append(String.valueOf(data));
	}

	public SnapshotAppender append(byte data) {
		return append(String.valueOf(data));
	}

	public SnapshotAppender append(float data) {
		return append(String.valueOf(data));
	}

	public SnapshotAppender append(double data) {
		return append(String.valueOf(data));
	}

	public SnapshotAppender append(Object data) {
		return append(data.toString());
	}

	public SnapshotAppender append(boolean data) {
		return append(String.valueOf(data));
	}

	public SnapshotAppender append(char data) {
		return append(String.valueOf(data));
	}

}
