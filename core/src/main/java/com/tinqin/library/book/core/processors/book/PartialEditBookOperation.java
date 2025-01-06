package com.tinqin.library.book.core.processors.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.book.partialedit.PartialEditBook;
import com.tinqin.library.book.api.operations.book.partialedit.PartialEditBookInput;
import com.tinqin.library.book.api.operations.book.partialedit.PartialEditBookResult;

import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.core.models.PartialEditPojo;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.tinqin.library.book.api.ValidationMessages.AUTHOR_NOT_FOUND;
import static com.tinqin.library.book.api.ValidationMessages.BOOK_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class PartialEditBookOperation implements PartialEditBook {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ErrorHandler errorHandler;
    private final ObjectMapper objectMapper;
    private final ConversionService conversionService;


    @Override
    public Either<OperationError, PartialEditBookResult> process(PartialEditBookInput input) {

        return Try.of(() -> {
                    Book book = bookRepository
                            .findById(UUID.fromString(input.getBookId()))
                            .orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND));

                    PartialEditPojo convertedInput = conversionService.convert(input, PartialEditPojo.class);

                    JsonNode existingBookDB = objectMapper.valueToTree(book);
                    JsonNode fieldsToUpdateFromInput = objectMapper
                            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                            .valueToTree(convertedInput);

                    JsonMergePatch patchFromInput = JsonMergePatch.fromJson(fieldsToUpdateFromInput);
                    JsonNode patchedBookMergedData = patchFromInput.apply(existingBookDB);
                    Book updatedBookMergedData = objectMapper.treeToValue(patchedBookMergedData, Book.class);


                    List<Author> newAuthorList = fetchAuthors(input);
                    if (!newAuthorList.isEmpty()) {
                        updatedBookMergedData.setAuthors(newAuthorList);
                    }

                    Book persistedBook = bookRepository.save(updatedBookMergedData);

                    return PartialEditBookResult
                            .builder()
                            .bookId(persistedBook.getId())
                            .build();
                })
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private List<Author> fetchAuthors(PartialEditBookInput input) {
        if (input.getAuthorIds() == null || input.getAuthorIds().isEmpty()) {
            return new ArrayList<Author>();
        }
        return input.getAuthorIds()
                .stream()
                .map(UUID::fromString)
                .map(authorRepository::findById)
                .map(a -> a.orElseThrow(() -> new BusinessException(AUTHOR_NOT_FOUND)))
                .toList();

    }
}
