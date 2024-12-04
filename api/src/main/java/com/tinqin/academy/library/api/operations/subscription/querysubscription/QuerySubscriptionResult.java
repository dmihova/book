package com.tinqin.academy.library.api.operations.subscription.querysubscription;


import com.tinqin.academy.library.api.base.ProcessorResult;
import com.tinqin.academy.library.api.models.subscription.SubscriptionModel;
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
