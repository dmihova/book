package com.tinqin.library.book.core.processors.user;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.user.blockuser.BlockUser;
import com.tinqin.library.book.api.operations.user.blockuser.BlockUserInput;
import com.tinqin.library.book.api.operations.user.blockuser.BlockUserResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.*;

@Service
@RequiredArgsConstructor
public class UserBlockProcessor implements BlockUser {

    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;


    @Override
    public Either<OperationError, BlockUserResult> process(BlockUserInput input) {
        return fetchUser(UUID.fromString(input.getId()))
                .flatMap(this::validateAdmin)
                .flatMap(this::validateUnblockedUser)
                .flatMap(this::blockUserAndSave)
                .map(this::convertUserToBlockUserOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<User> fetchUser(UUID id) {
        return Try.of(() -> userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(USER_IS_NOT_FOUND)));
    }

    private Try<User> validateAdmin(User userEntity) {
        return !userEntity.isAdmin() ?
                Try.success(userEntity) :
                Try.failure(new BusinessException(USER_IS_ADMIN));
    }

    private Try<User> validateUnblockedUser(User userEntity) {
        return !userEntity.isBlocked() ?
                Try.success(userEntity) :
                Try.failure(new BusinessException(USER_IS_ALREADY_BLOCK));
    }

    private Try<User> blockUserAndSave(User userToBlock) {
        return Try.of(() -> {
            userToBlock.setBlocked(true);
            return userRepository.save(userToBlock);
        });
    }

    private BlockUserResult convertUserToBlockUserOutput(User user) {
        return BlockUserResult
                .builder()
                .id(user.getId().toString())
                .build();

    }
}
