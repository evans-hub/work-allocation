package com.example.workallocation.Entity;

public class Model {
    private String name,email,phone,id,userid;


    public Model(String name, String email, String phone, String id, String userid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.id = id;
        this.userid = userid;
    }

    public Model() {
    }

    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
