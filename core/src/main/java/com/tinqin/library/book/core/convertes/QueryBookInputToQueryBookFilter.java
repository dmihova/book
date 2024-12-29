package com.tinqin.library.book.core.convertes;


import com.tinqin.library.book.api.operations.book.querybook.QueryBookInput;
import com.tinqin.library.book.core.specification.filtermodel.QueryBookFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class QueryBookInputToQueryBookFilter implements Converter<QueryBookInput, QueryBookFilter> {
    @Override
    public QueryBookFilter convert(@NotNull QueryBookInput source) {
        return QueryBookFilter
                .builder()
                .authorFirstNameLike(source.getAuthorFirstName())
                .authorLastNameLike(source.getAuthorLastName())
                .authorId(source.getAuthorId())
                .titleLike(source.getTitle())
                .priceMax(source.getPriceMax())
                .priceMin(source.getPriceMin())
                .stockMin(source.getStockMin())
                .stockMax(source.getStockMax())
                .pricePerRentalMax(source.getPricePerRentalMax())
                .pricePerRentalMin(source.getPricePerRentalMin())
                .isDeleted(source.getIsDeleted())
                             .build();
    }
}
