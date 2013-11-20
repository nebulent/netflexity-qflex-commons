package org.netflexity.api.command;


/**
 * @author Max Fedorov
 * 
 * A DataListener that maintains information.
 */
public abstract class AbstractCommandEventListener implements CommandEventListener {

    private Class commandClass;

    /**
     * @param commandClass
     */
    protected AbstractCommandEventListener(Class commandClass) {
        this.commandClass = commandClass;
        CommandEventListenerRegistryImpl.getInstance().registerCommandEventListener(this);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.command.CommandEventListener#getCommandClass()
     */
    public Class getCommandClass() {
        return commandClass;
    }
}
