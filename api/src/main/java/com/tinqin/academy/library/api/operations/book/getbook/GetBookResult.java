package com.tinqin.academy.library.api.operations.book.getbook;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tinqin.academy.library.api.base.ProcessorResult;
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
    private String pages;
    private BigDecimal price;
    private int stock;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

     public record GetBookAuthor(String authorId, String firstName, String lastName) {
    }
}
