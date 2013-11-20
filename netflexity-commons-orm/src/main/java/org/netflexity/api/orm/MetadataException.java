package org.netflexity.api.orm;

/**
 * @author Max Fedorov
 *
 * Exception thrown when a metadata related error occurrs. Should be
 * prevented as soon as encountered.
 */
public class MetadataException extends RuntimeException {

    private Throwable cause;

    /**
     * Default.
     */
    public MetadataException() {
    }

    /**
     * @param message
     */
    public MetadataException(String message) {
	super(message);
    }

    /**
     * @param cause
     */
    public MetadataException(Throwable cause) {
	if (cause != null) {
	    cause.fillInStackTrace();
	    this.cause = cause;
	}
    }

    /**
     * @param message
     * @param cause
     */
    public MetadataException(String message, Throwable cause) {
	super(message);
	if (cause != null) {
	    cause.fillInStackTrace();
	    this.cause = cause;
	}
    }

    /**
     * Returns the cause.
     *
     * @return Throwable
     */
    public Throwable getCause() {
	return cause;
    }

    /**
     * Sets the cause.
     *
     * @param cause The cause to set
     */
    public void setCause(Throwable cause) {
	this.cause = cause;
	if (cause != null) {
	    cause.fillInStackTrace();
	    this.cause = cause;
	}
    }
}
