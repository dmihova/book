package com.tinqin.library.book.api.operations.purchase.getpurchase;

import com.tinqin.library.book.api.base.ProcessorInput;
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
