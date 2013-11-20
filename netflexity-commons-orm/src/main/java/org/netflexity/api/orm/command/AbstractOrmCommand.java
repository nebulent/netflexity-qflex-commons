package org.netflexity.api.orm.command;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.netflexity.api.command.AbstractCommand;
import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResourceException;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.util.jdbc.StatementExecutor;

/**
 * @author Max Fedorov
 *
 * Generic command implementation for all ORM commands.
 */
public abstract class AbstractOrmCommand extends AbstractCommand implements OrmCommand {

    private DatasourceMetadata datasourceMetadata;
    protected StatementExecutor executor;

    /**
     * @param datasourceMetadata
     */
    public AbstractOrmCommand(DatasourceMetadata datasourceMetadata) {
        this.datasourceMetadata = datasourceMetadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.command.OrmCommand#commit()
     */
    @Override
    public void commit() throws CommandException {
        try {
            getResource().commit();
        } catch (TransactionResourceException sql) {
            throw new CommandException("Failed to commit contents of " + ClassUtils.getShortClassName(getClass()) + " because " + sql.getMessage(), sql);
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.orm.command.OrmCommand#rollback()
     */
    @Override
    public void rollback() throws CommandException {
        try {
            getResource().rollback();
        } catch (TransactionResourceException sql) {
            throw new CommandException("Failed to rollback contents of " + ClassUtils.getShortClassName(getClass()) + " because " + sql.getMessage(), sql);
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.command.AbstractCommand#getDatasourceMetadata()
     */
    @Override
    public DatasourceMetadata getDatasourceMetadata() {
        return datasourceMetadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.command.AbstractCommand#setDatasourceMetadata(null)
     */
    @Override
    public void setDatasourceMetadata(DatasourceMetadata datasourceMetadata) {
        this.datasourceMetadata = datasourceMetadata;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.command.Command#getDescription()
     */
    @Override
    public String getDescription() {
        return (executor != null) ? executor.getDescription() : StringUtils.EMPTY;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getDescription();
    }
}
