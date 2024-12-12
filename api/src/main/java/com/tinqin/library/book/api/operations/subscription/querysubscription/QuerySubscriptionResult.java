package com.tinqin.library.book.api.operations.subscription.querysubscription;


import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.subscription.SubscriptionModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QuerySubscriptionResult implements ProcessorResult {

    private  List<SubscriptionModel> subscriptionList;

}
