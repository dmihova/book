package com.tinqin.library.book.persistence.seeders.userandsubscription;

import com.tinqin.library.book.persistence.models.*;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.book.persistence.repositories.PurchaseRepository;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
@Order(4)
public class PurchaseSeederRepository implements ApplicationRunner {

    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (purchaseRepository.count() > 0) {
            return;
        }

        List<User> users = userRepository.findAll();
        int count = users.size();
        Pageable pageable = PageRequest.of(0, count);
        List<Book> bookList = bookRepository.findAll(pageable).getContent();

        for (int i = 0; i < count; i++) {
            Purchase newPurchase = Purchase
                    .builder()
                    .book(bookList.get(i))
                    .user(users.get(i) )
                    .purchaseDate(LocalDate.now())
                    .price(bookList.get(i).getPrice())
                    .build();
            purchaseRepository.save(newPurchase);
        }

    }

}
