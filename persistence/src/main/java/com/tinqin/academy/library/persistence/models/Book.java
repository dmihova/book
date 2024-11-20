package com.tinqin.academy.library.persistence.models;


import com.tinqin.academy.library.persistence.enums.BookStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id",nullable=false)
    private UUID id;

    @Column(name="title",nullable=false)
    private String title;

    @Column(name="author",nullable=false)
    private String author;

    @Column(name="book_status",nullable=false)
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    List<BookRental> rentals;

   // @ManyToOne
   // @JoinColumn(name="autor_id")
   // private User author1;

    @ManyToMany
    @JoinTable(
             name="book_categories",
             joinColumns = @JoinColumn (name ="book_id"),
             inverseJoinColumns = @JoinColumn(name="category_id"))
    private List<Category> categories;







}
