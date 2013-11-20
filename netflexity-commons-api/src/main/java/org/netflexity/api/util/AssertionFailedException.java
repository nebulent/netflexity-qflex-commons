package org.netflexity.api.util;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author Max Fedorov
 * 
 * Runtime exception to indicate that an assertion
 * has failed. Now allows wrapping of exceptions,
 * useful for tunnelling of a checked exception in
 * an assertion.
 */
public class AssertionFailedException extends NestableRuntimeException {

    /**
     * @param message
     * @param t
     */
    public AssertionFailedException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * @param message
     */
    public AssertionFailedException(String message) {
        super((message == null) ? "Assertion failed: " : message);
    }

    /**
     * @param t
     */
    public AssertionFailedException(Throwable t) {
        super(t);
    }
}
