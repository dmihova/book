package com.tinqin.library.book.persistence.repositories;


import com.tinqin.library.book.persistence.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Optional<Author> findByLastNameAndFirstName(String lastName, String firstName);
    List<Author> findByFirstNameLikeAndLastNameLike(String firstName, String lastName);
    List<Author> findByFirstNameLike(String firstName);
    List<Author> findByLastNameLike(String lastName);


}
