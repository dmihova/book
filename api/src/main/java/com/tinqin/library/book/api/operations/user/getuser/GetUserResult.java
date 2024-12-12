package com.tinqin.library.book.api.operations.user.getuser;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserResult implements ProcessorResult {
    private String id;
    private String firstName;
    private String lastName;
    private boolean isBlocked;
    private boolean isAdmin;


}
