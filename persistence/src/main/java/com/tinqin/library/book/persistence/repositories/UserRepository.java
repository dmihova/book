package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    @Query(value = "SELECT u.id FROM User u WHERE u.id = :userId AND u.isBlocked = false")
    Optional<UUID> findUnblockUserId(@Param("userId") UUID userId);

    List<User> findAll(Specification<User> specification);
    Page<User> findAll(Specification<User> specification, Pageable pageable);
    List<User> findAll( );

}
