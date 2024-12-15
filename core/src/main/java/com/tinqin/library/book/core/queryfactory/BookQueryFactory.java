package com.tinqin.library.book.core.queryfactory;

import com.tinqin.library.book.core.queryfactory.base.QueryFactory;
import com.tinqin.library.book.persistence.models.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class BookQueryFactory  implements QueryFactory {

    private final EntityManager entityManager;
    private final CriteriaBuilder cb;
    private final CriteriaQuery<Book> cq;
    private final Root<Book> book;

    public BookQueryFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
        cb = entityManager.getCriteriaBuilder();
        cq = cb.createQuery(Book.class);
        book = cq.from(Book.class);
       // cq.select(book);

    }

    public Optional<Predicate> buildAuthorFirstName(String authorFirstName) {
        if (!authorFirstName.isEmpty()) {
            return Optional.of(cb.equal(book.get("author").get("firstName"), authorFirstName));
        }
        return Optional.empty();
    }

    public Optional<Predicate> buildAuthorLastName(String authorLastName) {
        if (!authorLastName.isEmpty()) {
            return Optional.of(cb.equal(book.get("author").get("lastName"), authorLastName));
        }
        return Optional.empty();
    }

    public Optional<Predicate> buildAuthorId(String authorId) {
        if (!authorId.isEmpty()) {
            return Optional.of(cb.equal(book.get("author").get("id"), authorId));
        }
        return Optional.empty();
    }

    public Optional<Predicate> buildTitleLike(String title) {
        if (!title.isEmpty()) {
            return Optional.of(cb.like(book.get("title"), title + "%"));
        }
        return Optional.empty();
    }

    public Optional<Predicate> buildIsDeleted(Boolean deleted) {
        if (!deleted) {
            Predicate isDeleted = cb.equal(book.get("isDeleted"), false);
            Predicate isDeletedNull = cb.isNull (book.get("isDeleted") );
            Predicate or = cb.or(isDeleted, isDeletedNull);
            return Optional.of(or);
        } else {
            return Optional.of(cb.equal(book.get("isDeleted"), true));
        }

    }


}
