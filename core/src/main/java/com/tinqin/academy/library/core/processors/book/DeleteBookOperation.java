package com.tinqin.academy.library.core.processors.book;


import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.deletebook.DeleteBook;
import com.tinqin.academy.library.api.operations.deletebook.DeleteBookInput;
import com.tinqin.academy.library.api.operations.deletebook.DeleteBookResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
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
public class DeleteBookOperation implements DeleteBook {
    private final BookRepository bookRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, DeleteBookResult> process(DeleteBookInput input) {

        return getBook(input)
                .flatMap(this::deleteBook)
                .toEither()
                .mapLeft(errorHandler::handle) ;
    }

    private Try<Book> getBook(DeleteBookInput input) {
        return Try.of(() -> UUID.fromString(input.getBookId()))
                .flatMap(bookId -> Try.of(() -> bookRepository.findById(bookId)
                        .orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND))));
    }
    private Try<DeleteBookResult> deleteBook(Book book) {
        book.setIsDeleted(true);
        return Try.of(() ->  bookRepository.save(book))
                .map(savedBook -> DeleteBookResult.builder()
                        .id(book.getId().toString())
                        .build());
    }
}
