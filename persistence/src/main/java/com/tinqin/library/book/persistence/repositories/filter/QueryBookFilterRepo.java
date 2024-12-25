package com.tinqin.library.book.persistence.repositories.filter;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QueryBookFilterRepo   {

    private String title;

    private UUID authorId;
    private String authorFirstName;
    private String authorLastName;

    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private BigDecimal pricePerRentalMin;
    private BigDecimal pricePerRentalMax;
    private Integer stockMin ;
    private Integer stockMax ;



}
