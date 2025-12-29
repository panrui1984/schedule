package com.yakx.web.domain.vo.login;

import lombok.Data;

@Data
public class LoginResult {
    private Long userId;
    private String username;
    private String token;
    private String userType;
}
