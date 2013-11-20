package org.netflexity.api.orm.command;

import org.apache.log4j.Logger;
import org.netflexity.api.command.AbstractCommandExecutor;
import org.netflexity.api.command.Command;
import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.datasource.Datasource;
import org.netflexity.api.orm.datasource.db.DatabaseDatasourceFactory;

/**
 * @author Max Fedorov
 *
 * Implementation of executor that executes on the same box as 
 * the rest of the application
 */
public class LocalOrmCommandExecutor extends AbstractCommandExecutor {

    private static Logger logger = Logger.getLogger(LocalOrmCommandExecutor.class.getPackage().getName());
    // Keep track of inner command calls.
    protected static final ThreadLocal threadLocalParentCommand = new ThreadLocal();

    /**
     * Overriden to provide inner command the same context.
     * @see org.netflexity.dorm.command.CommandExecutor#execute(org.netflexity.dorm.command.Command)
     */
    @Override
    public Command execute(Command command) throws CommandException {

        // Execute command directly in case of an inner call and inheritTransaction = true;
        // Assumption: Parent command already started execution,
        // transaction and located appropriate executor. This
        // executes in the scope of LocalOrmCommandExecutor.
        OrmCommand localCommand = (OrmCommand) threadLocalParentCommand.get();
        if (localCommand != null && command.isInheritTransaction()) {
            // Execute inner command business logic w/o going through 
            // LocalOrmCommandExecutor to be under one transaction.
            OrmCommand cmd = ((OrmCommand) command);
            cmd.setResource(localCommand.getResource()); // connection
            cmd.setDatasourceMetadata(localCommand.getDatasourceMetadata());
            cmd.execute();

            return command;
        } // Execute outer command (parent command).
        // Note: this will happen only once per parent command.
        else {
            // Only set ThreadLocal if inheriting transactions is allowed.
            if (command.isInheritTransaction()) {
                threadLocalParentCommand.set(command);
            }

            DatasourceMetadata dmd = ((OrmCommand) command).getDatasourceMetadata();
            Datasource datasource = DatabaseDatasourceFactory.getInstance().getDatasource(dmd);
            TransactionResource connection = null;
            boolean transactionFailed = false;
            try {
                // Obtain command resource.
                connection = datasource.getResource();

                // Start command-level transaction.
                connection.begin();

                // Set resource in AbstractCommand for reuse (one per thread).
                command.setResource(connection);

                // Trigger callback function to include plugable functionality.
                init(command);

                /*
                 * Execute the actual command.
                 * NOTE: This command can have inner commands that will be executed
                 * w/o AbstractCommand Executor to be in context of one transaction.
                 */
                return process(command);
            } catch (Throwable t) {
                if (connection != null) {
                    transactionFailed = true;
                    try {
                        connection.rollback();
                        logger.error("Rolling back transaction TRANS-ID=[" + connection.getTransactionId() + "]");
                    } catch (Throwable t1) {
                        logger.error("Rollback failed because " + t1.getMessage(), t1);
                    }
                }
                Object message = t.getMessage();
                String errorMessage = "";
                if (message == null) {
                	
                } else if (message.getClass().isArray()) {
                    StringBuilder sb = new StringBuilder();
                    String[] mess = (String[]) message;
                    for (int i = 0; i < mess.length; i++) {
                        sb.append(mess[i]).append(" | ");
                    }
                    errorMessage = sb.toString();
                } else {
                    errorMessage = t.getMessage();
                }
                logger.error(errorMessage, t);
                
                if (t instanceof CommandException) {
                    throw (CommandException) t;
                } else {
                    throw new CommandException(errorMessage, t);
                }
            } finally {
                // Apply finalizing logic to AbstractCommand that was just executed
                // and allows to inherit parent's transaction.
                if (command.isInheritTransaction()) {
                    finish(command);
                }

                // Try to commit or rollback the transaction.
                if (connection != null) {
                    try {
                        if (!transactionFailed) {
                            connection.commit();
                            // logger.debug("Committing transaction TRANS-ID=[" + connection.getTransactionId() + "]");
                        }
                    } catch (Throwable t) {
                        try {
                            connection.rollback();
                            logger.error("Finally Rolling back transaction TRANS-ID=[" + connection.getTransactionId() + "]");
                        } catch (Throwable t1) {
                            logger.error("Finally Rollback failed because " + t1.getMessage(), t1);
                        }
                        throw new CommandException("Finally Rollback() failed because " + t.getMessage(), t);
                    } finally {
                        try {
                            connection.close();
                            //logger.debug("Returned connection to pool TRANS-ID=[" + connection.getTransactionId() + "]");
                        } catch (Throwable t) {
                            logger.error("Finally close() failed because " + t.getMessage(), t);
                            throw new CommandException("Finally close() failed because " + t.getMessage(), t);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param command
     * @return
     * @throws CommandException
     */
    protected Command process(Command command) throws CommandException {
        command.execute();
        return command;
    }

    /**
     * @see org.netflexity.dorm.command.CommandExecutor#finish(Command)
     */
    @Override
    public void finish(Command command) throws CommandException {
        // Clean thread local at the end of the command.
        threadLocalParentCommand.set(null);
    }

    /**
     * @see org.netflexity.dorm.command.CommandExecutor#init(Command)
     */
    @Override
    public void init(Command command) throws CommandException {
    }
}
