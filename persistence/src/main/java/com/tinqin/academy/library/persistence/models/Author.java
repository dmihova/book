package com.tinqin.academy.library.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter

@Entity
@Table(name="authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id",nullable=false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private Book books;

}
