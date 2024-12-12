package com.tinqin.library.book.core.errorhandler.base;


import com.tinqin.library.book.api.errors.OperationError;

public interface ErrorHandler {

    OperationError handle(Throwable throwable);
}
