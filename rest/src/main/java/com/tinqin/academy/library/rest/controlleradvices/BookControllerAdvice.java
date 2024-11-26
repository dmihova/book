package com.tinqin.academy.library.rest.controlleradvices;

import com.tinqin.academy.library.api.enumerations.MessageLevel;
import com.tinqin.academy.library.api.errors.BeError;
import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.rest.controllers.BookController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = BookController.class)
public class BookControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<OperationError> handleBusinessException(BusinessException businessException) {
        BeError error = BeError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .messageLevel(MessageLevel.ERROR)
                .errorCode("BE-404")
                .message(businessException.getMessage())
                .build();

        return ResponseEntity
                .status(error.getStatus())
                .body(error);
    }
}