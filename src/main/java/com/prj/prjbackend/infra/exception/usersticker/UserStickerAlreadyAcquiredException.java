package com.prj.prjbackend.infra.exception.usersticker;

public class UserStickerAlreadyAcquiredException extends RuntimeException {
    public UserStickerAlreadyAcquiredException(String message) {
        super(message);
    }
}
