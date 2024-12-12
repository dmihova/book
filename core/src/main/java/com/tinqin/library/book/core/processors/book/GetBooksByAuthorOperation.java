package com.tinqin.library.book.core.processors.book;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.book.BookModel;
import com.tinqin.library.book.api.operations.book.getbooksbyAuthor.GetBooksByAuthor;
import com.tinqin.library.book.api.operations.book.getbooksbyAuthor.GetBooksByAuthorInput;
import com.tinqin.library.book.api.operations.book.getbooksbyAuthor.GetBooksByAuthorResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GetBooksByAuthorOperation implements GetBooksByAuthor {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, GetBooksByAuthorResult> process(GetBooksByAuthorInput input) {
        return Try.of(() -> {
                            Author author = authorRepository
                                    .findById(UUID.fromString(input.getAuthorId()))
                                    .orElseThrow(() -> new BusinessException(AUTHOR_NOT_FOUND));

                            List<Book> pagedBooks = bookRepository
                                    .findByAuthors(List.of( author), input.getPageable());

                            List<BookModel> bookModels = pagedBooks
                                    .stream()
                                    .map(bookEntity -> conversionService.convert(bookEntity, BookModel.class))
                                    .toList();

                            return GetBooksByAuthorResult.builder()
                                    .bookModels(bookModels)
                                    .build();
                        }
                )
                .toEither()
                .mapLeft(errorHandler::handle);

    }
}
