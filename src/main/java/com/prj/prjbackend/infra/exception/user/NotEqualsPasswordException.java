package com.prj.prjbackend.infra.exception.user;

public class NotEqualsPasswordException extends RuntimeException {
    public NotEqualsPasswordException(String message) {
        super(message);
    }
}
