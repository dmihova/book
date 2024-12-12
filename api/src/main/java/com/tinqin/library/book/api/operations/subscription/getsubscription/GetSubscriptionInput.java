package com.tinqin.library.book.api.operations.subscription.getsubscription;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class GetSubscriptionInput implements ProcessorInput {

    @UUID
    @NotBlank
    private String subscriptionId;


}
