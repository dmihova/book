package com.tinqin.library.book.api.operations.rental.getrental;


import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class GetBookRentalResult implements ProcessorResult {

    private String id;

   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
