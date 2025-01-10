package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.projections.BookUUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>, BookRepositoryCustom {

    List<Book> findByAuthors(List<Author> authors, Pageable pageable);

    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

    List<Book> findAll(Specification<Book> specification );

    @Query("SELECT id AS id  FROM Book")
    Set<BookUUID> findAllUUID()  ;




}
