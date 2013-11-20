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

import java.util.EventListener;
import java.util.Properties;


/**
 *  The interface implemented by objects that listen
 *  to data-related events thrown by instances of Command.
 */
public interface CommandEventListener extends EventListener {

    /**
     * @return Command class object.
     */
    public Class getCommandClass();
    
    /**
     * handle the event when the DataSource we are listening
     * to has modified data
     */
    public void handleEvent(CommandEvent event);

    /**
     * used in the startup phase to initialize the object
     * based on properties. Note that the properties
     * are typically defined in an XML file.
     */
    public void init(Properties properties);
}
