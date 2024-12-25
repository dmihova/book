package com.tinqin.library.book.api.operations.subscription.getsubscription;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetSubscriptionResult implements ProcessorResult {

    private  UUID subscriptionId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean canRent;
    private List<GetSubscriptionRental> rentals;
    private GetSubscriptionUser user;
    @Builder
    @Getter
    public static class GetSubscriptionUser  {
        private  String id;
        private String firstName;
        private String lastName;
    }
    @Builder
    @Getter
    public static class GetSubscriptionRental {
        private String id;
        private String bookId;
        private String title;
        LocalDate startDate;
        LocalDate endDate;


    }


}

