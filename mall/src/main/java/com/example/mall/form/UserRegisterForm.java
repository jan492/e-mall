package com.example.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterForm {

    // @NotBlank: 用于String判断空格
    // @NotNull: 用于基本类型
    // @NotEmpty: 用于集合
//    @NotBlank(message = "用户名不能为空")
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    public UserRegisterForm(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
