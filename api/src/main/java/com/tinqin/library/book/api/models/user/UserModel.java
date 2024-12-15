package com.tinqin.library.book.api.models.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserModel {
    private String id;
    private String firstName;
    private String lastName;
    private boolean isBlocked;
    private boolean isAdmin;
}
