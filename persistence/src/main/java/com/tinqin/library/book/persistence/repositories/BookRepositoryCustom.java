package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.projections.BookUUID;
import com.tinqin.library.book.persistence.repositories.filter.QueryBookFilterRepo;
import io.vavr.Value;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public  interface  BookRepositoryCustom {


    List<Book> findBooksByFilter(QueryBookFilterRepo input, Pageable pageable);

}
