package com.tinqin.academy.library.persistence.models;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "pages", nullable = false)
    private String pages;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "price_per_rental", nullable = false)
    private BigDecimal pricePerRental;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_on" )
    private LocalDateTime updatedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

   // @ManyToOne
   // @JoinColumn(name="autor_id")
   // private User author1;

//    @ManyToMany
//    @JoinTable(
//             name="book_categories",
//             joinColumns = @JoinColumn (name ="book_id"),
//             inverseJoinColumns = @JoinColumn(name="category_id"))
//    private List<Category> categories;







}
