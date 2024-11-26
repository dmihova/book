package com.tinqin.academy.library.core.errorhandler.exceptions;

public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }
}