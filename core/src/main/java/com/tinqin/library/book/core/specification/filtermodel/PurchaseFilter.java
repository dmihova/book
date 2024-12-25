package com.tinqin.library.book.core.specification.filtermodel;

import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PurchaseFilter {
    private final User user;
    private final Book book;


}
