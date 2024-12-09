package com.tinqin.academy.library.rest.controllers;

import com.tinqin.academy.library.api.APIRoutes;
import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.book.getbook.GetBookResult;
import com.tinqin.academy.library.api.operations.user.blockuser.BlockUser;
import com.tinqin.academy.library.api.operations.user.blockuser.BlockUserInput;
import com.tinqin.academy.library.api.operations.user.blockuser.BlockUserResult;
import com.tinqin.academy.library.api.operations.user.getuser.GetUser;
import com.tinqin.academy.library.api.operations.user.getuser.GetUserInput;
import com.tinqin.academy.library.api.operations.user.getuser.GetUserResult;
import com.tinqin.academy.library.api.operations.user.unblockuser.UnblockUser;
import com.tinqin.academy.library.api.operations.user.unblockuser.UnblockUserInput;
import com.tinqin.academy.library.api.operations.user.unblockuser.UnblockUserResult;
import com.tinqin.academy.library.rest.controllers.base.BaseController;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UnblockUser unblockUser;
    private final BlockUser blockUser;
    private final GetUser getUser;

    @PutMapping(APIRoutes.BLOCK_USER)
    public ResponseEntity<?> blockUser(@PathVariable("userId") String userId) {
        BlockUserInput input = BlockUserInput
                .builder()
                .id(userId)
                .build();

        Either<OperationError, BlockUserResult> result = blockUser.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @PutMapping(APIRoutes.UNBLOCK_USER)
    public ResponseEntity<?> unblockUser(@PathVariable("userId") String userId) {
        UnblockUserInput input = UnblockUserInput
                .builder()
                .id(userId)
                .build();

        Either<OperationError, UnblockUserResult> result = unblockUser.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(APIRoutes.GET_USER)
    public ResponseEntity<?> getBook(@PathVariable("userId") String userId) {
        GetUserInput input = GetUserInput
                .builder()
                .userId(userId)
                .build();

        Either<OperationError, GetUserResult> result = getUser.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }
}
