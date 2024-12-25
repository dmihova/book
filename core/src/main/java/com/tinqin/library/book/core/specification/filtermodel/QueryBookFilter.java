package com.tinqin.library.book.core.specification.filtermodel;


import com.tinqin.library.book.api.base.ProcessorInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QueryBookFilter implements ProcessorInput {

    private String title;


    private String authorId;
    private String authorFirstName;
    private String authorLastName;

    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private BigDecimal pricePerRentalMin;
    private BigDecimal pricePerRentalMax;
    private Integer stockMin ;
    private Integer stockMax ;
    private Boolean isDeleted;



}
