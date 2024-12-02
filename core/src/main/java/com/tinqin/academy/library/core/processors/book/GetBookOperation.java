package com.tinqin.academy.library.core.processors.book;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.getbook.GetBook;
import com.tinqin.academy.library.api.operations.getbook.GetBookInput;
import com.tinqin.academy.library.api.operations.getbook.GetBookResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.BOOK_NOT_FOUND;


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
                .orElseThrow(() -> new RuntimeException(BOOK_NOT_FOUND)));
    }

    private GetBookResult convertBookToGetBookOutput(Book book) {
        return GetBookResult.builder()
                .title(book.getTitle())
                .pages(book.getPages())
                .authors(
                        book.getAuthors()
                                .stream()
                                .map(author->new GetBookResult.GetBookAuthor(
                                        author.getId().toString(),
                                        author.getFirstName(),
                                        author.getLastName()))
                                .toList()
                )
                .build();

    }


}
