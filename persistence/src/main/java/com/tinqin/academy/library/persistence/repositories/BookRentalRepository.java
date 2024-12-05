package com.tinqin.academy.library.persistence.repositories;

import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.models.BookRental;
import com.tinqin.academy.library.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface BookRentalRepository extends JpaRepository<BookRental, UUID> {
    Optional<BookRental> findByBookAndUserAndEndDate(Book book, User user, LocalDate endDate);
}
