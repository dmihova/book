package com.tinqin.academy.library.api.operations.purchase.querypurchase;


import com.tinqin.academy.library.api.base.ProcessorResult;
import com.tinqin.academy.library.api.models.purchase.PurchaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryPurchaseResult implements ProcessorResult {

    private  List<PurchaseModel> purchaseModels;

}
