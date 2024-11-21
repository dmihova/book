package com.tinqin.academy.library.persistence.seeders;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BookSeeder implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // code to load books if db table is empty
        //book by book with bookrepository saveall

        //if sql ->jdbc template with entity manager +read from file
    }
}
