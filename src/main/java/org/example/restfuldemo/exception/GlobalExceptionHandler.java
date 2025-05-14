package org.example.restfuldemo.exception;

import lombok.extern.log4j.Log4j2;
import org.example.restfuldemo.constants.Constants;
import org.example.restfuldemo.dto.response.ResponseData;
import org.example.restfuldemo.dto.response.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseData<?>> handleResourceNotFoundException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseUtil.error(Arrays.asList(e.getMessage()), Constants.RESOURCE_NOT_FOUND));
    }

    @ExceptionHandler(ResponseNotFoundException.class)
    public ResponseEntity<ResponseData<?>> handleResponseNotFoundException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseUtil.error(Arrays.asList(e.getMessage()), Constants.RESPONSE_DATA_NOT_FOUND));
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ResponseData<?>> handleResourceAlreadyExistException(ResourceAlreadyExistException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ResponseUtil.error(Arrays.asList(e.getMessage()), Constants.RESOURCE_ALREADY_EXIST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<?>> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseUtil.error(Arrays.asList(e.getFieldErrors()
                                .stream()
                                .map(error -> {
                                    log.error(error.getDefaultMessage());
                                    return error.getDefaultMessage();
                                }).toArray(String[]::new)),
                        Constants.RESPONSE_DATA_NOT_FOUND
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData<?>> handleGeneralException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseUtil.error(Arrays.asList(e.getMessage()), Constants.GENERAL_EXCEPTION));
    }

}
