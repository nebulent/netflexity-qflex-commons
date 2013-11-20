package org.netflexity.api.command;


/**
 * @author Max Fedorov
 *
 * AbstractCommand Executer is triggered in the following 
 * sequence AbstractCommand-->Locator-->Factory-->Executor.
 * Locator has a ThreadLocal variable that will make sure
 * that only parent command (not inner commands) will be executed
 * by specific AbstractCommand Executor, thus only one transaction will 
 * exist for a AbstractCommand and its childs (inner command calls).
 */
public abstract class AbstractCommandExecutor implements CommandExecutor {
}
