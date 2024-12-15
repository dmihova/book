package com.tinqin.library.book.api.operations.user.queryuser;


import com.tinqin.library.book.api.base.ProcessorInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;


@Getter
@Builder
@AllArgsConstructor
@ToString
public class QueryUserInput implements ProcessorInput {

  private String firstName;
  private String lastName;
  private Boolean isBlocked;
  private Boolean isAdmin;

  Pageable pageable;

}
