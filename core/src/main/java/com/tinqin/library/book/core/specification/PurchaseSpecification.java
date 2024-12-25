package com.tinqin.library.book.core.specification;

import com.tinqin.library.book.core.specification.filtermodel.PurchaseFilter;
import com.tinqin.library.book.persistence.models.Purchase;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PurchaseSpecification {

    public static Specification<Purchase> getSpecification(PurchaseFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getUser() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("user"), filter.getUser()));
            }
            if (filter.getBook() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("book"), filter.getBook()));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
