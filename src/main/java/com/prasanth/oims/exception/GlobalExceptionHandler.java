package com.prasanth.oims.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import com.prasanth.oims.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
        MethodArgumentNotValidException ex, HttpServletRequest request){

        String errorMessage = ex.getBindingResult()
                                .getFieldErrors()
                                .get(0)
                                .getDefaultMessage();
        
        return ResponseEntity.badRequest().body(
            new com.prasanth.oims.dto.ErrorResponse(
                400, 
                "Bad Request", 
                errorMessage, 
                request.getRequestURI().toString()
            )
        ); 
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex, HttpServletRequest request){
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                    400, 
                    "Bad Request",
                    ex.getMessage(), 
                    request.getRequestURI().toString()
                )
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request){
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(
                                new ErrorResponse(
                                    401,
                                    "Unauthorized",
                                    errorMessage,
                                    request.getRequestURI().toString()
                                )
                    );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        return ResponseEntity.badRequest().body(
            new ErrorResponse(
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
            )
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ErrorResponse(
                404,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
            )
        );
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(
                                    new ErrorResponse(
                                        500,
                                        "Internal Server Error",
                                        "Something went wrong. Please try again later.",
                                        request.getRequestURI().toString()
                                    )
                                );
    }


}
