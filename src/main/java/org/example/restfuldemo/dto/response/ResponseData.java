package org.example.restfuldemo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseData<T> {
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
    private long timestamp;
}
