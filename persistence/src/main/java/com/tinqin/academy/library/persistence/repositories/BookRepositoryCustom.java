package com.tinqin.academy.library.persistence.repositories;

import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public   interface  BookRepositoryCustom {

    public List<Book> findBooksByCriteriaQuery(List<Predicate> predicates) ;
}
