package com.tinqin.library.book.persistence.seeders.authorsandbookscsv;


import com.tinqin.library.book.persistence.filereaderfactory.FileReaderFactory;
import com.tinqin.library.book.persistence.filereaderfactory.base.FileReader;
import com.tinqin.library.book.persistence.filereaderfactory.models.BookSeederModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Component
@RequiredArgsConstructor
@Order(1)
public class AuthorCsvV1SeederDBConnection implements ApplicationRunner {
    private final FileReaderFactory fileReaderFactory;

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

        ResultSet resultSet = connection
                .prepareStatement("SELECT COUNT(*) FROM authors")
                .executeQuery();
        resultSet.next();
        int authorCount = resultSet.getInt(1);
        if (authorCount > 0) {
            return;
        }

        PreparedStatement psAuthor = connection.prepareStatement(AUTHORS_QUERY);

        FileReader fileReader = fileReaderFactory.createCsvFileReader("files/v1csv/books.csv", 20);
        List<BookSeederModel> batch = fileReader.getBatch();

        Set<String> authorsSet = new HashSet<>();

        while (!batch.isEmpty()) {
            for (BookSeederModel book : batch) {
                authorsSet.add(book.getAuthorFirstName() + ";" + book.getAuthorLastName());
            }
            batch = fileReader.getBatch();
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

