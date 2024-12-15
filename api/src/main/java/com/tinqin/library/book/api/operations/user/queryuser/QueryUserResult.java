package com.tinqin.library.book.api.operations.user.queryuser;

import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.user.UserModel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QueryUserResult implements ProcessorResult {


    private List<UserModel> userModelList;

}
