package com.tinqin.academy.library.rest.controllers;


import org.hibernate.ObjectNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice

//https://www.baeldung.com/exception-handling-for-rest-with-spring
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<ErrorMessage> ObjectNotFoundException(ObjectNotFoundException ex, WebRequest request) {

        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }


}
