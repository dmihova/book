package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
   Page<Purchase> findAll(Specification<Purchase> specification, Pageable pageable);
}
