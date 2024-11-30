package com.tinqin.academy.library.persistence.repositories;

import com.tinqin.academy.library.persistence.models.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>, BookRepositoryCustom  {

    List<Book> findByAuthors_FirstName(String firstName);

    List<Book> findByAuthors_LastName(String lastName);

    List<Book> findByAuthors_FirstNameAndAuthors_LastName(String firstName, String lastName);

    List<Book> findByTitleLikeIgnoreCase(String title);

    List<Book> findByTitleOrderByTitleAsc(String title, Sort sort);

    List<Book> findByAuthors_Id(UUID id);
}
