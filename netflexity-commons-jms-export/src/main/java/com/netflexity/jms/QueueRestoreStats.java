package com.netflexity.jms;


/**
 * 
 * This class keeps statistics of the current snapshot
 * 
 * @author Orient
 *
 */
public class QueueRestoreStats {
	
	long start;
	
	long end;
	
	int messageCount;
	//TODO: I think we dont need statistics for each message
	//List<MessageStats> overallStats = new ArrayList<MessageStats>();
	
	public void markSnapshotStat() {
		start = System.currentTimeMillis();
	}
	
	protected void addMessageStats(MessageStats stats) {
		end = System.currentTimeMillis();
		messageCount++;
		//overallStats.add(stats);
	}
	
	public long getTotalTimeMillis() {
		if (end == 0) {
			return -1;
		}
		return end - start;
	}
	
	public int getProcessedMessageCount() {
		return messageCount;
		//return overallStats.size();
	}
}
