package dev.arman.productservice.exceptions;

/**
 * @author mdarmanansari
 */
public class CategoryNotExistsException extends Exception {

    public CategoryNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CategoryNotExistsException(Throwable cause) {
        super(cause);
    }

    public CategoryNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryNotExistsException(String message) {
        super(message);
    }

    public CategoryNotExistsException() {
    }
}
