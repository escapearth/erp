package org.erp.exception;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String msg, Throwable t) {
        super(msg,t);
    }
    public UserAlreadyExistException(String msg) {
        super(msg);
    }
    public UserAlreadyExistException() {
        super();
    }
}
