package com.tinqin.academy.library.api.operations.purchase.getpurchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tinqin.academy.library.api.base.ProcessorResult;
import com.tinqin.academy.library.api.operations.rental.getrental.GetBookRentalResult;
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

