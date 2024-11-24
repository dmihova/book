package com.tinqin.academy.library.persistence.repositories;

import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final EntityManager entityManager;



    @Override
    public List<Book> findBooksByAuthorAndAuthorNameAndTitle(String title,
                                                    Author author, String authorFirstName ,
                                                             String authorLastName ) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();

        if (author != null) {
            predicates.add(cb.equal(book.get("author"), author));
        } else {
            if (!authorFirstName.isEmpty()) {
                predicates.add(cb.equal(book.get("author").get("firstName"), authorFirstName));
            }
            if (!authorLastName.isEmpty()) {
                predicates.add(cb.equal(book.get("author").get("lastName"), authorLastName));
            }
        }
        if (!title.isEmpty()) {
            predicates.add(cb.like(book.get("title"), title + "%"));
        }

      //  predicates.add(cb.equal(book.get("isDeleted"), false));


        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager
                .createQuery(cq)
               // .setFirstResult(1)
                .setMaxResults(50)
                .getResultList();


    }
}
