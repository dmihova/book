package com.tinqin.library.book.rest.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class SecurityController {


    @GetMapping("/security/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/security/private")
    public String privateEndpoint() {
        return "This is a private endpoint";
    }
}
