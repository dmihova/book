package com.tinqin.academy.library.core.errorhandler.base;


import com.tinqin.academy.library.api.errors.OperationError;

public interface ErrorHandlerComponent {

    OperationError handle(Throwable throwable);

    ErrorHandlerComponent getNext();

    void setNext(ErrorHandlerComponent next);
}
