package com.tinqin.library.book.api.operations.subscription.createsubscription;


import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateSubscriptionInput implements ProcessorInput {

    @NotBlank
    private String userId;

}
