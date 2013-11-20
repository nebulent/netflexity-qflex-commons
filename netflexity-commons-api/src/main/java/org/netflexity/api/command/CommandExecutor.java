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

import java.io.Serializable;
import java.rmi.Remote;

/**
 * @author Max Fedorov
 *
 * Defines Executor interface.
 */
public interface CommandExecutor extends Serializable, Remote{
	/**
	 * @param command
	 * @return command object, which could possibly have traveled to and
     *          from an application server.
	 * @throws CommandException
	 */
	public Command execute(Command command) throws CommandException;
	
	/**
	 * @param command
	 * @throws CommandException
	 */
	public void init(Command command) throws CommandException;
	
	/**
	 * @param command
	 * @throws CommandException
	 */
	public void finish(Command command) throws CommandException;
}
