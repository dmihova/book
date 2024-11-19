package com.tinqin.academy.library.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookStatus {
    PUBLISHED("Published"),
    UNPUBLISHED("Unpublished");

    private final String code;

    @Override
    public String toString() {
        return code;
    }
}
