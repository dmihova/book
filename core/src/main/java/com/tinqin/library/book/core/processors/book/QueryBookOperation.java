package com.tinqin.library.book.core.processors.book;


import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.book.BookModel;
import com.tinqin.library.book.api.operations.book.querybook.QueryBook;
import com.tinqin.library.book.api.operations.book.querybook.QueryBookInput;
import com.tinqin.library.book.api.operations.book.querybook.QueryBookResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.specification.BookSpecification;
import com.tinqin.library.book.core.specification.filtermodel.QueryBookFilter;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.book.persistence.repositories.filter.QueryBookFilterRepo;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QueryBookOperation implements QueryBook {
    private final BookRepository bookRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;


    @Override
    public Either<OperationError, QueryBookResult> process(QueryBookInput input) {
        return getBooks(input)
                .flatMap(this::convertToQueryBookOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<QueryBookResult> convertToQueryBookOutput(List<BookModel> bookModels) {
        return Try.of(() -> QueryBookResult.builder()
                .bookModelList(bookModels)
                .build());
    }

    private Try<List<BookModel>> getBooks(QueryBookInput input) {
        return Try.of(() -> getBooksByParameter(input)
                .stream()
                .map(book -> conversionService.convert(book, BookModel.class))
                .toList());
    }

    private Collection<Book> getBooksByParameter(QueryBookInput input) {
        QueryBookFilter filter =  conversionService.convert(input, QueryBookFilter.class);
        Specification<Book> specification = BookSpecification.getSpecification(filter);
        return bookRepository
                .findAll(specification, input.getPageable()).toList();
    }


}



