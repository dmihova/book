package com.tinqin.library.book.persistence.seeders.userandsubscription;

import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.BookRental;
import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.repositories.BookRentalRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.book.persistence.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@Component
@RequiredArgsConstructor
@Order(4)
public class RentalsSeederRepository implements ApplicationRunner {

    private final SubscriptionRepository subscriptionRepository;
    private final BookRentalRepository bookRentalRepository;
    private final BookRepository bookRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (bookRentalRepository.count() > 0) {
            return;
        }

        List<Subscription> subscriptions = subscriptionRepository.findAll();
        int count = subscriptions.size();
        Pageable pageable = PageRequest.of(0, count);
        List<Book> books = bookRepository.findAll(pageable).getContent();


        for (int i = 0; i < count; i++) {
            BookRental rental = BookRental
                    .builder()
                    .book(books.get(i))
                    .user(subscriptions.get(i).getUser())
                    .subscription(subscriptions.get(i))
                    .startDate(LocalDate.now())
                    .build();
            bookRentalRepository.save(rental);
        }
        createMoreRental(subscriptions, count, books);
        createMoreRental(subscriptions, count, books);
        createMoreRental(subscriptions, count, books);
    }

    private void createMoreRental(List<Subscription> subscriptions, int count, List<Book> bookList) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            BookRental rental = BookRental
                    .builder()
                    .user(subscriptions.get(i).getUser())
                    .book(bookList.get(random.nextInt(0, count - 1)))
                    .user(subscriptions.get(i).getUser())
                    .subscription(subscriptions.get(i))
                    .startDate(LocalDate.now())
                    .build();
            bookRentalRepository.save(rental);
        }
    }

}
