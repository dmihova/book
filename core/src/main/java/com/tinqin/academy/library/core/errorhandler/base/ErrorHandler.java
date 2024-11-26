package com.tinqin.academy.library.core.errorhandler.base;


import com.tinqin.academy.library.api.errors.OperationError;

public interface ErrorHandler {

    OperationError handle(Throwable throwable);
}
