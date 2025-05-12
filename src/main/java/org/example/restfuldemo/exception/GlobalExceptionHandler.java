package org.example.restfuldemo.exception;

import org.example.restfuldemo.constants.Constants;
import org.example.restfuldemo.dto.response.ResponseData;
import org.example.restfuldemo.dto.response.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseData<?>> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(ResponseUtil.error(Arrays.asList(e.getMessage()),
                Constants.RESOURCE_NOT_FOUND
        ), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ResponseNotFoundException.class)
    public ResponseEntity<ResponseData<?>> handleResponseNotFoundException(Exception e) {
        return new ResponseEntity<>(ResponseUtil.error(Arrays.asList(e.getMessage()),
                Constants.RESPONSE_DATA_NOT_FOUND
        ), HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData<?>> handleGeneralException(Exception e) {
        return new ResponseEntity<>(ResponseUtil.error(Arrays.asList(e.getMessage()),
                Constants.GENERAL_EXCEPTION
        ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
