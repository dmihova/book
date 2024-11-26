package com.tinqin.academy.library.rest.controllers;

import com.tinqin.academy.library.api.base.ProcessorOutput;
import com.tinqin.academy.library.api.errors.OperationError;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class BaseController {
    public BaseController() {

    }

    protected <O extends ProcessorOutput> ResponseEntity<?> mapToResponseEntity(
            Either<OperationError, O> either, HttpStatus httpStatus) {

        return either.isRight() ? new ResponseEntity((ProcessorOutput) either.get(), httpStatus)
                : new ResponseEntity(((OperationError) either.getLeft()).getStatus(), httpStatus);
    }

}
