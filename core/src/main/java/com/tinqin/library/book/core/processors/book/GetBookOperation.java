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
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.BOOK_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class GetBookOperation implements GetBook {

    private final BookRepository bookRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, GetBookResult> process(GetBookInput input) {
        return fetchBook(input)
                .map(this::convertBookToGetBookOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<Book> fetchBook(GetBookInput input) {
        return Try.of(() -> bookRepository.findById(UUID.fromString(input.getBookId()))
                .orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND)));
    }

    private GetBookResult convertBookToGetBookOutput(Book book) {
        return GetBookResult.builder()
                .title(book.getTitle())
                .pages(book.getPages())
                .createdAt(book.getCreatedAt())
                .price(book.getPrice())
                .stock(book.getStock())
                .authors(
                        book.getAuthors()
                                .stream()
                                .map(author -> new GetBookResult.GetBookAuthor(
                                        author.getId().toString(),
                                        author.getFirstName(),
                                        author.getLastName()))
                                .toList()
                )
                .build();

    }
}
