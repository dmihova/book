package com.tinqin.library.book.core.errorhandler.base;


import com.tinqin.library.book.api.errors.OperationError;

public interface ErrorHandlerComponent {

    OperationError handle(Throwable throwable);

    ErrorHandlerComponent getNext();

    void setNext(ErrorHandlerComponent next);
}
