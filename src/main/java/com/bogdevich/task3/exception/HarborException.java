package com.bogdevich.task3.exception;

/**
 * Created by eugene on 12.4.17.
 */
public class HarborException extends Exception {
    public HarborException() {
    }

    public HarborException(String s) {
        super(s);
    }

    public HarborException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public HarborException(Throwable throwable) {
        super(throwable);
    }
}
