package com.tinqin.academy.library.api.operations.subscription.querysubscription;


import com.tinqin.academy.library.api.base.ProcessorInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QuerySubscriptionInput implements ProcessorInput {

    @UUID
    private String userId;

    private boolean active;

}
