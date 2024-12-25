package com.tinqin.library.book.core.specification.filtermodel;


import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.models.User;
import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class BookRentalFilter {
    private final User user;
    private final Book book;
    private final Subscription subscription;
    private final Boolean returned;



}
