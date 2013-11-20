/*
 *  2005 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.command;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author MAX
 *
 */
public class RuntimeCommandException extends NestableRuntimeException {

    /**
     * @param message
     */
    public RuntimeCommandException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param t
     */
    public RuntimeCommandException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * @param t
     */
    public RuntimeCommandException(Throwable t) {
        super(t);
    }

}
