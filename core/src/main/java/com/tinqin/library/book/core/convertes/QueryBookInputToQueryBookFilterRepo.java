package com.tinqin.library.book.core.convertes;


import com.tinqin.library.book.api.operations.book.querybook.QueryBookInput;
import com.tinqin.library.book.persistence.repositories.filter.QueryBookFilterRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueryBookInputToQueryBookFilterRepo implements Converter<QueryBookInput, QueryBookFilterRepo> {
    @Override
    public QueryBookFilterRepo convert(@NotNull QueryBookInput source) {
        return QueryBookFilterRepo
                .builder()
                .authorFirstName(source.getAuthorFirstName())
                .authorLastName(source.getAuthorLastName())
                .authorId((source.getAuthorId() != null&&!source.getAuthorId().isEmpty()) ? UUID.fromString(source.getAuthorId()) : null)
                .title(source.getTitle())
                .priceMax(source.getPriceMax())
                .priceMin(source.getPriceMin())
                .stockMin(source.getStockMin())
                .stockMax(source.getStockMax())
                .pricePerRentalMax(source.getPricePerRentalMax())
                .pricePerRentalMin(source.getPricePerRentalMin())
                .build();
    }
}
