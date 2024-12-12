package com.tinqin.library.book.api.errors;

import com.tinqin.library.book.api.enumerations.MessageLevel;
import org.springframework.http.HttpStatus;

public interface OperationError {

    HttpStatus getStatus();

    String getErrorCode();

    String getMessage();

    MessageLevel getMessageLevel();
}
