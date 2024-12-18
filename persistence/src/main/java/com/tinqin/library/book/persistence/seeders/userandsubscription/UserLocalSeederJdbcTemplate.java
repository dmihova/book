package com.tinqin.library.book.persistence.seeders.userandsubscription;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Order(2)
public class UserLocalSeederJdbcTemplate implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_USER_QUERY_TEMPLATE = """
            INSERT INTO users (id,username,first_name, last_name,is_admin,is_blocked)
            VALUES """;

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

        String countQuery = "SELECT COUNT(*) FROM users";
        Integer count = this.jdbcTemplate.queryForObject(countQuery, Integer.class);
        if (count != null && count > 0) {
            return;
        }

        String names = users
                .stream()
                .map(userLine -> userLine.split(" "))
                .map(userArray ->
                        String.format("(gen_random_uuid(), '%s', '%s', '%s',  '%s', '%s')",
                                userArray[0].trim()+userArray[1].trim(),
                                userArray[0].trim(),
                                userArray[1].trim(),
                                userArray[2].equals("0") ? "false" : "true",
                                userArray[3].equals("0") ? "false" : "true"
                        )
                )
                .collect(Collectors.joining(", "));
        String query = INSERT_USER_QUERY_TEMPLATE + names;
        jdbcTemplate.execute(query);
    }
}
