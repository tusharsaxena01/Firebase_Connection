package com.bitandroid.firebase;

public class UserData {

    String uuid;

    String name;
    String mobileNo;
    String email;
    String city;
    String password;

    public UserData() {
    }

    public UserData(String uuid, String name, String mobileNo, String email, String city, String password) {
        this.uuid = uuid;
        this.name = name;
        this.mobileNo = mobileNo;
        this.email = email;
        this.city = city;
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
