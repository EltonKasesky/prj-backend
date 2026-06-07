package com.prj.prjbackend.infra.exception.profile;

public class ProfileDisabledException extends RuntimeException {
    public ProfileDisabledException(String message) {
        super(message);
    }
}
