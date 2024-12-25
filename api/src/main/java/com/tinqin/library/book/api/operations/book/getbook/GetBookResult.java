package com.tinqin.library.book.api.operations.book.getbook;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetBookResult implements ProcessorResult {

    private List<GetBookAuthor> authors;
    private String title;
    private Integer pages;

    private BigDecimal price;
    private BigDecimal pricePerRental;
    private Integer stock;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Boolean isDeleted;


     public record GetBookAuthor(String authorId, String firstName, String lastName) {
    }
}
