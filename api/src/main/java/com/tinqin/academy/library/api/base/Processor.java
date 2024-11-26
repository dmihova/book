package com.tinqin.academy.library.api.base;


import com.tinqin.academy.library.api.errors.OperationError;
import io.vavr.control.Either;

public interface Processor <R extends ProcessorOutput, I extends ProcessorInput>{
    Either<OperationError, R> process(I input);
}
