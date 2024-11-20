package com.tinqin.academy.library.persistence.models;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="book_rentals")
public class BookRental {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id",nullable=false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    @OneToOne
    @JoinColumn(name="reader_id")
    private User reader;

}
