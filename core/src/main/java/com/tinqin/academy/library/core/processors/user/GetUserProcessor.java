package com.tinqin.academy.library.core.processors.user;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.user.getuser.GetUser;
import com.tinqin.academy.library.api.operations.user.getuser.GetUserInput;
import com.tinqin.academy.library.api.operations.user.getuser.GetUserResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.User;
import com.tinqin.academy.library.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.USER_IS_NOT_FOUND;

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
                .isBlocked(user.isBlocked())
                .isAdmin(user.isAdmin())
                .build();
    }


}
