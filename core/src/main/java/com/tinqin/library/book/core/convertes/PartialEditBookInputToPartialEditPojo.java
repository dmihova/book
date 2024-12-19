package com.tinqin.library.book.core.convertes;


import com.tinqin.library.book.api.operations.book.partialedit.PartialEditBookInput;
import com.tinqin.library.book.core.models.PartialEditPojo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PartialEditBookInputToPartialEditPojo implements Converter<PartialEditBookInput, PartialEditPojo> {

    @Override
    public PartialEditPojo convert(PartialEditBookInput input) {
        return PartialEditPojo.builder()
                .bookId(input.getBookId())
                .title(input.getTitle())
                .pages(input.getPages())
                .price(input.getPrice())
                .build();
    }
}
