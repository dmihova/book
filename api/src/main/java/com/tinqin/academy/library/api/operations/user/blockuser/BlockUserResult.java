package com.tinqin.academy.library.api.operations.user.blockuser;

import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlockUserResult implements ProcessorResult {
    private String id;

}
