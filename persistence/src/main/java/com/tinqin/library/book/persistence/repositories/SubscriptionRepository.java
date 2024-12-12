package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Optional<Subscription> findByUserAndEndDateGreaterThanEqual(User user, LocalDate endDate);

    List<Subscription> findByUser(User user);

    List<Subscription> findByUser_Id(UUID id);


    Optional<Subscription> findByUserAndEndDateGreaterThanEqualAndCanRent(User user, LocalDate endDate, Boolean canRent);
}
