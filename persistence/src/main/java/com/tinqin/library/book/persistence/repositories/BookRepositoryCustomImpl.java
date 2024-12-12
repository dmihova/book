package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@AllArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<Book> findBooksByCriteriaQuery(List<Predicate> predicates) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        cq.select(book);


        Predicate[] p = predicates.toArray(new Predicate[0]);
        cq.where(predicates.toArray(new Predicate[0]));

          return entityManager
                .createQuery(cq)
                // .setFirstResult(1)
              //  .setMaxResults(50)
                .getResultList();
    }
}
