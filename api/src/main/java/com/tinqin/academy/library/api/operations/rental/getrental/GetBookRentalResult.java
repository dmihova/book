package com.tinqin.academy.library.api.operations.rental.getrental;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tinqin.academy.library.api.base.ProcessorResult;
import com.tinqin.academy.library.api.models.book.BookWithAuthorsModel;
import com.tinqin.academy.library.api.operations.book.getbook.GetBookResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetBookRentalResult implements ProcessorResult {

    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private GetBookRentalBook book;
    private GetBookRentalUser user;
    private GetBookRentalSubscription subscription;


    public record GetBookRentalBook(String id, String title) {
    }

    public record GetBookRentalUser(String id, String firstName, String lastName) {
    }

    public record GetBookRentalSubscription(String id, LocalDate endDate) {
    }
}
