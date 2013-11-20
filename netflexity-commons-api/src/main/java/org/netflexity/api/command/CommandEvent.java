package org.netflexity.api.command;

import java.io.Serializable;
import java.util.Date;
import java.util.EventObject;


/**
 * @author Max Fedorov
 * 
 * An event that encapsulates something happening
 * in an DataSource -- the insertion, deletion
 * or updating of a record
 */
public class CommandEvent extends EventObject implements Serializable {

    private Date timeStamp;
    private Command command;

    /**
     * @param commandExecuted
     */
    public CommandEvent(Command commandExecuted) {
        super(commandExecuted);
        this.timeStamp = new Date();
        this.command = commandExecuted;
    }

    /**
     * @return
     */
    public Date getTimeStamp() {
        return timeStamp;
    }
    /**
     * @return
     */
    public Command getCommand() {
        return command;
    }

}
