package com.example.belajar_android_pengenalan_material_design.model;

public class UsersData {
    private String userId;
    private String username;
    private String status;
    private String email;
    private String gender;
    private String mobile;
    private String imageUrl;

    /*Create Constructor*/

    public UsersData(String userId, String username, String status, String email, String gender, String mobile, String imageUrl) {
        this.userId = userId;
        this.username = username;
        this.status = status;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
        this.imageUrl = imageUrl;
    }
    /*set default constructor to be defined must fit 2 simple constraints */
    public UsersData() {
    }

    public UsersData(String username, String email, String imageUrl) {
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public void setImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
