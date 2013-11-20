package org.netflexity.api.mq.ibm.pool;

import org.apache.commons.pool.KeyedObjectPool;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

/**
 * @author Max Fedorov
 *
 * Factory interface for creating MQ connection.
 */
public interface IBMMqConnectionFactory {

	/**
	 * Create implementation specific MQ connection.
	 * 
	 * @param poolKey
	 * @param pool
	 * @return
	 * @throws MQException
	 */
	MQQueueManager createMqConnection(PoolableKey poolKey, KeyedObjectPool pool) throws MQException;
}
