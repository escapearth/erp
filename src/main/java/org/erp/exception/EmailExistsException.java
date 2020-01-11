package org.erp.exception;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String msg, Throwable t) {
        super(msg,t);
    }
    public EmailExistsException(String msg) {
        super(msg);
    }
    public EmailExistsException() {
        super();
    }
}
