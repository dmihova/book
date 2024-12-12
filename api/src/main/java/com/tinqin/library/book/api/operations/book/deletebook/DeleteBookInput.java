package com.tinqin.library.book.api.operations.book.deletebook;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import static com.tinqin.library.book.api.ValidationMessages.BOOK_ID_CANNOT_BE_NULL;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class DeleteBookInput implements ProcessorInput {

    @UUID
    @NotBlank(message = BOOK_ID_CANNOT_BE_NULL)
    private String bookId;

}
