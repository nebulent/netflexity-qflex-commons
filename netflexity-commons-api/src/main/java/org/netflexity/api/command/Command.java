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

/**
 * @author Max Fedorov
 *
 */
public interface Command extends Serializable {

    /**
     * Execute command through special CommandExecutor that manages 
     * all transaction specific logic transparently.
     * 
     * @return Command
     * @throws CommandException
     */
    public Command run() throws CommandException;

    /**
     * This method will typically be overriden with command
     * specific business logic.
     * @throws CommandException
     */
    void execute() throws CommandException;

    /**
     * @return
     */
    public TransactionResource getResource();
    
    /**
     * @param resource
     */
    public void setResource(TransactionResource resource);

    /**
     * @return
     */
    public Object getUser();
    
    /**
     * @param user
     */
    public void setUser(Object user);
    
    /**
     * @return Returns the inheritTransaction.
     */
    public boolean isInheritTransaction();
    
    /**
     * @param inheritTransaction The inheritTransaction to set.
     */
    public void setInheritTransaction(boolean inheritTransaction);
    
    /**
     * @return description of the command.
     */
    public String getDescription();
}
