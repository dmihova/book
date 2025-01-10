package com.tinqin.library.book.api.operations.book.getbooksidlist;


import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetBooksIdListInput implements ProcessorInput {

    private String title;

    @UUID
    private String authorId;

    private Integer sizeMin ;
    private Integer sizeMax ;

    private LocalDate createDateMin ;
    private LocalDate createDateMax ;


}
