package com.tinqin.academy.library.core.processors.autor;


import com.tinqin.academy.library.api.models.author.AuthorModel;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthor;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthor;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthorInput;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthorOutput;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
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
    private final GetAuthor getAuthor;

    //criteria builder - separate component to build complicated cases
    @Override
    public QueryAuthorOutput process(QueryAuthorInput input) {

        List<AuthorModel> authorModelList =
                getAuthorsByParameter(authorRepository,input)
                .stream()
                .map(authorEntity -> conversionService.convert(authorEntity, AuthorModel.class))
                .toList();

        return QueryAuthorOutput
                .builder()
                .authorModelList(authorModelList)
                .build();
    }

    private Collection<Author> getAuthorsByParameter(AuthorRepository authorRepository, QueryAuthorInput input) {
        if (!input.getLastName().isBlank()&&!input.getFirstName().isBlank()) {
            return authorRepository.findAllByLastNameAndFirstName(input.getLastName(),input.getFirstName());
        } else if (!input.getLastName().isBlank()) {
            return authorRepository.findAllByLastName(input.getLastName());
        } else if (!input.getFirstName().isBlank()) {
            return authorRepository.findAllByFirstName(input.getFirstName());
        }
        else {
            return authorRepository.findAll();
        }

    }
}
