package com.tinqin.academy.library.api.operations.purchase.createpurchase;


import com.tinqin.academy.library.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreatePurchaseInput implements ProcessorInput {

    @NotBlank
    @UUID
    private String userId;

    @NotBlank
    @UUID
    private String bookId;

}
