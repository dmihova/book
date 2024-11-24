package com.tinqin.academy.library.persistence.repositories;

import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;

import java.util.List;

public   interface  BookRepositoryCustom {
    List<Book> findBooksByAuthorAndAuthorNameAndTitle(String title,
                                             Author author,String authorFirstName ,String authorLastName );
}
