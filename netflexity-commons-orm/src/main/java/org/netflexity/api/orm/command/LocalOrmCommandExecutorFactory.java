package org.netflexity.api.orm.command;

import org.netflexity.api.command.CommandExecutor;
import org.netflexity.api.command.CommandExecutorFactory;

/**
 * @author Max Fedorov
 *
 * Factory that creates executors on the local box.
 */
public class LocalOrmCommandExecutorFactory implements CommandExecutorFactory {

    // Keep a separate executor per thread.
    private static ThreadLocal anExecutor = new ThreadLocal();

    /**
     * Default Constructor
     */
    public LocalOrmCommandExecutorFactory() {
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.command.CommandExecutorFactory#createExecutor()
     */
    @Override
    public CommandExecutor createExecutor() {
	CommandExecutor executor = (CommandExecutor) anExecutor.get();
	if (executor == null) {
	    executor = new LocalOrmCommandExecutor();
	    anExecutor.set(executor);
	}
	return executor;
    }
}
