package com.prj.prjbackend.infra.exception.user;

public class InvalidUserPasswordException extends RuntimeException {
    public InvalidUserPasswordException(String message) {
        super(message);
    }
}
