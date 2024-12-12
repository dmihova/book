package com.tinqin.library.book.persistence.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

@Entity
@Table(name="book_rentals")
public class BookRental   {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id",nullable=false)
    private UUID id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="subscription_id", nullable=false)
    @ToString.Exclude
    private Subscription subscription;

    @Column(name = "start_date", nullable = false)
    @CreationTimestamp
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true )
    private LocalDate endDate;
}
