package com.tinqin.library.book.core.specification;

import com.tinqin.library.book.core.specification.filtermodel.QueryBookFilter;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookSpecification {

    public static Specification<Book> getSpecification(QueryBookFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            // book
            if (filter.getTitleLike()!=null&&  !filter.getTitleLike().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("title"), filter.getTitleLike() + "%"));
            }
            if (filter.getTitle()!=null&&!filter.getTitle().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("title"), filter.getTitle()  ));
            }
            if (filter.getPriceMin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getPriceMin()));
            }

            if (filter.getPriceMax() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getPriceMax()));
            }

            if (filter.getPricePerRentalMin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerRental"), filter.getPricePerRentalMin()));
            }
            if (filter.getPricePerRentalMax() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerRental"), filter.getPricePerRentalMax()));
            }

            if (filter.getStockMin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("stock"), filter.getStockMin()));
            }
            if (filter.getStockMax() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("stock"), filter.getStockMax()));
            }
            if (filter.getIsDeleted() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isDeleted"), filter.getIsDeleted()));
            }

            if (filter.getPageMin() != null&&filter.getPageMin()>0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("page"), filter.getPageMin()));
            }

            if (filter.getPageMax() != null&&filter.getPageMax()>0) {
              predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("page"), filter.getPageMax()));
            }
            if (filter.getCreateDateMin() != null ) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdOn"), filter.getCreateDateMin()));
            }
            if (filter.getCreateDateMax() != null ) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdOn"), filter.getCreateDateMax()));
            }




            /// author n:n link
            if (!((filter.getAuthorId() == null || filter.getAuthorId().isEmpty()) &&
                    (filter.getAuthorFirstName() == null || filter.getAuthorFirstName().isEmpty()) &&
                    (filter.getAuthorLastName() == null || filter.getAuthorLastName().isEmpty())&&
                    (filter.getAuthorFirstNameLike() == null || filter.getAuthorFirstNameLike().isEmpty()) &&
                    (filter.getAuthorLastNameLike() == null || filter.getAuthorLastNameLike().isEmpty()))
            ) {

                Join<Book, Author> author = root.join("authors");
                if (filter.getAuthorId() != null && !filter.getAuthorId().isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("authors").get("id"), UUID.fromString(filter.getAuthorId())));
                } else {
                    if (filter.getAuthorFirstNameLike() != null && !filter.getAuthorFirstNameLike().isEmpty()) {
                        predicates.add(criteriaBuilder.like(root.get("authors").get("firstName"), filter.getAuthorFirstNameLike() + "%"));
                    }

                    if (filter.getAuthorFirstName() != null && !filter.getAuthorFirstName().isEmpty()) {
                        predicates.add(criteriaBuilder.equal(root.get("authors").get("firstName"), filter.getAuthorFirstName() ));
                    }

                    if (filter.getAuthorLastNameLike() != null && !filter.getAuthorLastNameLike().isEmpty()) {
                        predicates.add(criteriaBuilder.like(root.get("authors").get("lastName"), filter.getAuthorLastNameLike() + "%"));
                    }
                    if (filter.getAuthorLastName() != null && !filter.getAuthorLastName().isEmpty()) {
                        predicates.add(criteriaBuilder.equal(root.get("authors").get("lastName"), filter.getAuthorLastName()));
                    }
                }
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
