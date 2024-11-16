package dev.arman.productservice.exceptions;

/**
 * @author mdarmanansari
 */
public class ProductNotExistsException extends Exception {

    public ProductNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProductNotExistsException(Throwable cause) {
        super(cause);
    }

    public ProductNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotExistsException(String message) {
        super(message);
    }

    public ProductNotExistsException() {
    }
}
