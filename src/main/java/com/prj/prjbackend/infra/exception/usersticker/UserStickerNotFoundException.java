package com.prj.prjbackend.infra.exception.usersticker;

public class UserStickerNotFoundException extends RuntimeException {
    public UserStickerNotFoundException(String message) {
        super(message);
    }
}
