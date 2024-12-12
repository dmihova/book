package com.tinqin.library.book.api.errors;

import com.tinqin.library.book.api.enumerations.MessageLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BeError implements OperationError {
    private HttpStatus status;
    private String errorCode;
    private String message;
    private MessageLevel messageLevel;


    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public MessageLevel getMessageLevel() {
        return messageLevel;
    }
}