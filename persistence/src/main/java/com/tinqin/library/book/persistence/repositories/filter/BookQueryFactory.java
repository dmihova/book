package com.tinqin.library.book.persistence.repositories.filter;

import com.tinqin.library.book.persistence.repositories.filter.base.QueryFactory;
import com.tinqin.library.book.persistence.models.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class BookQueryFactory  implements QueryFactory {


    public Optional<Predicate> buildAuthorFirstName(CriteriaBuilder cb,Root<Book> book,String authorFirstName) {
        if (!authorFirstName.isEmpty()) {
            return Optional.of(cb.equal(book.get("author").get("firstName"), authorFirstName));
        }
        return Optional.empty();
    }

    public Optional<Predicate> buildAuthorLastName(CriteriaBuilder cb,Root<Book> book,String authorLastName) {
        if (!authorLastName.isEmpty()) {
            return Optional.of(cb.equal(book.get("author").get("lastName"), authorLastName));
        }
        return Optional.empty();
    }

    public Optional<Predicate> buildAuthorId(CriteriaBuilder cb, Root<Book> book, UUID authorId) {
        if (authorId!=null) {
            return Optional.of(cb.equal(book.get("author").get("id"), authorId));
        }
        return Optional.empty();
    }

    public Optional<Predicate> buildTitleLike(CriteriaBuilder cb,Root<Book> book,String title) {
        if (!title.isEmpty()) {
            return Optional.of(cb.like(book.get("title"), title + "%"));
        }
        return Optional.empty();
    }

    public Optional<Predicate> buildIsDeleted(CriteriaBuilder cb,Root<Book> book,Boolean deleted) {
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
