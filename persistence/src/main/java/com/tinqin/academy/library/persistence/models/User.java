package com.tinqin.academy.library.persistence.models;

import jakarta.persistence.*;

import java.util.UUID;

//@Entity
//@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id",nullable=false)
    private UUID id;

}
