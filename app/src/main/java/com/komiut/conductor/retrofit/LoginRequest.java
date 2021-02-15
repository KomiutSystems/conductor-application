package com.komiut.conductor.retrofit;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
