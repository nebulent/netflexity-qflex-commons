package org.netflexity.api.command;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Max Fedorov
 * 
 * Abstract Command that provides setters for all required fields such
 * as datasource name. This is the class to extend when building your
 * own business commands.
 */
public abstract class AbstractCommand implements Command {

    private static Logger logger = LoggerFactory.getLogger(AbstractCommand.class.getPackage().getName());
    
    private TransactionResource resource;
    private Object user;
    private boolean inheritTransaction = true;
    
    /**
     * @see com.netflexity.business.Command#run()
     */
    @Override
    public final Command run() throws CommandException {
        long startTime = System.currentTimeMillis();
        boolean failedToExecute = false;
        try {
            CommandExecutor executor = CommandExecutorLocator.getInstance().getCommandExecutor(this);
            return executor.execute(this);
        }
        catch (CommandException se) {
            failedToExecute = true;
            logger.error(getDescription() + " failed because " + se.getMessage(), se);
            throw se;
        }
        finally {
            // Trigger all registered to datasource+command listeners.
            // Note: this is triggered on the client side only and inner
            // commands will not cause registered listeners to hadle events.
            if(!failedToExecute){
                fireEvent(this);
                
                // Log timing.
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                logger.debug("*TRANS[" + resource.getTransactionId() + "] - " + getDescription() + " : took " + elapsedTime + " (ms).");
            }
        }
    }

    /**
     * @param command
     */
    protected void fireEvent(Command command) {
        List listeners = CommandEventListenerRegistryImpl.getInstance().getCommandEventListeners(command.getClass().getName());
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            CommandEventListener listener = (CommandEventListener) iter.next();
            CommandEvent event = new CommandEvent(command);
            listener.handleEvent(event);
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.command.Command#getResource()
     */
    @Override
    public TransactionResource getResource() {
        return resource;
    }

    /* (non-Javadoc)
     * @see org.netflexity.dorm.command.ICommand#setResource(org.netflexity.dorm.datasource.ITransactionResource)
     */
    @Override
    public void setResource(TransactionResource resource) {
        this.resource = resource;
    }

    /* (non-Javadoc)
     * @see org.netflexity.dorm.command.ICommand#getUser()
     */
    @Override
    public Object getUser() {
        return user;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.dorm.command.ICommand#setUser(java.lang.Object)
     */
    @Override
    public void setUser(Object user) {
        this.user = user;
    }
    
    /**
     * @return Returns the inheritTransaction.
     */
    @Override
    public boolean isInheritTransaction() {
        return inheritTransaction;
    }

    /**
     * @param inheritTransaction The inheritTransaction to set.
     */
    @Override
    public void setInheritTransaction(boolean inheritTransaction) {
        this.inheritTransaction = inheritTransaction;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.command.Command#getDescription()
     */
    @Override
    public String getDescription() {
        return getClass().getSimpleName();
    }
}
