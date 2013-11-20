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
package org.netflexity.api.mq;

import java.io.Serializable;

public interface MqQmanagerManagerFactory extends Serializable{

    /**
     * @param qmanager
     * @return
     */
    public MqQmanagerManager createMqQmanagerManager(MqQmanagerManagerContext context) throws MqException;

}