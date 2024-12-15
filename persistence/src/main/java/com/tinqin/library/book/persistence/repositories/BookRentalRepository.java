package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.BookRental;
import com.tinqin.library.book.persistence.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface BookRentalRepository extends JpaRepository<BookRental, UUID> {
    Optional<BookRental> findByBookAndUserAndEndDate(Book book, User user, LocalDate endDate);

    Page<BookRental> findAll(Specification<BookRental> specification, Pageable pageable);
}
