package com.tinqin.library.book.core.specification;

import com.tinqin.library.book.api.operations.subscription.querysubscription.QuerySubscriptionInput;
import com.tinqin.library.book.persistence.models.Subscription;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubscriptionSpecification {
    public static Specification<Subscription> getSpecification(QuerySubscriptionInput filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getUserId() != null && !filter.getUserId().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), UUID.fromString(filter.getUserId())));
            }


            if (filter.isActive()) {
                predicates.add(criteriaBuilder.equal(root.get("canRent"), true));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), LocalDate.now()));
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}