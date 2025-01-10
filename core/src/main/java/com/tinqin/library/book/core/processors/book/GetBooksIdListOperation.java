package com.tinqin.library.book.core.processors.book;


import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.book.getbooksidlist.GetBooksIdList;
import com.tinqin.library.book.api.operations.book.getbooksidlist.GetBooksIdListInput;
import com.tinqin.library.book.api.operations.book.getbooksidlist.GetBooksIdListResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.specification.BookSpecification;
import com.tinqin.library.book.core.specification.filtermodel.QueryBookFilter;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.projections.BookUUID;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBooksIdListOperation implements GetBooksIdList {
    private final BookRepository bookRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;


    @Override
    public Either<OperationError, GetBooksIdListResult> process(GetBooksIdListInput input) {
        return getBooks(input)
                .flatMap(this::convertToQueryBookOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<GetBooksIdListResult> convertToQueryBookOutput(List<UUID> uuids) {
        return Try.of(() -> GetBooksIdListResult.builder()
                .uuids(uuids)
                .count(uuids.size())
                .build());
    }


    private Try<List<UUID>> getBooks(GetBooksIdListInput input) {
        return Try.of(() -> {
                    if ((input.getTitle() == null ||input.getTitle().isEmpty())                            &&
                            input.getAuthorId() == null &&
                            input.getCreateDateMin() == null && input.getCreateDateMax() == null &&
                            input.getSizeMin() == null && input.getSizeMax() == null) {
                        return bookRepository.findAllUUID()
                                .stream()
                                .map(BookUUID::getId)
                                .toList();

                    } else {
                        QueryBookFilter filter = conversionService.convert(input, QueryBookFilter.class);
                        Specification<Book> specification = BookSpecification.getSpecification(filter);
                        return bookRepository.findAll(specification)
                                .stream()
                                .map(Book::getId)
                                .toList();

                    }
                }
        );
    }
}








