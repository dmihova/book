package com.tinqin.library.book.core.convertes;

import com.tinqin.library.book.api.models.subscription.SubscriptionModel;
import com.tinqin.library.book.persistence.models.Subscription;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionEntityToSubscriptionModel
            implements Converter<Subscription, SubscriptionModel> {


    @Override
    public SubscriptionModel convert(Subscription source) {
        return SubscriptionModel
                .builder()
                .subscriptionId(source.getId())
                .canRent(source.getCanRent())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .userId(source.getUser().getId())
                .userName(source.getUser().getLastName())
                .build();
    }
}