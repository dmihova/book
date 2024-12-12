package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Book;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public   interface  BookRepositoryCustom {

    public List<Book> findBooksByCriteriaQuery(List<Predicate> predicates) ;
}
