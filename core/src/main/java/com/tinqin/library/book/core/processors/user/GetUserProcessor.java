package com.tinqin.library.book.core.processors.user;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.user.getuser.GetUser;
import com.tinqin.library.book.api.operations.user.getuser.GetUserInput;
import com.tinqin.library.book.api.operations.user.getuser.GetUserResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.USER_IS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GetUserProcessor implements GetUser {

    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, GetUserResult> process(GetUserInput input) {
        return fetchUser(UUID.fromString(input.getUserId()))
                .map(this::convertUserToGetUserOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<User> fetchUser(UUID id) {
        return Try.of(() -> userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(USER_IS_NOT_FOUND)));
    }

    private GetUserResult convertUserToGetUserOutput(User user) {
        return GetUserResult
                .builder()
                .id(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isBlocked(user.getIsBlocked())
                .isAdmin(user.getIsAdmin())
                .build();
    }


}
