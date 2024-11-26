package com.tinqin.academy.library.core.processors.hello;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.hello.HelloWorld;
import com.tinqin.academy.library.api.hello.HelloWorldInput;
import com.tinqin.academy.library.api.hello.HelloWorldResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HelloWorldOperation implements HelloWorld {
    private final ErrorHandler errorHandler;


    @Override
    public Either<OperationError, HelloWorldResult> process(HelloWorldInput input) {
        return Try.of(() -> HelloWorldResult
                        .builder()
                        .message("Hello from core/processor")
                        .build())
                .toEither()
                .mapLeft(errorHandler::handle);
    }
}
