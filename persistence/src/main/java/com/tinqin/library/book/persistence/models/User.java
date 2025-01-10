package com.tinqin.library.book.persistence.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username" ,unique = true,nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "is_admin"  )
    private Boolean isAdmin;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @Column(name = "is_activated")
    private Boolean isActivated;
}
