package com.tinqin.academy.library.core.processors.book;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.models.book.BookModel;
import com.tinqin.academy.library.api.operations.book.getbooksbyAuthor.GetBooksByAuthor;
import com.tinqin.academy.library.api.operations.book.getbooksbyAuthor.GetBooksByAuthorInput;
import com.tinqin.academy.library.api.operations.book.getbooksbyAuthor.GetBooksByAuthorResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.AUTHOR_NOT_FOUND;

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
