package com.example.USLTEST.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex){
        String errorMessage = ex.getMessage();

        return new ResponseEntity<>(createErrorResponse(errorMessage),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleUserAlreadyExitsException(UserAlreadyExistsException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(createErrorResponse(errorMessage),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception ex) {
        System.out.println(ex);
        String errorMessage = "An error occurred while processing the request.";
        return new ResponseEntity<>(createErrorResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Object createErrorResponse(String message){
        return Map.of(
                "status","error",
                "message",message
        );
    }
}
