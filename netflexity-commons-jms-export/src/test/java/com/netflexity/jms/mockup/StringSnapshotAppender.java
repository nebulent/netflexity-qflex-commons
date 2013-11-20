package com.netflexity.jms.mockup;

import com.netflexity.jms.AbstractSnapshotAppender;
import com.netflexity.jms.SnapshotAppender;

public class StringSnapshotAppender extends AbstractSnapshotAppender {
	
	
	StringBuilder builder = new StringBuilder();
	
	public StringSnapshotAppender() {
		
	}
	
	public SnapshotAppender append(String data) {
		builder.append(data);
		//System.out.println("Append: " + data);
		return this;
	}

	public String getData() {
		return builder.toString();
	}
}
