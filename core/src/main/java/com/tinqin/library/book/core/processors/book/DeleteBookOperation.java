package com.tinqin.library.book.core.processors.book;


import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.book.deletebook.DeleteBook;
import com.tinqin.library.book.api.operations.book.deletebook.DeleteBookInput;
import com.tinqin.library.book.api.operations.book.deletebook.DeleteBookResult;
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
