package com.tinqin.academy.library.api.operations.rental.rerurnRental;


import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ReturnRentalResult implements ProcessorResult {

    private final UUID id;



}
