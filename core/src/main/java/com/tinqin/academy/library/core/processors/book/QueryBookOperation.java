package com.tinqin.academy.library.core.processors.book;


import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.models.book.BookModel;
import com.tinqin.academy.library.api.operations.querybook.QueryBook;
import com.tinqin.academy.library.api.operations.querybook.QueryBookInput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.core.queryfactory.BookQueryFactory;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tinqin.academy.library.api.ValidationMessages.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class QueryBookOperation implements QueryBook {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;
    private final BookQueryFactory queryFactory;

    //criteria builder - separate component to build complicated cases

    @Override
    public Either<OperationError, QueryBookResult> process(QueryBookInput input) {
        return getCriteria(input)
                .flatMap(this::findBooks)
                .flatMap(this::convertToQueryBookOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<QueryBookResult> convertToQueryBookOutput(List<BookModel> bookModels) {
        return Try.of(() -> QueryBookResult.builder()
                        .bookModelList(bookModels)
                        .build());
          }



    private Try<List<BookModel>> findBooks(List<Predicate> predicates) {
        return Try.of(() -> bookRepository
                .findBooksByCriteriaQuery(predicates)
                .stream()
                .map(book -> conversionService.convert(book, BookModel.class))
                .toList()
                );
    }


    private Try<Author> getAuthor(UUID authorId) {
        return Try.of(() -> authorRepository.findById(authorId)
                       .orElseThrow(() -> new BusinessException(AUTHOR_NOT_FOUND)));
    }


    private Try<List<Predicate>> getCriteria(QueryBookInput input) {
        return Try.of(() -> {
            List<Optional<Predicate>> predicates = new ArrayList<>();
            predicates.add(queryFactory.buildAuthorFirstName(input.getAuthorFirstName()));
            predicates.add(queryFactory.buildAuthorLastName(input.getAuthorLastName()));
            predicates.add(queryFactory.buildAuthorId(input.getAuthorId()));
            predicates.add(queryFactory.buildTitleLike(input.getTitle()));
          //  predicates.add(queryFactory.buildIsDeleted(false));
            return predicates
                    .stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        });
    }
}




