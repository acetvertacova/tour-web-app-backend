package com.tour_web_app.exception.advice;

import com.tour_web_app.dto.response.ErrorResponse;
import com.tour_web_app.exception.AuthFailedException;
import com.tour_web_app.exception.CreateFailedException;
import com.tour_web_app.exception.NotFoundException;
import com.tour_web_app.exception.enums.ErrorType;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElseGet(() ->
                        ex.getBindingResult().getGlobalErrors().stream()
                                .map(error -> error.getObjectName() + ": " + error.getDefaultMessage())
                                .findFirst()
                                .orElse("Invalid input.")
                );

        ErrorResponse response = ErrorResponse.builder()
                .errorCode("400")
                .errorDescription(message)
                .impact("The request contains invalid or incomplete data.")
                .resolution("Correct the input data and try again.")
                .errorType(ErrorType.WARNING.name())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException() {
        ErrorResponse response = ErrorResponse.builder()
                .errorCode("404")
                .errorDescription("No matching resource found")
                .impact("The requested resource does not exist or has been removed.")
                .resolution("Check the request parameters or try a different ID.")
                .errorType(ErrorType.WARNING.name())
                .build();

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(AuthFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException() {
        ErrorResponse response = ErrorResponse.builder()
                .errorCode("401")
                .errorDescription("Account details are incorrect. Please try again.")
                .impact("Authentication failed.")
                .resolution("Verify your login or password and try again.")
                .errorType(ErrorType.WARNING.name())
                .build();

        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(CreateFailedException.class)
    public ResponseEntity<ErrorResponse> handleException(CreateFailedException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .errorCode("409")
                .errorDescription(ex.getMessage())
                .impact("The requested resource could not be created and saved to the database.")
                .resolution("Check the input data for validity and required fields. If the issue persists, contact support.")
                .errorType(ErrorType.WARNING.name())
                .build();

        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = ErrorResponse.builder()
                .errorCode("500")
                .errorDescription("Unexpected server error while processing the request")
                .impact("The system encountered an unexpected condition that prevented it from fulfilling the request.")
                .resolution("Please try again later or contact support if the issue persists.")
                .errorType(ErrorType.CRITICAL.name())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
