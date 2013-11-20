package org.netflexity.api.command;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.DiscoveryUtil;
import org.netflexity.api.util.PropertiesUtil;
import org.netflexity.api.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Max Fedorov
 *
 * This is just another layer of abstraction between Commands
 * and specific Command Executors, to simplify and facilitate
 * execution of multiple inner commands under transaction of the parent
 * one. To accomplish that, ThreadLocal variables are used. 
 * In SDK1.4 performance of such variables is very acceptable.
 * 
 * Note: this locator is residing on the server side in distributed
 * environment.
 */
public class CommandExecutorLocator {

    private static Logger logger = LoggerFactory.getLogger(CommandExecutorLocator.class.getPackage().getName());
    // String constant used for Properties file
    public static final String DISCOVERY_CLASSES_PATH = "executorDiscoveryClasses.properties";
    // Single instance of locator.
    private static CommandExecutorLocator instance;
    // Executor Factory properties, containing class names of concrete executors.
    private Properties discoveryClasses = new Properties();
    private CommandExecutorFactory executorFactoryInstance;

    /**
     * Private Default Constructor.
     */
    private CommandExecutorLocator() {
        try {
            discoveryClasses = PropertiesUtil.getProperties(DISCOVERY_CLASSES_PATH);
            executorFactoryInstance = (CommandExecutorFactory) DiscoveryUtil.newInstance(CommandExecutorFactory.class, discoveryClasses);
        } catch (Throwable t) {
            logger.warn("CommandExecutorLocator failed to load '" + DISCOVERY_CLASSES_PATH + "' file.", t);

            // Re-try
            String defaultPath = null;
            try {
                defaultPath = StringUtils.replace(getClass().getPackage().getName(), StringConstants.DOT, StringConstants.FORWARD_SLASH) + DISCOVERY_CLASSES_PATH;
                discoveryClasses = PropertiesUtil.getProperties(defaultPath);
                executorFactoryInstance = (CommandExecutorFactory) DiscoveryUtil.newInstance(CommandExecutorFactory.class, discoveryClasses);
            } catch (Throwable tr) {
                logger.error("CommandExecutorLocator failed to load '" + defaultPath + "' file.", tr);
                throw new RuntimeCommandException(tr);
            }
        }
    }

    /**
     * @return singleton instance of locator.
     */
    public static synchronized CommandExecutorLocator getInstance() {
        if (instance == null) {
            instance = new CommandExecutorLocator();
        }
        return instance;
    }

    /**
     * @param command
     * @return
     * @throws CommandException
     */
    public CommandExecutor getCommandExecutor(Command command) throws CommandException {
        return executorFactoryInstance.createExecutor();
    }
}
