package com.tinqin.academy.library.api.operations.getauthor;

import com.tinqin.academy.library.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.AUTHOR_ID_CANNOT_BE_NULL;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class GetAuthorInput implements ProcessorInput {

    @UUID
    @NotBlank(message = AUTHOR_ID_CANNOT_BE_NULL)
    private String authorId;


}
