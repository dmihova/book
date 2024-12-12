package com.tinqin.library.book.api.operations.subscription.getsubscription;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetSubscriptionResult implements ProcessorResult {
    private UUID subscriptionId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")

    private LocalDate endDate;
    private boolean canRent;

    private UUID userId;
    private String userName;



}

