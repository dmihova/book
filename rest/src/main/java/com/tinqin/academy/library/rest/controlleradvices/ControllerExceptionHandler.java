package com.tinqin.academy.library.rest.controlleradvices;


import com.tinqin.academy.library.api.enumerations.MessageLevel;
import com.tinqin.academy.library.api.errors.BeError;
import com.tinqin.academy.library.api.errors.OperationError;
import org.hibernate.ObjectNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<ErrorMessage> ObjectNotFoundException(ObjectNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> ObjectNotFoundException(IllegalArgumentException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }



}
