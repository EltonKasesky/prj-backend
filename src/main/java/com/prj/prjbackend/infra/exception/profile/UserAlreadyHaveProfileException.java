package com.prj.prjbackend.infra.exception.profile;

public class UserAlreadyHaveProfileException extends RuntimeException {
    public UserAlreadyHaveProfileException(String message) {
        super(message);
    }
}
