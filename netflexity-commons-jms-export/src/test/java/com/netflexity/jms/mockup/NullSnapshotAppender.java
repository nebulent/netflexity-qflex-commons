package com.netflexity.jms.mockup;

import com.netflexity.jms.AbstractSnapshotAppender;
import com.netflexity.jms.SnapshotAppender;

public class NullSnapshotAppender extends AbstractSnapshotAppender {
	
	
	
	public NullSnapshotAppender() {
		
	}
	
	public SnapshotAppender append(String data) {
		return this;
	}

}
