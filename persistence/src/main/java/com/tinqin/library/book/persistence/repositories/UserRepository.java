package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT u.id FROM User u WHERE u.id = :userId AND u.isBlocked = false")
    Optional<UUID> findUnblockUserId(@Param("userId") UUID userId);

}
