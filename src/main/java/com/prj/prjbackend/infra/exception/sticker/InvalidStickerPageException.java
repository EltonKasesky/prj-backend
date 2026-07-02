package com.prj.prjbackend.infra.exception.sticker;

public class InvalidStickerPageException extends RuntimeException {
    public InvalidStickerPageException(String message) {
        super(message);
    }
}
