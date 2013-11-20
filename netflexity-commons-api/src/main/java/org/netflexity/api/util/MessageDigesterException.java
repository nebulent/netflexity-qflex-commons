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
package org.netflexity.api.util;

import org.apache.commons.lang.exception.NestableException;

/**
 * @author MAX
 *
 */
public class MessageDigesterException extends NestableException {

    /**
     * @param message
     */
    public MessageDigesterException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param t
     */
    public MessageDigesterException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * @param t
     */
    public MessageDigesterException(Throwable t) {
        super(t);
    }

}
