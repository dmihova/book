package com.tinqin.academy.library.persistence.repositories;


import com.tinqin.academy.library.persistence.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Collection<Author> findAllByLastNameAndFirstName(String lastName, String firstName);

    Optional<Author> findByLastNameAndFirstName(String lastName, String firstName);

    Collection<Author> findAllByLastName(String lastName);

    Collection<Author> findAllByFirstName(String firstName);
}
