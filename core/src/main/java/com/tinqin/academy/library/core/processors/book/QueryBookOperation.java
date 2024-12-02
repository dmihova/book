package com.tinqin.academy.library.core.processors.book;


import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.models.book.BookModel;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthorInput;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthorResult;
import com.tinqin.academy.library.api.operations.querybook.QueryBook;
import com.tinqin.academy.library.api.operations.querybook.QueryBookInput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.core.queryfactory.BookQueryFactory;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tinqin.academy.library.api.ValidationMessages.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class QueryBookOperation implements QueryBook {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
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


    private Try<QueryBookResult> convertToQueryBookOutput(List<BookModel> bookModels) {
        return Try.of(() -> QueryBookResult.builder()
                .bookModelList(bookModels)
                .build());
    }

    private Try<Author> getAuthor(UUID authorId) {
        return Try.of(() -> authorRepository.findById(authorId)
                .orElseThrow(() -> new BusinessException(AUTHOR_NOT_FOUND)));
    }

    private Try<List<BookModel>> getBooks(QueryBookInput input) {
        return Try.of(() -> getBooksByParameter(input)
                .stream()
                .map(book -> conversionService.convert(book, BookModel.class))
                .toList());
    }

    private Collection<Book> getBooksByParameter(QueryBookInput input) {
        if (!input.getAuthorFirstName().isEmpty()
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
        }

    }
}




