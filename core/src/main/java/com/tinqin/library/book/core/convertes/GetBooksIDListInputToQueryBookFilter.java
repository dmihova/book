package com.tinqin.library.book.core.convertes;


import com.tinqin.library.book.api.operations.book.getbooksidlist.GetBooksIdListInput;
import com.tinqin.library.book.core.specification.filtermodel.QueryBookFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GetBooksIDListInputToQueryBookFilter implements Converter<GetBooksIdListInput, QueryBookFilter> {
    @Override
    public QueryBookFilter convert(@NotNull GetBooksIdListInput source) {
        return QueryBookFilter
                .builder()
                .authorId(source.getAuthorId())
                .title(source.getTitle())
                .pageMin(source.getPageMin())
                .pageMax(source.getPageMax())
                .createDateMin(source.getCreateDateMin())
                .createDateMax(source.getCreateDateMax())
                .build();
    }
}
