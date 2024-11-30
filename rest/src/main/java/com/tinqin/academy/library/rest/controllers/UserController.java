package com.tinqin.academy.library.rest.controllers;

import com.tinqin.academy.library.api.APIRoutes;
import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.blockuser.BlockUser;
import com.tinqin.academy.library.api.operations.blockuser.BlockUserInput;
import com.tinqin.academy.library.api.operations.blockuser.BlockUserResult;
import com.tinqin.academy.library.rest.controllers.base.BaseController;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final BlockUser blockUser;

    @PutMapping(APIRoutes.BLOCK_USER)
    public ResponseEntity<?> blockUser(@PathVariable("userId") String userId) {
        BlockUserInput input = BlockUserInput
                .builder()
                .id(userId)
                .build();

        Either<OperationError, BlockUserResult> result = blockUser.process(input);

        return mapToResponseEntity(result, HttpStatus.OK);

    }
}
