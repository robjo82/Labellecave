package com.labellecave.user.exception;

import com.labellecave.user.model.dto.ExceptionDto;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final String TIMESTAMP = String.valueOf(System.currentTimeMillis());
    private static final String BAD_REQUEST = "Bad argument on request, please check your request and try again.";
    private static final String NOT_FOUND = "The requested resource was not found.";
    private static final String CONFLICT = "The request could not be completed due to a conflict with the current state of the target resource.";
    private static final String UNAUTHORIZED = "The request has not been applied because it lacks valid authentication credentials for the target resource.";
    private static final String FORBIDDEN = "The server understood the request, but is refusing to fulfill it.";
    

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(new ExceptionDto(
                BAD_REQUEST,
                "Data integrity violation: " + ex.getMessage(),
                HttpStatus.CONFLICT.toString(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionDto> handleEntityExistsException(EntityExistsException ex) {
        return new ResponseEntity<>(new ExceptionDto(
                CONFLICT,
                "Entity already exists: " + ex.getMessage(),
                HttpStatus.CONFLICT.toString(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionDto> handleForbiddenException(ForbiddenException e) {
        return new ResponseEntity<>(new ExceptionDto(
                FORBIDDEN,
                e.getMessage(),
                HttpStatus.FORBIDDEN.toString(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.FORBIDDEN
        );
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ExceptionDto(
                NOT_FOUND,
                e.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(new ExceptionDto(
                BAD_REQUEST,
                e.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDto> handleUnauthorizedException(UnauthorizedException e) {
        return new ResponseEntity<>(new ExceptionDto(
                UNAUTHORIZED,
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.toString(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionDto> handleConflictException(ConflictException e) {
        return new ResponseEntity<>(new ExceptionDto(
                CONFLICT,
                e.getMessage(),
                HttpStatus.CONFLICT.toString(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception e) {
        return new ResponseEntity<>(new ExceptionDto(
                e.getMessage(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ExceptionDto(
                BAD_REQUEST,
                e.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException e) {
        return new ResponseEntity<>(new ExceptionDto(
                "Authentication credentials not found.",
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.toString(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDto> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new ExceptionDto(
                BAD_REQUEST,
                e.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                TIMESTAMP),
                HttpStatus.BAD_REQUEST
        );
    }


}
