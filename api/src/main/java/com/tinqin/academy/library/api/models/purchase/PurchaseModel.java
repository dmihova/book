package com.tinqin.academy.library.api.models.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@AllArgsConstructor
@Builder
@Getter

public class PurchaseModel {
    UUID id;
    QueryPurchaseUser user;
    QueryPurchaseBook book;
    BigDecimal price;
    LocalDate purchaseDate;

    public record QueryPurchaseUser(UUID id, String firstName, String lastName) {
    }

    public record QueryPurchaseBook(UUID id, String title, String pages) {
    }
}