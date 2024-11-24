package com.tinqin.academy.library.core.processors.book;


import com.tinqin.academy.library.api.models.book.BookModel;
import com.tinqin.academy.library.api.operations.querybook.QueryBook;
import com.tinqin.academy.library.api.operations.querybook.QueryBookInput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookOutput;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueryBookOperation implements QueryBook {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;

    //criteria builder - separate component to build complicated cases

    @Override
    public QueryBookOutput process(QueryBookInput input) {
        Author author =null;
        if (!input.getAuthorId().isBlank()){
              author = authorRepository
                      .findById(UUID.fromString(input.getAuthorId()))
                      .orElseThrow(() ->  new ObjectNotFoundException(input,input.getAuthorId()));
        }

       List<BookModel> bookModelList  = bookRepository
               .findBooksByAuthorAndAuthorNameAndTitle(input.getTitle(),
                       author, input.getAuthorFirstName(),input.getAuthorLastName())
                .stream()
                .map(book -> conversionService.convert(book, BookModel.class))
                .toList();

        return QueryBookOutput
                .builder()
                .bookModelList(bookModelList)
                .build();
    }


}
