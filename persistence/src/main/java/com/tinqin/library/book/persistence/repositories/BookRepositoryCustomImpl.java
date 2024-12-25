package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.filter.BookQueryFactory;
import com.tinqin.library.book.persistence.repositories.filter.QueryBookFilterRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final EntityManager entityManager;
    private final BookQueryFactory bookQueryFactory;


    @Override
    public List<Book> findBooksByFilter(QueryBookFilterRepo input, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        cq.select(book);
        cq.orderBy(cb.asc(book.get("title")));

        Predicate [] predicates = Stream.of(
                        bookQueryFactory.buildTitleLike(cb, book, input.getTitle()),
                        bookQueryFactory.buildAuthorId(cb, book, input.getAuthorId()),
                        bookQueryFactory.buildAuthorFirstName(cb, book, input.getAuthorFirstName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toArray(Predicate[]::new);

        cq.where(predicates);

        return entityManager
                .createQuery(cq)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}
