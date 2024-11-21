package com.tinqin.academy.library.api.operations.postbook;


import com.tinqin.academy.library.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

import static com.tinqin.academy.library.api.ValidationMessages.BOOK_TITLE_SIZE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostBookInput implements ProcessorInput {


    @NotBlank( )
    @Length(min=5,max=100,message = BOOK_TITLE_SIZE)
    private String title;
    @NotBlank( )
    private String author;
    @NotBlank( )
    private String pages;
    @NotBlank( )
    private BigDecimal price;

}
