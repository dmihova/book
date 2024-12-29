package com.tinqin.library.book.rest.notusedauth.contollers;

import com.tinqin.library.book.api.auth.login.LoginUserInput;
import com.tinqin.library.book.api.auth.register.RegisterUser;
import com.tinqin.library.book.api.auth.register.RegisterUserInput;
import com.tinqin.library.book.api.auth.register.RegisterUserResult;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.rest.controllers.base.BaseController;
import com.tinqin.library.book.rest.notusedauth.config.LoginManager;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.tinqin.library.book.api.APIRoutes.LOGIN;
import static com.tinqin.library.book.api.APIRoutes.REGISTER;

//@RestController
@RequiredArgsConstructor
public class AuthController extends BaseController {
    private final RegisterUser registerUser;
    private final LoginManager loginManager;
    @PostMapping(REGISTER)
    public ResponseEntity<?> register(@RequestBody RegisterUserInput input) {
        Either<OperationError, RegisterUserResult> results = registerUser.process(input);

        return mapToResponseEntity(results, HttpStatus.CREATED);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@RequestBody LoginUserInput input) {
        UserDetails userDetail = loginManager.loadUserByUsername(input.getUsername());
        return ResponseEntity.ok("User registered");
    }
}
