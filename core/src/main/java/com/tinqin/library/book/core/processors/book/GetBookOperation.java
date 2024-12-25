package com.tinqin.library.book.core.processors.book;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.book.getbook.GetBook;
import com.tinqin.library.book.api.operations.book.getbook.GetBookInput;
import com.tinqin.library.book.api.operations.book.getbook.GetBookResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.BOOK_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class GetBookOperation implements GetBook {

    private final BookRepository bookRepository;
    private final ErrorHandler errorHandler;
    private final ConversionService conversionService;

    @Override
    public Either<OperationError, GetBookResult> process(GetBookInput input) {
        return fetchBook(input)
                .map(book -> conversionService.convert(book, GetBookResult.class))
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<Book> fetchBook(GetBookInput input) {
        return Try.of(() -> bookRepository.findById(UUID.fromString(input.getBookId()))
                .orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND)));
    }
}
