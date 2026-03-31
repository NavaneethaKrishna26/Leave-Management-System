package com.lms.demo.exception;

import com.lms.demo.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 400 — @Valid failures
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> fieldErrors.put(e.getField(), e.getDefaultMessage()));
        return respond(HttpStatus.BAD_REQUEST, "Bad Request",
                "Validation failed", req, fieldErrors);
    }
    // 404 — resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(
            ResourceNotFoundException ex, HttpServletRequest req) {
        return respond(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), req,
                null);
    }
    // 403 — ownership violation
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbidden(
            UnauthorizedException ex, HttpServletRequest req) {
        return respond(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage(), req,
                null);
    }
    // 409 — duplicate registration
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(
            UserAlreadyExistsException ex, HttpServletRequest req) {
        return respond(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), req,
                null);
    }
    // 500 — unhandled exceptions (safety net)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(
            Exception ex, HttpServletRequest req) {
        return respond(HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error", "An unexpected error occurred", req,
                null);
    }
    // ── Helper ────────────────────────────────────────────────────────────────
    private ResponseEntity<ErrorResponseDTO> respond(HttpStatus status, String error,
                                                     String message, HttpServletRequest req, Map<String, String>
                                                          fieldErrors) {
        ErrorResponseDTO body = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(req.getRequestURI())
                .errors(fieldErrors)
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
