package com.example.user.projectbringback.data;

public class User {
    private String userId;
    private String password;
    private String email;
    private String phone;
    private String birth;
    private String gender;
    private String[] taste;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirth() {
        return birth;
    }

    public String getGender() {
        return gender;
    }

    public String[] getTaste() {
        return taste;
    }

    public void setTaste(String[] taste) {
        this.taste = taste;
    }
}