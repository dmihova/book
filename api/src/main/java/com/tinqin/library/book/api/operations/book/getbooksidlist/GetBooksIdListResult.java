package com.tinqin.library.book.api.operations.book.getbooksidlist;


import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class GetBooksIdListResult implements ProcessorResult {

    private  List<UUID> uuids;
    private Integer count;

}
