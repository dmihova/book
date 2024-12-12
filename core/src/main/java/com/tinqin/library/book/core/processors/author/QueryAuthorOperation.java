package com.tinqin.library.book.core.processors.author;


import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.author.AuthorModel;
import com.tinqin.library.book.api.operations.author.queryauthor.QueryAuthor;
import com.tinqin.library.book.api.operations.author.queryauthor.QueryAuthorInput;
import com.tinqin.library.book.api.operations.author.queryauthor.QueryAuthorResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAuthorOperation implements QueryAuthor {
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;


    @Override
    public Either<OperationError, QueryAuthorResult> process(QueryAuthorInput input) {
        return findAuthors(input)
                .map(authorModelList -> QueryAuthorResult
                        .builder()
                        .authorModelList(authorModelList)
                        .build()
                )
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<List<AuthorModel>> findAuthors( QueryAuthorInput input)  {
        return Try.of(() ->  getAuthorsByParameter(input)
                .stream()
                .map(authorEntity -> conversionService.convert(authorEntity, AuthorModel.class))
                .toList());
    }

    private Collection<Author> getAuthorsByParameter(QueryAuthorInput input) {
        if (!input.getLastName().isBlank()&&!input.getFirstName().isBlank()) {
            return authorRepository.findByFirstNameLikeAndLastNameLike(input.getFirstName()+"%",input.getLastName()+"%");
        } else if (!input.getLastName().isBlank()) {
            return authorRepository.findByLastNameLike(input.getLastName()+"%");
        } else if (!input.getFirstName().isBlank()) {
            return authorRepository.findByFirstNameLike(input.getFirstName()+"%");
        }
        else {
            return authorRepository.findAll();
        }

    }
}
