package com.tinqin.library.book.core.processors.user;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.user.UserModel;
import com.tinqin.library.book.api.operations.user.queryuser.QueryUser;
import com.tinqin.library.book.api.operations.user.queryuser.QueryUserInput;
import com.tinqin.library.book.api.operations.user.queryuser.QueryUserResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;

import com.tinqin.library.book.core.queryfactory.UserQuery;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserProcessor implements QueryUser {
    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;
    private final ConversionService conversionService;


    @Override
    public Either<OperationError, QueryUserResult> process(QueryUserInput input) {
        return getUsers(input)
                .flatMap(this::convertToModel)
                .flatMap(this::convertToOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<QueryUserResult> convertToOutput(List<UserModel> userModelList) {
        return Try.of(() -> QueryUserResult.builder()
                .userModelList(userModelList)
                .build());
    }

    private Try<List<UserModel>> convertToModel(List<User> users) {
        return Try.of(() -> {
            return users
                    .stream()
                    .map(userEntity -> conversionService.convert(userEntity, UserModel.class))
                    .toList();
        });
    }

    private Try<List<User>> getUsers(QueryUserInput input) {
        return Try.of(() -> {
            Specification<User> specification = UserQuery.getSpecification(input);
            return userRepository.findAll(specification, input.getPageable()).toList();
        });
    }

}
