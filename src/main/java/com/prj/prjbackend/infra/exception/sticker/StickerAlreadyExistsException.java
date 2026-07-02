package com.prj.prjbackend.infra.exception.sticker;

public class StickerAlreadyExistsException extends RuntimeException {
    public StickerAlreadyExistsException(String message) {
        super(message);
    }
}
