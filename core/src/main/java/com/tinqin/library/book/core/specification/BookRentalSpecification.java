package com.tinqin.library.book.core.specification;

import com.tinqin.library.book.core.specification.filtermodel.BookRentalFilter;
import com.tinqin.library.book.persistence.models.BookRental;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookRentalSpecification {

    public static Specification<BookRental> getSpecification(BookRentalFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getUser() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("user"), filter.getUser()));
            }
            if (filter.getBook() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("book"), filter.getBook()));
            }
            if (filter.getSubscription() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("subscription"), filter.getSubscription()));
            }
            if (filter.getReturned() != null ) {
                if (filter.getReturned()) {
                    predicates.add(criteriaBuilder.isNotNull(root.get("endDate")));
                 }else{
                    predicates.add(criteriaBuilder.isNull(root.get("endDate")));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
