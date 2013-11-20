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

import org.apache.commons.lang.exception.NestableException;

/**
 * @author MAX
 *
 */
public class MqException extends NestableException {

    private int errorCode = 0;
    
    /**
     * 
     */
    public MqException() {
        super();
    }

    /**
     * @param msg
     */
    public MqException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param t
     */
    public MqException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * @param t
     */
    public MqException(Throwable t) {
        super(t);
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
