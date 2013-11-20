package com.netflexity.jms;

import com.netflexity.jms.util.XmlMessageConvertor;

/**
 * 
 * This interface is required for {@link MessageStorage} and {@link XmlMessageConvertor} 
 * in order to separate message conversion logic from result aggregation in some storage
 * 
 * @author Orient
 *
 */
public interface SnapshotAppender {
	
	SnapshotAppender append(String data);
	
	SnapshotAppender append(short data);
	
	SnapshotAppender append(int data);
	
	SnapshotAppender append(long data);
	
	SnapshotAppender append(byte data);
	
	SnapshotAppender append(float data);
	
	SnapshotAppender append(double data);
	
	SnapshotAppender append(Object data);
	
	SnapshotAppender append(boolean data);
	
	SnapshotAppender append(char data);
	
	
	
}
