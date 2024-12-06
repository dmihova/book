package com.tinqin.academy.library.persistence.repositories;

import com.tinqin.academy.library.persistence.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}
