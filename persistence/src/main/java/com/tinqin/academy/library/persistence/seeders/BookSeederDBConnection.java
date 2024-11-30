package com.tinqin.academy.library.persistence.seeders;


import com.tinqin.academy.library.persistence.filereader.BookSeederModel;
import com.tinqin.academy.library.persistence.filereader.FileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

//@Component
@RequiredArgsConstructor
@Order(2)
public class BookSeederDBConnection implements ApplicationRunner {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String postgresUsername;

    @Value("${spring.datasource.password}")
    private String postgresPassword;

    String BOOKS_QUERY = """
            INSERT INTO books (id, created_at, is_deleted, pages, price, stock, title,price_per_rental )
            VALUES (gen_random_uuid(),
                    now(),
                    false,
                    ?,
                    ?,
                    0,
                    ?,
                    0 )
            """;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Connection connection = DriverManager.getConnection(jdbcUrl, postgresUsername, postgresPassword);
        PreparedStatement ps = connection.prepareStatement(BOOKS_QUERY);
        FileReader fileReader = FileReader.loadFile("files/books.csv", 2);

        List<BookSeederModel> batch = fileReader.getBatch();

        while (!batch.isEmpty()) {
            for (BookSeederModel book : batch) {
                ps.setInt(1, book.getPages());
                ps.setDouble(2, book.getPrice());
                ps.setString(3, book.getTitle());

                ps.addBatch();
                ps.clearParameters();
            }

            ps.executeBatch();
            batch = fileReader.getBatch();
        }
    }
}

