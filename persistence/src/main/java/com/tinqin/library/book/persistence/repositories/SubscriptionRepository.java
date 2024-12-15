package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.models.User;
import io.vavr.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Optional<Subscription> findByUserAndEndDateGreaterThanEqual(User user, LocalDate endDate);

    List<Subscription> findByUser(User user);

    List<Subscription> findByUser_Id(UUID id);

    Page<Subscription> findByUser_Id(UUID id, Pageable pageable);


    Optional<Subscription> findByUserAndEndDateGreaterThanEqualAndCanRent(User user, LocalDate endDate, Boolean canRent);

    Page<Subscription> findByUser_IdAndCanRentAndEndDateGreaterThanEqualAndStartDateLessThanEqual(UUID id, Boolean canRent, LocalDate endDate, LocalDate startDate, Pageable pageable);

    Page<Subscription> findByCanRentAndEndDateGreaterThanEqualAndStartDateLessThanEqual(  Boolean canRent, LocalDate endDate, LocalDate startDate, Pageable pageable);


    Page<Subscription> findAll(Specification<Subscription> specification, Pageable pageable);
}
