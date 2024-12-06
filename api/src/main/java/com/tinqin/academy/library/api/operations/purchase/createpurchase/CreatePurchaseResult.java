package com.tinqin.academy.library.api.operations.purchase.createpurchase;


import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreatePurchaseResult implements ProcessorResult {

    private final UUID id;
    private final BigDecimal price;


}
