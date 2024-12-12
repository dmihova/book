package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}
