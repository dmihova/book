package com.tinqin.library.book.core.processors.user;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.user.unblockuser.UnblockUser;
import com.tinqin.library.book.api.operations.user.unblockuser.UnblockUserInput;
import com.tinqin.library.book.api.operations.user.unblockuser.UnblockUserResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUnblockProcessor implements UnblockUser {

    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, UnblockUserResult> process(UnblockUserInput input) {
        return fetchUser(UUID.fromString(input.getId()))
                .flatMap(this::isBlockedUser)
                .flatMap(this::unblockUser)
                .map(this::convertUserToBlockUserOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<User> fetchUser(UUID id) {
        return Try.of(() -> userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(USER_IS_NOT_FOUND)));
    }

    private Try<User> isBlockedUser(User user) {
        return user.getIsBlocked() ?
                Try.success(user) :
                Try.failure(new BusinessException(USER_IS_NOT_BLOCKED));
    }


    private Try<User> unblockUser(User userToUnblock) {
        return Try.of(() -> {
            userToUnblock.setIsBlocked(false);
            userRepository.save(userToUnblock);
            return userToUnblock;
        });
    }

    private UnblockUserResult convertUserToBlockUserOutput(User userEntity) {
        return UnblockUserResult
                .builder()
                .id(userEntity.getId().toString())
                .build();

    }

}
