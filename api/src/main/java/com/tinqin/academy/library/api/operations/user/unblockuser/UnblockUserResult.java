package com.tinqin.academy.library.api.operations.user.unblockuser;


import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnblockUserResult implements ProcessorResult {

    private String id;

}
