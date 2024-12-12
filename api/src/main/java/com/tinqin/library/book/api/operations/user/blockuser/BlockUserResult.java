package com.tinqin.library.book.api.operations.user.blockuser;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlockUserResult implements ProcessorResult {
    private String id;

}
