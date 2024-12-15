package com.tinqin.library.book.core.queryfactory;

import com.tinqin.library.book.api.operations.book.querybook.QueryBookInput;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookQuery {

    public static Specification<Book> getSpecification(QueryBookInput filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!((filter.getAuthorId() == null || filter.getAuthorId().isEmpty()) &&
                    (filter.getAuthorFirstName() == null || !filter.getAuthorFirstName().isEmpty()) &&
                    (filter.getAuthorLastName() == null || !filter.getAuthorLastName().isEmpty()))) {
            }

            Join<Book, Author> author = root.join("authors");
            if (filter.getAuthorId() != null && !filter.getAuthorId().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("authors").get("id"), UUID.fromString(filter.getAuthorId())));
            } else {
                if (filter.getAuthorFirstName() != null && !filter.getAuthorFirstName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("authors").get("firstName"), filter.getAuthorFirstName()+ "%"));
                }
                if (filter.getAuthorLastName() != null && !filter.getAuthorLastName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("authors").get("lastName"), filter.getAuthorLastName()+ "%"));
                }
            }




            if (!filter.getTitle().isEmpty()) {
                return criteriaBuilder.like(root.get("title"), filter.getTitle() + "%");
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
                ;
    }
}
