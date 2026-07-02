package com.prj.prjbackend.infra.exception.sticker;

public class StickerNotFoundException extends RuntimeException {
    public StickerNotFoundException(String message) {
        super(message);
    }
}
