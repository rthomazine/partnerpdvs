package com.github.rthomazine.partnerpdv.api.exception;

import com.github.rthomazine.partnerpdv.api.model.ErrorResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PartnerPdvExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(DuplicateKeyException e) {
        return ErrorResponse.builder()
                .errorCode("DUPLICATE_KEY")
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(DocumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(DocumentException e) {
        return ErrorResponse.builder()
                .errorCode("INVALID_DOCUMENT")
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleException(NotFoundException e) {
        return ErrorResponse.builder()
                .errorCode("NOT_FOUND")
                .message(e.getMessage())
                .build();
    }

}
