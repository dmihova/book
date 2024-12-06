package com.tinqin.academy.library.persistence.repositories;

import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>, BookRepositoryCustom {

    List<Book> findByTitleLikeAndAuthors_FirstNameLikeAndAuthors_LastNameLike(String title, String firstName, String lastName);

    List<Book> findByAuthors_FirstNameLikeAndAuthors_LastNameLike(String authorFirstName, String title);

    List<Book> findByTitleLikeAndAuthors_FirstNameLike(String title, String authorFirstName);

    List<Book> findByTitleLikeAndAuthors_LastNameLike(String title, String authorLastName);

    List<Book> findByAuthors_LastNameLike(String authorLastName);

    List<Book> findByAuthors_FirstNameLike(String authorFirstName);

    List<Book> findByAuthors(List<Author> authors, Pageable pageable);

    Optional<Book> findByIdAndStockAfter(UUID uuid, int i);
}
