package com.tinqin.library.book.api.operations.subscription.deletesubscription;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import static com.tinqin.library.book.api.ValidationMessages.SUBSCRIPTION_ID_CANNOT_BE_NULL;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class DeleteSubscriptionInput implements ProcessorInput {

    @UUID
    @NotBlank(message = SUBSCRIPTION_ID_CANNOT_BE_NULL)
    private String subscriptionId;

}
