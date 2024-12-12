package com.tinqin.library.book.api.operations.user.unblockuser;


import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnblockUserResult implements ProcessorResult {

    private String id;

}
