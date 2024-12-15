package com.tinqin.library.book.persistence.seeders.userandsubscription;

import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.book.persistence.repositories.SubscriptionRepository;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Component
@RequiredArgsConstructor
@Order(3)
public class SubscriptionSeederRepository implements ApplicationRunner {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (subscriptionRepository.count() > 0) {
            return;
        }

        List<User> users = userRepository.findAll();
        List<Subscription> newSubscriptions = users
                .stream()
                .map(user -> Subscription
                        .builder()
                        .user(user)
                        .canRent(true)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(100))
                        .build())
                .toList();

        subscriptionRepository.saveAll(newSubscriptions);

    }


}
