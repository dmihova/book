package com.tinqin.academy.library.core.processors.user;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.user.blockuser.BlockUser;
import com.tinqin.academy.library.api.operations.user.blockuser.BlockUserInput;
import com.tinqin.academy.library.api.operations.user.blockuser.BlockUserResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.persistence.models.User;
import com.tinqin.academy.library.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.*;

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

    private Try<UUID> fetchUser(UUID id) {
        return Try.of(() -> userRepository.findById(id)
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException(USER_IS_NOT_FOUND)));
    }

    private Try<UUID> validateAdmin(UUID adminId) {
        return Try.of(() -> userRepository.findById(adminId)
                .filter(user -> !user.isAdmin())
                .map(User::getId)
                .orElseThrow(
                        () -> new IllegalArgumentException(USER_IS_ADMIN)));
    }

    private Try<UUID> validateUnblockedUser(UUID userId) {
        return Try.of(() -> userRepository.findUnblockUserId(userId)
                .orElseThrow(
                        () -> new IllegalStateException(USER_IS_ALREADY_BLOCK)));
    }

    private Try<User> blockUserAndSave(UUID userId) {
        return Try.of(() -> {
            User userToBlock = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException(USER_IS_NOT_FOUND));
            userToBlock.setBlocked(true);
            userRepository.save(userToBlock);
            return userToBlock;
        });
    }

    private BlockUserResult convertUserToBlockUserOutput(User user) {
        user.setBlocked(true);
        userRepository.save(user);
        return BlockUserResult
                .builder()
                .build();

    }
}
