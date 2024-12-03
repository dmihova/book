package com.tinqin.academy.library.api.operations.author.createauthor;


import com.tinqin.academy.library.api.base.ProcessorInput;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

import static com.tinqin.academy.library.api.ValidationMessages.BOOK_TITLE_SIZE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateAuthorInput implements ProcessorInput {

    @NotBlank
    @Length(min=1,max=100)
    private String firstName;

    @NotBlank
    @Length(min=1,max=100)
    private String lastName;


}
