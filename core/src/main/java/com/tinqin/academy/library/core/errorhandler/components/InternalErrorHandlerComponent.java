package com.tinqin.academy.library.core.errorhandler.components;

import com.tinqin.academy.library.api.errors.BeError;
import com.tinqin.academy.library.api.errors.OperationError;
import org.springframework.http.HttpStatus;

public class InternalErrorHandlerComponent extends BaseErrorHandlerComponent {
    @Override
    public OperationError handle(Throwable throwable) {
        return BeError
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode("IE-001")
                .message(throwable.getMessage())
                .build();
    }
}
