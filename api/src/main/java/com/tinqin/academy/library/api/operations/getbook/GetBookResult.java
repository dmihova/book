package com.tinqin.academy.library.api.operations.getbook;

import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetBookResult implements ProcessorResult {
    private List<GetBookAuthor> authors;
    private String title;
    private String pages;


    public GetBookResult buildAuthors( List<String[]> authorList ) {
        authors = authorList
                .stream()
                .map(a-> new GetBookAuthor(a[0], a[1], a[2]))
                .toList();
        return this;
    }


    static class GetBookAuthor {
        private final String authorId;
        private final String firstName;
        private final String lastName;

        public GetBookAuthor(String authorId, String firstName, String lastName) {
            this.authorId = authorId;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
