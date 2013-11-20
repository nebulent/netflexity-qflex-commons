/*
 *  2006 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.mq.ibm.pool;

import org.apache.commons.pool.KeyedObjectPool;
import org.netflexity.api.mq.MqException;

public interface IBMMqConnectionPool extends KeyedObjectPool{

    /**
     * @param key
     * @return
     * @throws MqException
     */
    Object borrowMqConnection(PoolableKey key) throws MqException;

    /**
     * @param key
     * @param obj
     * @throws MqException
     */
    void returnMqConnection(PoolableKey key, Object obj) throws MqException;
}