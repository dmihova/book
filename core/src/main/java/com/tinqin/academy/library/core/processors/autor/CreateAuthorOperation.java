package com.tinqin.academy.library.core.processors.autor;

import com.tinqin.academy.library.api.operations.createauthor.CreateAuthor;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthorInput;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthorOutput;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAuthorOperation implements CreateAuthor {

    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;

    @Override
    public CreateAuthorOutput process(CreateAuthorInput input) {

        Author newAuthor = conversionService.convert(input, Author.class);

        Author persistedAuthor = authorRepository.save(newAuthor);

        return CreateAuthorOutput
                .builder()
                .id(persistedAuthor.getId())
                .build();
    }
}
