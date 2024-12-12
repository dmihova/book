package com.tinqin.library.book.api.operations.book.createbook;


import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

import static com.tinqin.library.book.api.ValidationMessages.BOOK_TITLE_SIZE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateBookInput implements ProcessorInput {


    @NotBlank
    @Length(min=5,max=100,message = BOOK_TITLE_SIZE)
    private String title;


    private List<String> authorIds;

    @NotBlank
    private String pages;

    @DecimalMin(value ="1.0")
    private BigDecimal price;

}
