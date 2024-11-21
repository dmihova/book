package com.tinqin.academy.library.rest.controllers.hello;

import com.tinqin.academy.library.api.hello.HelloWorld;
import com.tinqin.academy.library.api.hello.HelloWorldInput;
import com.tinqin.academy.library.api.hello.HelloWorldResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequiredArgsConstructor
public class HelloWorldController {
    private final HelloWorld helloWorld;

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {

        HelloWorldResult process = helloWorld.process(new HelloWorldInput());

        return ResponseEntity.ok(process);
    }
}
