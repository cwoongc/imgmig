package com.cwoongc.st11.image_migrator.exception;

public class URLReplacingException extends RuntimeException {

    public URLReplacingException() {
        super();
    }


    public URLReplacingException(String message) {
        super(message);
    }


    public URLReplacingException(String message, Throwable cause) {
        super(message, cause);
    }


    public URLReplacingException(Throwable cause) {
        super(cause);
    }


    protected URLReplacingException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
