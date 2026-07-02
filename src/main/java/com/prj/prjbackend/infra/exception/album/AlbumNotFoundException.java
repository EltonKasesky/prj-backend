package com.prj.prjbackend.infra.exception.album;

public class AlbumNotFoundException extends RuntimeException {
  public AlbumNotFoundException(String message) {
    super(message);
  }
}
