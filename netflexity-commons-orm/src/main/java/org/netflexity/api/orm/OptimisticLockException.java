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
package org.netflexity.api.orm;

import org.netflexity.api.command.CommandException;

/**
 * @author MAX
 *
 */
public class OptimisticLockException extends CommandException {

    public OptimisticLockException() {
	super("Optimistic lock exception.");
    }

    /**
     * @param m
     * @param t
     */
    public OptimisticLockException(String m, Throwable t) {
	super(m, t);
    }

    /**
     * @param m
     */
    public OptimisticLockException(String m) {
	super(m);
    }
}
