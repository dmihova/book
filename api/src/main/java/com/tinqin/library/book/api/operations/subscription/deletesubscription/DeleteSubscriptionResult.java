package com.tinqin.library.book.api.operations.subscription.deletesubscription;

import com.tinqin.library.book.api.base.ProcessorResult;
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
