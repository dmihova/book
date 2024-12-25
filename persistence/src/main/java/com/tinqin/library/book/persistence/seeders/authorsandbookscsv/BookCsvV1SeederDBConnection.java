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
import java.util.List;

//@Component
@RequiredArgsConstructor
@Order(2)
public class BookCsvV1SeederDBConnection implements ApplicationRunner {
    private final FileReaderFactory fileReaderFactory;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String postgresUsername;

    @Value("${spring.datasource.password}")
    private String postgresPassword;public

    String BOOKS_QUERY = """
            INSERT INTO books (id, created_on, is_deleted, pages, price, stock, title,price_per_rental )
            VALUES (gen_random_uuid(),
                    now(),
                    false,
                    ?,
                    ?,
                    0,
                    ?,
                    0 )
            """;


    String BOOKAUTHOR_QUERY = """
            INSERT INTO book_authors (book_id, author_id)
            VALUES (
                    (SELECT id
                     FROM books
                     WHERE title = ?
                        ),
                    (SELECT id
                     FROM authors
                     WHERE first_name = ?
                        AND last_name = ?)
                    )
            """;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Connection connection = DriverManager.getConnection(jdbcUrl, postgresUsername, postgresPassword);

        ResultSet resultSet = connection
                .prepareStatement("SELECT COUNT(*) FROM books")
                .executeQuery();
        resultSet.next();
        int booksCount = resultSet.getInt(1);
        if (booksCount > 0) {
            return;
        }


        PreparedStatement psBooks = connection.prepareStatement(BOOKS_QUERY);
        PreparedStatement psBookAuthors = connection.prepareStatement(BOOKAUTHOR_QUERY);
        FileReader fileReader = fileReaderFactory.createCsvFileReader("files/v1csv/books.csv", 20);

        List<BookSeederModel> batch = fileReader.getBatch();

        while (!batch.isEmpty()) {

            for (BookSeederModel book : batch) {
                psBooks.setInt(1, book.getPages());
                psBooks.setDouble(2, book.getPrice());
                psBooks.setString(3, book.getTitle());
                psBooks.addBatch();
                psBooks.clearParameters();

                psBookAuthors.setString(1,book.getTitle());
                psBookAuthors.setString(2,book.getAuthorFirstName());
                psBookAuthors.setString(3,book.getAuthorLastName());
                psBookAuthors.addBatch();
                psBookAuthors.clearParameters();
            }

            psBooks.executeBatch();
            psBookAuthors.executeBatch();
            batch = fileReader.getBatch();
        }
    }
}

