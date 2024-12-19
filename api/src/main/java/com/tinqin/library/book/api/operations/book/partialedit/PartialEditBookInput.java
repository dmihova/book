package com.tinqin.library.book.api.operations.book.partialedit;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqin.library.book.api.base.ProcessorInput;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;

import static com.tinqin.library.book.api.ValidationMessages.BOOK_TITLE_SIZE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder (toBuilder = true)
public class PartialEditBookInput implements ProcessorInput {
    @UUID
    @Hidden
    @JsonIgnore
    private String bookId;

    @Length(min=5,max=100,message = BOOK_TITLE_SIZE)
    private String title;

    private List<String> authorIds;


    private String pages;

    @DecimalMin(value ="1.0")
    private BigDecimal price;

}


