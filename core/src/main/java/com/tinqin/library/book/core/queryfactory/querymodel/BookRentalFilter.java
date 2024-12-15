package com.tinqin.library.book.core.queryfactory.querymodel;


import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.models.User;
import lombok.Getter;

@Getter
public class BookRentalFilter {
    private final User user;
    private final Book book;
    private final Subscription subscription;


    public BookRentalFilter(User user, Book book, Subscription subscription) {
        this.user = user;
        this.book = book;
        this.subscription = subscription;
    }
}
