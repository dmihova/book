package com.tinqin.library.book.core.queryfactory;

import com.tinqin.library.book.api.operations.user.queryuser.QueryUserInput;
 import com.tinqin.library.book.persistence.models.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserQuery  {

    public static Specification<User> getSpecification( QueryUserInput filter ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFirstName() != null&& !filter.getFirstName().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("firstName"),filter.getFirstName()));
            }
            if (filter.getLastName() != null&& !filter.getLastName().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("lastName"),filter.getLastName()));
            }
            if (!(filter.getIsAdmin()==null)){
                predicates.add(criteriaBuilder.equal(root.get("isAdmin"),filter.getIsAdmin()));
            }
            if (!(filter.getIsBlocked()==null)){
                predicates.add(criteriaBuilder.equal(root.get("isBlocked"),filter.getIsBlocked()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
