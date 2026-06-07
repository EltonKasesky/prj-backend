package com.prj.prjbackend.infra.exception.profile;

public class ProfileAlreadyExists extends RuntimeException {
    public ProfileAlreadyExists(String message) {
        super(message);
    }
}
