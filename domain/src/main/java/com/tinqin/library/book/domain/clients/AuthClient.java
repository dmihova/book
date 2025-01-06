package com.tinqin.library.book.domain.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authClient", url = "${authentication.url}")
public interface AuthClient {

    @GetMapping(
            path = {"/verify"},
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    String verify(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);

    @GetMapping(
            path = {"/verify"},
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    String verify(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                  @RequestParam String role);
}
