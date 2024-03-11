package com.example.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginForm {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public UserLoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
}