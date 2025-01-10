package com.tinqin.library.book.core.convertes;

import com.tinqin.library.book.api.models.user.UserModel;
import com.tinqin.library.book.persistence.models.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserModel implements Converter<User, UserModel> {


    @Override
    public UserModel convert(User source) {
        return UserModel
                .builder()
                .id(String.valueOf(source.getId()))
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .isAdmin(source.getIsAdmin())
                .isBlocked(source.getIsBlocked())
                .build();
    }
}
