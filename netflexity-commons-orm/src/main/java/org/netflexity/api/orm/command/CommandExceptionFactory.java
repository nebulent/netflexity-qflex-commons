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
package org.netflexity.api.orm.command;

import java.sql.SQLException;
import org.apache.commons.lang.ClassUtils;
import org.netflexity.api.command.CommandException;
import org.netflexity.api.util.StringConstants;

/**
 * @author MAX
 *
 */
public class CommandExceptionFactory {

    private static CommandExceptionFactory instance = new CommandExceptionFactory();

    /**
     * Only one instance is allowed.
     */
    private CommandExceptionFactory() {
    }

    /**
     * @return Returns the instance.
     */
    public static CommandExceptionFactory getInstance() {
	return instance;
    }

    /**
     * @param sqle
     * @return
     */
    public CommandException create(SQLException sqle) {
	return new CommandException(ClassUtils.getShortClassName(getClass()) + StringConstants.COLON + sqle.getMessage() + StringConstants.COLON + sqle.getErrorCode(), sqle);
    }

    /**
     * @param sqle
     * @return
     */
    public CommandException createGeneric(Throwable t) {
	return new CommandException(ClassUtils.getShortClassName(getClass()) + StringConstants.COLON + t.getMessage(), t);
    }
}
