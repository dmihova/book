package com.tinqin.library.book.api.operations.purchase.querypurchase;


import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.purchase.PurchaseModel;
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
