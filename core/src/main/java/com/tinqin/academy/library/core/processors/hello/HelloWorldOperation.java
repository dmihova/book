package com.tinqin.academy.library.core.processors.hello;

import com.tinqin.academy.library.api.hello.HelloWorld;
import com.tinqin.academy.library.api.hello.HelloWorldInput;
import com.tinqin.academy.library.api.hello.HelloWorldResult;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldOperation implements HelloWorld {

    @Override
    public HelloWorldResult process(HelloWorldInput input) {
        return HelloWorldResult
                .builder()
                .message("Hello from core/processor")
                .build();
    }
}
