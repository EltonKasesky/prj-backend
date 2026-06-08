package com.prj.prjbackend.infra.exception.auth;

public class UserAuthNotFoundException extends RuntimeException {
    public UserAuthNotFoundException(String message) {
        super(message);
    }
}
