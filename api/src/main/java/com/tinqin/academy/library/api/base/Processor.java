package com.tinqin.academy.library.api.base;

public interface Processor <R extends ProcessorOutput, I extends ProcessorInput>{
    R process(I input);
}
