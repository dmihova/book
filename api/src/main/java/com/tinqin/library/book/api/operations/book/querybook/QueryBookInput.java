package com.tinqin.library.book.api.operations.book.querybook;


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
public class QueryBookInput implements ProcessorInput {

    private String title;

    @UUID
    private String authorId;
    private String authorFirstName;
    private String authorLastName;

    Pageable pageable;

}
