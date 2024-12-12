package com.tinqin.library.book.api.operations.author.createauthor;


import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
