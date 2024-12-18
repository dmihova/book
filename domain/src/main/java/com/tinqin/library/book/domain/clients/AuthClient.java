package com.tinqin.library.book.domain.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authClient", url = "${authentication.url}")
public interface AuthClient {

    @GetMapping(
            path = {"/validate"},
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    String validate(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);
}
