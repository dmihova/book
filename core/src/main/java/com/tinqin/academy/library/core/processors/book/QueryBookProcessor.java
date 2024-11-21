package com.tinqin.academy.library.core.processors.book;


import com.tinqin.academy.library.api.model.book.BookModel;
import com.tinqin.academy.library.api.operations.getbook.GetBookOutput;
import com.tinqin.academy.library.api.operations.querybook.QueryBook;
import com.tinqin.academy.library.api.operations.querybook.QueryBookInput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookResult;
import com.tinqin.academy.library.core.convertes.BookEntityToBookModel;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryBookProcessor implements QueryBook {
    private final BookRepository bookRepository;
    private final ConversionService conversionService;

    //criteria builder - separate component to build complicated cases
    @Override
    public QueryBookResult process(QueryBookInput input) {
        List<BookModel> list = bookRepository
                .findAll()
                .stream()
                .map(book -> conversionService.convert(book, BookModel.class))
                .toList();

        return QueryBookResult
                .builder()
                .books(list)
                .build();
    }
}
