package com.tinqin.library.book.api.operations.purchase.getpurchase;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetPurchaseResult implements ProcessorResult {
    private UUID purchaseId;
    private LocalDate purchaseDate;
    private BigDecimal price;

    private GetPurchaseBook book;
    private GetPurchaseUser user;


    public record GetPurchaseBook(String id, String title) {
    }

    public record GetPurchaseUser(String id, String firstName, String lastName) {
    }


}

