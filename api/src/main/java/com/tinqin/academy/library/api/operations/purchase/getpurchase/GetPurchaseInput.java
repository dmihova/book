package com.tinqin.academy.library.api.operations.purchase.getpurchase;

import com.tinqin.academy.library.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class GetPurchaseInput implements ProcessorInput {

    @UUID
    @NotBlank
    private String purchaseId;


}
