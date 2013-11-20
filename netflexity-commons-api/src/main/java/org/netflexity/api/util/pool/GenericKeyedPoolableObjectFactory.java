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
package org.netflexity.api.util.pool;

import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedPoolableObjectFactory;

/**
 * @author MAX
 *
 * Keyed object factory that takes a keyed pool.
 */
public interface GenericKeyedPoolableObjectFactory extends KeyedPoolableObjectFactory {

    /**
     * @return
     */
    public KeyedObjectPool getKeyedObjectPool();
    
    /**
     * @param pool
     */
    public void setKeyedObjectPool(KeyedObjectPool pool);
}
