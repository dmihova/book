package com.tinqin.library.book.api.base;


import com.tinqin.library.book.api.errors.OperationError;
import io.vavr.control.Either;

public interface Processor <R extends ProcessorResult, I extends ProcessorInput>{
    Either<OperationError, R> process(I input);
}
