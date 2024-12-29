package com.tinqin.library.book.rest.controllers;

import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.subscription.querysubscription.QuerySubscriptionInput;
import com.tinqin.library.book.api.operations.subscription.querysubscription.QuerySubscriptionResult;
import com.tinqin.library.book.api.operations.user.blockuser.BlockUser;
import com.tinqin.library.book.api.operations.user.blockuser.BlockUserInput;
import com.tinqin.library.book.api.operations.user.blockuser.BlockUserResult;
import com.tinqin.library.book.api.operations.user.getuser.GetUser;
import com.tinqin.library.book.api.operations.user.getuser.GetUserInput;
import com.tinqin.library.book.api.operations.user.getuser.GetUserResult;
import com.tinqin.library.book.api.operations.user.queryuser.QueryUser;
import com.tinqin.library.book.api.operations.user.queryuser.QueryUserInput;
import com.tinqin.library.book.api.operations.user.queryuser.QueryUserResult;
import com.tinqin.library.book.api.operations.user.unblockuser.UnblockUser;
import com.tinqin.library.book.api.operations.user.unblockuser.UnblockUserInput;
import com.tinqin.library.book.api.operations.user.unblockuser.UnblockUserResult;
import com.tinqin.library.book.rest.controllers.base.BaseController;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UnblockUser unblockUser;
    private final BlockUser blockUser;
    private final GetUser getUser;
    private final QueryUser queryUser;

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

    @GetMapping(APIRoutes.API_USER)
    public ResponseEntity<?> getUsers(
            @RequestParam(name = "firstName", required = false, defaultValue = "") String firstName,
            @RequestParam(name = "lastName", required = false, defaultValue = "") String lastName,
            @RequestParam(name = "isAdmin", required = false  ) boolean isAdmin,
            @RequestParam(name = "isBlocked", required = false ) boolean isBlocked,
            @SortDefault(sort = "lastName", direction = Sort.Direction.ASC)
            @PageableDefault(page = 0, size = 10
            ) Pageable pageable
    ) {
        QueryUserInput input = QueryUserInput
                .builder()
                .firstName(firstName)
                .lastName(lastName)
                .isAdmin(isAdmin)
                .isBlocked(isBlocked)
                .pageable(pageable)
                .build();
        Either<OperationError, QueryUserResult> result = queryUser.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }
}
