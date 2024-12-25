package com.tinqin.library.book.api.operations.rental.rerurnRental;


import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ReturnRentalResult implements ProcessorResult {

    private final UUID id;
    private final LocalDate startDate;
    private final LocalDate endDate;


}
