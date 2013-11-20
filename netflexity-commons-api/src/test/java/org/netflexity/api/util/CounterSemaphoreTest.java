package org.netflexity.api.util;

import org.netflexity.api.thread.CounterSemaphore;

public class CounterSemaphoreTest extends Thread{

	private CounterSemaphore semaphore;
	
	/**
	 * @param semaphore
	 */
	public CounterSemaphoreTest(CounterSemaphore semaphore) {
		this.semaphore = semaphore;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		semaphore.lock();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Executed!");
		semaphore.unlock();
	}


	public static void main(String[] args) {
		CounterSemaphore semaphore = new CounterSemaphore(1);
		CounterSemaphoreTest sd1 = new CounterSemaphoreTest(semaphore);
		sd1.start();
		CounterSemaphoreTest sd2 = new CounterSemaphoreTest(semaphore);
		sd2.start();
		CounterSemaphoreTest sd3 = new CounterSemaphoreTest(semaphore);
		sd3.start();
	}
}
