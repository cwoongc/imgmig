package com.cwoongc.st11.image_migrator.exception;

public class URLDataMigrationException extends RuntimeException {

    public URLDataMigrationException() {
        super();
    }


    public URLDataMigrationException(String message) {
        super(message);
    }


    public URLDataMigrationException(String message, Throwable cause) {
        super(message, cause);
    }


    public URLDataMigrationException(Throwable cause) {
        super(cause);
    }


    protected URLDataMigrationException(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
