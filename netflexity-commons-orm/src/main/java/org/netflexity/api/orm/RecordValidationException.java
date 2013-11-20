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

import java.util.ArrayList;
import java.util.List;
import org.netflexity.api.command.CommandException;

/**
 * @author MAX
 *
 */
public class RecordValidationException extends CommandException {

    private List<String> errors = new ArrayList<String>();

    public RecordValidationException() {
	super("Record validation exception");
    }

    /**
     * @param m
     * @param t
     */
    public RecordValidationException(String m, Throwable t) {
	super(m, t);
	this.errors.add(m);
    }

    /**
     * @param m
     */
    public RecordValidationException(String m) {
	super(m);
	this.errors.add(m);
    }

    /**
     * @return Returns the errors.
     */
    public List<String> getErrors() {
	return errors;
    }

    /**
     * @param errors The errors to set.
     */
    public void addError(String message) {
	this.errors.add(message);
    }
}
