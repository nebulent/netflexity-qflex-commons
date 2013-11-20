package org.netflexity.api.util.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.NestableException;

/**
 * @author Max Fedorov
 *
 * Exception that contains all error messages and 
 * gets thrown during validation exceptions.
 */
public class BeanValidationException extends NestableException {

    private List errors = new ArrayList();

    public BeanValidationException() {
        super("Bean validation exception");
    }
    
    /**
    * @param message
    */
    public BeanValidationException(String message) {
        super(message);
        errors.add(message);
    }

    /**
     * @param message
     * @param cause
     */
    public BeanValidationException(String message, Throwable cause) {
        super(message, cause);
        errors.add(message);
    }
    
    /**
     * @return Returns the errors.
     */
    public List getErrors() {
        return errors;
    }
    
    /**
     * @param errors The errors to set.
     */
    public void addError(String message) {
        this.errors.add(message);
    }
}
