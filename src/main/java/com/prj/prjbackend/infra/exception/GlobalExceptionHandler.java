package com.prj.prjbackend.infra.exception;

import com.prj.prjbackend.infra.exception.album.AlbumNotFoundException;
import com.prj.prjbackend.infra.exception.auth.UserAuthNotFoundException;
import com.prj.prjbackend.infra.exception.dto.StandardErrorDTO;
import com.prj.prjbackend.infra.exception.profile.ProfileAlreadyExists;
import com.prj.prjbackend.infra.exception.profile.ProfileDisabledException;
import com.prj.prjbackend.infra.exception.profile.ProfileNotFoundException;
import com.prj.prjbackend.infra.exception.profile.UserAlreadyHaveProfileException;
import com.prj.prjbackend.infra.exception.sticker.InvalidStickerPageException;
import com.prj.prjbackend.infra.exception.sticker.StickerAlreadyExistsException;
import com.prj.prjbackend.infra.exception.sticker.StickerNotFoundException;
import com.prj.prjbackend.infra.exception.user.*;
import com.prj.prjbackend.infra.exception.usersticker.UserStickerAlreadyAcquiredException;
import com.prj.prjbackend.infra.exception.usersticker.UserStickerNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    final String NOT_FOUND_MESSAGE = "Recurso não encontrado.";
    final String ALREADY_EXISTS = "Recurso já existente.";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> handleUserNotFound(
            UserNotFoundException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                NOT_FOUND_MESSAGE,
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardErrorDTO> handleUserNotFound(
            UserAlreadyExistsException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                ALREADY_EXISTS,
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<StandardErrorDTO> handleUserNotFound(
            UserDisabledException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                "Recurso desabilitado.",
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidUserPasswordException.class)
    public ResponseEntity<StandardErrorDTO> handleUserNotFound(
            InvalidUserPasswordException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                "A senha mencionada está incorreta ou é invalida.",
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(NotEqualsPasswordException.class)
    public ResponseEntity<StandardErrorDTO> handleUserNotFound(
            NotEqualsPasswordException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                "As senhas devem ser iguais para a troca da senha.",
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> handleUserNotFound(
            ProfileNotFoundException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                NOT_FOUND_MESSAGE,
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ProfileAlreadyExists.class)
    public ResponseEntity<StandardErrorDTO> handleUserNotFound(
            ProfileAlreadyExists exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                ALREADY_EXISTS,
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ProfileDisabledException.class)
    public ResponseEntity<StandardErrorDTO> handleUserNotFound(
            ProfileDisabledException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                "Recurso desabilitado.",
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserAlreadyHaveProfileException.class)
    public ResponseEntity<StandardErrorDTO> handleUserNotFound(
            UserAlreadyHaveProfileException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                "O usuário já possui o perfil solicitado.",
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserAuthNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> handleUserNotFound(
            UserAuthNotFoundException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                NOT_FOUND_MESSAGE,
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> handleAlbumNotFound(
            AlbumNotFoundException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                NOT_FOUND_MESSAGE,
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(StickerNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> handleStickerNotFound(
            StickerNotFoundException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                NOT_FOUND_MESSAGE,
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(StickerAlreadyExistsException.class)
    public ResponseEntity<StandardErrorDTO> handleStickerAlreadyExists(
            StickerAlreadyExistsException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                ALREADY_EXISTS,
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidStickerPageException.class)
    public ResponseEntity<StandardErrorDTO> handleInvalidStickerPage(
            InvalidStickerPageException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                "Dados inválidos.",
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserStickerAlreadyAcquiredException.class)
    public ResponseEntity<StandardErrorDTO> handleUserStickerAlreadyAcquired(
            UserStickerAlreadyAcquiredException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                "Recurso em uso.",
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserStickerNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> handleUserStickerNotFound(
            UserStickerNotFoundException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                NOT_FOUND_MESSAGE,
                exception.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorDTO> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        StandardErrorDTO error = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                "Erro de validação.",
                message
        );

        return ResponseEntity.status(status).body(error);
    }
}
