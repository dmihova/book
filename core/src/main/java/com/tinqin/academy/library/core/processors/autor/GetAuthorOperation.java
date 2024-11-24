package com.tinqin.academy.library.core.processors.autor;

import com.tinqin.academy.library.api.operations.getauthor.GetAuthor;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthorInput;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthorOutput;
import com.tinqin.academy.library.api.operations.getbook.GetBook;
import com.tinqin.academy.library.api.operations.getbook.GetBookInput;
import com.tinqin.academy.library.api.operations.getbook.GetBookOutput;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class GetAuthorOperation implements GetAuthor {

    private final AuthorRepository authorRepository;

    @Override
    public GetAuthorOutput process(GetAuthorInput input) {
        Author author = fetchAuthor(input);
        return convertAuthorToGetAuthorOutput(author);
    }


    private Author fetchAuthor(GetAuthorInput input) {
        return authorRepository
                .findById(UUID.fromString(input.getAuthorId()))
                .orElseThrow(() -> new ObjectNotFoundException(input, input.getAuthorId()));

    }

    private GetAuthorOutput convertAuthorToGetAuthorOutput(Author author) {
        return GetAuthorOutput.builder()
                .authorId(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }


}
