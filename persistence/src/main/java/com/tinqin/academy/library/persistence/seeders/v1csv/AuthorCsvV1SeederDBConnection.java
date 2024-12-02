package com.tinqin.academy.library.persistence.seeders.v1csv;


import com.tinqin.academy.library.persistence.filereader.BookSeederModelV1;
import com.tinqin.academy.library.persistence.filereader.FileReaderCsvV1;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Component
@RequiredArgsConstructor
@Order(1)
public class AuthorCsvV1SeederDBConnection implements ApplicationRunner {


    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String postgresUsername;

    @Value("${spring.datasource.password}")
    private String postgresPassword;


    String AUTHORS_QUERY = """
            INSERT INTO authors (id, first_name, last_name )
            VALUES (gen_random_uuid(), ?, ?)
            """;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Connection connection = DriverManager.getConnection(jdbcUrl, postgresUsername, postgresPassword);
        PreparedStatement psAuthor = connection.prepareStatement(AUTHORS_QUERY);
        FileReaderCsvV1 fileReaderCsvV1 = FileReaderCsvV1.loadFile("files/v1/books.csv", 20);
        List<BookSeederModelV1> batch = fileReaderCsvV1.getBatch();

        Set<String> authorsSet = new HashSet<>();

        while (!batch.isEmpty()) {
            for (BookSeederModelV1 book : batch) {
                authorsSet.add(book.getAuthorFirstName() + ";" + book.getAuthorLastName());
            }
            batch = fileReaderCsvV1.getBatch();

        }

        for (String author : authorsSet) {
            String[] authors = author.split(";");
            psAuthor.setString(1, authors[0]);
            psAuthor.setString(2, authors[1]);
            psAuthor.addBatch();
            psAuthor.clearParameters();
        }

        psAuthor.executeBatch();


    }
}

