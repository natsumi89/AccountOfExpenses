package com.example.AccountOfExpenses.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public class UserForm {
    private Integer id;

    @NotBlank(message = "名前を入力してください")
    @Size(min=1,max=10, message="名前は10文字以内で入力してください")
    private String username;

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスの形式が不正です")
    private String email;

    @NotBlank(message = "名前を入力してください")
    @Size(min=8,max=10, message="名前は8文字以上10文字以内で入力してください")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "名前を入力してください") @Size(min = 1, max = 10, message = "名前は10文字以内で入力してください") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "名前を入力してください") @Size(min = 1, max = 10, message = "名前は10文字以内で入力してください") String username) {
        this.username = username;
    }

    public @NotBlank(message = "メールアドレスを入力してください") @Email(message = "メールアドレスの形式が不正です") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "メールアドレスを入力してください") @Email(message = "メールアドレスの形式が不正です") String email) {
        this.email = email;
    }

    public @NotBlank(message = "名前を入力してください") @Size(min = 8, max = 10, message = "名前は8文字以上10文字以内で入力してください") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "名前を入力してください") @Size(min = 8, max = 10, message = "名前は8文字以上10文字以内で入力してください") String password) {
        this.password = password;
    }
}
