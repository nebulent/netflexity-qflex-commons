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

import org.apache.commons.lang.exception.NestableException;

/**
 * @author Max Fedorov
 *
 */
public class CommandException extends NestableException {

    /**
     * @param m
     * @param t
     */
    public CommandException(String m, Throwable t) {
        super(m, t);
    }

    /**
     * @param m
     */
    public CommandException(String m) {
        super(m);
    }

    /**
     * @param t
     */
    public CommandException(Throwable t) {
        super(t);
    }
}
