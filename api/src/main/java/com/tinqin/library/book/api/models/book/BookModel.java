package com.tinqin.library.book.api.models.book;

import com.tinqin.library.book.api.operations.book.getbook.GetBookResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class BookModel {
    private List<GetBookResult.GetBookAuthor> authors;

    private UUID id;
    private String title;
    private Integer pages;
    private BigDecimal price;
    private BigDecimal pricePerRental;
    private Integer stock;
  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Boolean isDeleted;

    public record GetBookAuthor(String authorId, String firstName, String lastName) {
    }
}
