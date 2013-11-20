package org.netflexity.api.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Max Fedorov
 *
 * Keeps track of all registered Command Listeners.
 * 
 */
public class CommandEventListenerRegistryImpl implements CommandEventListenerRegistry {

    // Singleton instance of the factory class.
    private static CommandEventListenerRegistry instance = new CommandEventListenerRegistryImpl();

    // All registered listeners
    private Map listenerLookup = Collections.synchronizedMap(new HashMap());
    
    /**
     * Default Constructor
     */
    private CommandEventListenerRegistryImpl() {
    }

    /**
     * @return
     */
    public static CommandEventListenerRegistry getInstance() {
        return instance;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.command.CommandEventListenerRegistry#registerCommandEventListener(org.netflexity.api.command.CommandEventListener)
     */
    public void registerCommandEventListener(CommandEventListener el) {
        String key = el.getCommandClass().getName();
        synchronized (listenerLookup) {
            if (listenerLookup.containsKey(key)) {
                List listeners = (List) listenerLookup.get(key);
                listeners.add(el);
            }
            else {
                List listeners = new ArrayList(5);
                listenerLookup.put(key, listeners);
                listeners.add(el);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.dorm.command.impl.ICommandEventListenerRegistry#getCommandEventListeners(java.lang.String)
     */
    public List getCommandEventListeners(String className) {
        synchronized (listenerLookup) {
            List listeners = (List) listenerLookup.get(className);
            if (listeners == null) {
                listeners = new ArrayList(5);
                listenerLookup.put(className, listeners);
            }
            return listeners;
        }
    }
}
