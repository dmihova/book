package com.tinqin.library.book.persistence.seeders.userandsubscription;

import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
@Order(2)
public class UserSeederRepository implements ApplicationRunner {
    private final UserRepository userRepository;

    private final List<String> users = List.of(
            "Admin Admin 1 0",
            "Pepi Petrov 0 0",
            "Niki Todorov 0 0",
            "Joko Todorov 0 0",
            "Alice Nikolova 0 0",
            "Mika Dobreva 0 0",
            "Sami Dodov 0 0",
            "Niki Nikolov 0 0"

    );

    @Override
    public void run(ApplicationArguments args) throws Exception {


        long count = userRepository.count();
        if (count > 0) {
            return;
        }

        List<User> newUsers = users
                .stream()
                .map(userLine -> userLine.split(" "))
                .map(userArray ->
                        User
                                .builder()
                                .email(userArray[0].trim()+userArray[1].trim() + "@abv.bg")
                                .firstName(userArray[0].trim())
                                .lastName(userArray[1].trim())
                                .isActivated(true)
                                .isBlocked(false)
                                .isAdmin(!userArray[2].equals("0"))
                                .username(userArray[0].trim() + userArray[1].trim())
                                .build()

                )
                .toList();

        userRepository.saveAll(newUsers);
    }
}
