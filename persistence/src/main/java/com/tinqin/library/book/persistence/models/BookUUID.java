package com.tinqin.library.book.persistence.models;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Book}
 */
@Value
public class BookUUID implements Serializable {
    UUID id;
}