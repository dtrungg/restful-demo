package org.example.restfuldemo.dto.request;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String userName;
    private String passWord;
}
