package com.tinqin.academy.library.api.operations.rental.createrental;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateRentalResult implements ProcessorResult {

    private final UUID id;



}
