package com.tinqin.library.book.core.processors.book;


import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.book.BookWithAuthorsModel;
import com.tinqin.library.book.api.operations.book.querybook.QueryBook;
import com.tinqin.library.book.api.operations.book.querybook.QueryBookInput;
import com.tinqin.library.book.api.operations.book.querybook.QueryBookResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.core.queryfactory.BookQuery;
import com.tinqin.library.book.core.queryfactory.PurchaseQuery;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.Purchase;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tinqin.library.book.api.ValidationMessages.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class QueryBookOperation implements QueryBook {
    private final BookRepository bookRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    //criteria builder - separate component to build complicated cases

    @Override
    public Either<OperationError, QueryBookResult> process(QueryBookInput input) {
        return getBooks(input)
                .flatMap(this::convertToQueryBookOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<QueryBookResult> convertToQueryBookOutput(List<BookWithAuthorsModel> bookModels) {
        return Try.of(() -> QueryBookResult.builder()
                .bookModelList(bookModels)
                .build());
    }

    private Try<List<BookWithAuthorsModel>> getBooks(QueryBookInput input) {
        return Try.of(() -> getBooksByParameter(input)
                .stream()
                .map(book -> conversionService.convert(book, BookWithAuthorsModel.class))
                .toList());
    }

    private Collection<Book> getBooksByParameter(QueryBookInput input) {
        Specification<Book> specification = BookQuery.getSpecification(input);
        return bookRepository
                .findAll(specification, input.getPageable()).toList();



     /*   if (!input.getAuthorFirstName().isEmpty()
                && !input.getAuthorLastName().isEmpty()
                && !input.getTitle().isEmpty()) {
            return bookRepository.findByTitleLikeAndAuthors_FirstNameLikeAndAuthors_LastNameLike(
                    input.getTitle()+"%",input.getAuthorFirstName()+"%",input.getTitle()+"%");
        } else if (!input.getAuthorFirstName().isEmpty()&&!input.getAuthorLastName().isEmpty()) {
            return bookRepository.findByAuthors_FirstNameLikeAndAuthors_LastNameLike(input.getAuthorFirstName()+"%",input.getTitle()+"%");
        } else if ( !input.getTitle().isEmpty()&&!input.getAuthorFirstName().isEmpty() ) {
            return bookRepository.findByTitleLikeAndAuthors_FirstNameLike (input.getTitle()+"%",input.getAuthorFirstName()+"%" );
        } else if ( !input.getTitle().isEmpty()&&!input.getAuthorLastName().isEmpty() ) {
            return bookRepository.findByTitleLikeAndAuthors_LastNameLike(input.getTitle()+"%", input.getAuthorLastName()+"%");
        } else if (  !input.getAuthorLastName().isEmpty() ) {
                return bookRepository.findByAuthors_LastNameLike (input.getAuthorLastName()+"%" );
        }  else if (  !input.getAuthorFirstName ().isEmpty() ) {
            return bookRepository.findByAuthors_FirstNameLike (input.getAuthorFirstName() +"%");
        }
        else {
            return bookRepository.findAll();
        }*/


    }
}



