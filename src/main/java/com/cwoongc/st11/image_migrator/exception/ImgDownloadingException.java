package com.cwoongc.st11.image_migrator.exception;

public class ImgDownloadingException extends RuntimeException {

    public ImgDownloadingException() {
        super();
    }


    public ImgDownloadingException(String message) {
        super(message);
    }


    public ImgDownloadingException(String message, Throwable cause) {
        super(message, cause);
    }


    public ImgDownloadingException(Throwable cause) {
        super(cause);
    }


    protected ImgDownloadingException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
