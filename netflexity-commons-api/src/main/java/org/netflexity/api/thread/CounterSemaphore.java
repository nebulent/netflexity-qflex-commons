package org.netflexity.api.thread;

/**
 * @author MAX
 * 
 */
public class CounterSemaphore {
	
	private int value;

	/**
	 * @param value
	 */
	public CounterSemaphore(int value) {
		this.value = value;
	}

	/**
	 * Down lock.
	 */
	public synchronized void lock() {
		--value;
		if (value < 0) {
			try {
				//System.out.println("waiting..");
				wait();
			} catch (Exception e) {
			}
			//System.out.println("awaken..");
		}
	}

	/**
	 * Up lock.
	 */
	public synchronized void unlock() {
		++value;
		notify();
	}
}
