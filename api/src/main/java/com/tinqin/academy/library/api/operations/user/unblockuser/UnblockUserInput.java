package com.tinqin.academy.library.api.operations.user.unblockuser;


import com.tinqin.academy.library.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.USER_ID_CANNOT_BE_NULL;


@Getter
@Builder
@AllArgsConstructor
@ToString
public class UnblockUserInput implements ProcessorInput {

  @UUID
  @NotBlank(message = USER_ID_CANNOT_BE_NULL)
  private String id;

}
