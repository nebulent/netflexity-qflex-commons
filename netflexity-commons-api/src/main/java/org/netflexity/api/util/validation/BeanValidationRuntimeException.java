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
package org.netflexity.api.util.validation;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author MAX
 *
 */
public class BeanValidationRuntimeException extends NestableRuntimeException {

    /**
     * @param message
     */
    public BeanValidationRuntimeException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param t
     */
    public BeanValidationRuntimeException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * @param arg0
     */
    public BeanValidationRuntimeException(Throwable t) {
        super(t);
    }
}
