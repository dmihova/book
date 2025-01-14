package com.tinqin.library.book.api.operations.book.querybook;


import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QueryBookInput implements ProcessorInput {

    private String title;

    @UUID
    private String authorId;
    private String authorFirstName;
    private String authorLastName;

    @DecimalMin(value ="0")
    private BigDecimal priceMin;
    @DecimalMin(value ="0")
    private BigDecimal priceMax;
    @DecimalMin(value ="0")
    private BigDecimal pricePerRentalMin;
    @DecimalMin(value ="0")
    private BigDecimal pricePerRentalMax;
    @DecimalMin(value ="0")
    private Integer stockMin ;
    @DecimalMin(value ="0")
    private Integer stockMax ;

    private Boolean isDeleted;

    Pageable pageable;

}
