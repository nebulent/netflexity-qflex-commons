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
package org.netflexity.api.orm.command;

import org.netflexity.api.command.Command;
import org.netflexity.api.command.CommandException;
import org.netflexity.api.orm.DatasourceMetadata;

/**
 * @author Max Fedorov
 *
 */
public interface OrmCommand extends Command {
    
    /**
     * @return Returns the datasourceMetadata.
     */
    DatasourceMetadata getDatasourceMetadata();
        
    /**
     * @param datasourceMetadata.
     */
    void setDatasourceMetadata(DatasourceMetadata metadata);
    
    /**
     * Commit all updates before the point, where
     * this method gets called.
     * 
     * @throws CommandException
     */
    void commit() throws CommandException;
    
    /**
     * Rollback all updates before the point, where
     * this method gets called.
     * 
     * @throws CommandException
     */
    void rollback() throws CommandException;
}
