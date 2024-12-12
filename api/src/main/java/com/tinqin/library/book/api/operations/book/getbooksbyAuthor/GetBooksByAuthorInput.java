package com.tinqin.library.book.api.operations.book.getbooksbyAuthor;


import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Pageable;

import static com.tinqin.library.book.api.ValidationMessages.AUTHOR_ID_CANNOT_BE_NULL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetBooksByAuthorInput implements ProcessorInput {

    @UUID
    @NotBlank(message = AUTHOR_ID_CANNOT_BE_NULL)
    private String authorId;

    Pageable pageable;

}
