package dev.arman.productservice.exceptions;

/**
 * @author mdarmanansari
 */
public class UnableToAddProductException extends Exception {

    public UnableToAddProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnableToAddProductException(Throwable cause) {
        super(cause);
    }

    public UnableToAddProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToAddProductException(String message) {
        super(message);
    }

    public UnableToAddProductException() {
    }
}
