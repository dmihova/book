package com.tinqin.academy.library.api.operations.subscription.deletesubscription;

import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DeleteSubscriptionResult implements ProcessorResult {
    private String id;
}
