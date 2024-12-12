package com.tinqin.library.book.api.operations.rental.queryrental;


import com.tinqin.library.book.api.base.ProcessorInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QueryBookRentalInput implements ProcessorInput {

    @UUID
    private String userId;

    @UUID
    private String subscriptionId;

    @UUID
    private String bookId;

    Pageable pageable;


}
