package com.cwoongc.st11.image_migrator.exception;

public class PlanGeneratingException extends RuntimeException {

    public PlanGeneratingException() {
        super();
    }


    public PlanGeneratingException(String message) {
        super(message);
    }


    public PlanGeneratingException(String message, Throwable cause) {
        super(message, cause);
    }


    public PlanGeneratingException(Throwable cause) {
        super(cause);
    }


    protected PlanGeneratingException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
