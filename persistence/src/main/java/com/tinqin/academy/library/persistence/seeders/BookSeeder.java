package com.tinqin.academy.library.persistence.seeders;

import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


@Component
@RequiredArgsConstructor
public class BookSeeder implements ApplicationRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //book by book with book repository save all
        //if sql ->jdbc template with entity manager +read from file

        //only if table is empty
         if (bookRepository.count() > 0) {
              return;
         }
        String fileWithPath = "rest/src/main/resources/files/books.csv";
        String currentRelativePath = Paths.get("").toAbsolutePath().toString();


        Random rand = new Random();

        List<Book> newBooks = Files.readAllLines(Path.of(fileWithPath))
                .stream()
                .filter(textLine -> !textLine.isBlank())
                .map(textLine -> textLine.split(";"))
                .map(bookArray -> Book
                        .builder()
                        .title(bookArray[2].trim())
                        .author(bookArray[0].trim())
                        .pages(String.valueOf( rand.nextInt(50,1000)))
                        .price(BigDecimal.valueOf(rand.nextInt(5,50)))
                        .pricePerRental(BigDecimal.valueOf(rand.nextInt(1,5)))
                        .stock(rand.nextInt(1,10))
                        .createdAt(LocalDateTime.now())
                        .build())
                .toList();


        bookRepository.saveAll(newBooks);


    }
}
