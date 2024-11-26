package com.tinqin.academy.library.api.errors;

import com.tinqin.academy.library.api.enumerations.MessageLevel;
import org.springframework.http.HttpStatus;

public interface OperationError {

    HttpStatus getStatus();

    String getErrorCode();

    String getMessage();

    MessageLevel getMessageLevel();
}
