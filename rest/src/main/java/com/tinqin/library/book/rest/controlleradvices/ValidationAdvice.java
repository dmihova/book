package com.tinqin.library.book.rest.controlleradvices;


import com.tinqin.library.book.api.enumerations.MessageLevel;
import com.tinqin.library.book.api.errors.BeError;
import com.tinqin.library.book.api.errors.OperationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationAdvice  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<OperationError> handleValidationException(MethodArgumentNotValidException exception) {

        BeError error = BeError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .messageLevel(MessageLevel.ERROR)
                .errorCode("BE-400")
                .message(exception.getMessage())
                .build();

        return ResponseEntity
                .status(error.getStatus())
                .body(error);
    }
}