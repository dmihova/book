package com.tinqin.academy.library.rest.controllers.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class HelloWorld {
    @GetMapping("/hello_old")
    public String hello() {
        return "Hello World! - hardcoded message";
    }
}

