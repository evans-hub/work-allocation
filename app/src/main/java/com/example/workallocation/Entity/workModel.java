package com.example.workallocation.Entity;

public class workModel {
    private String name,email,phone,id,dep,availability,uid;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public workModel(String name, String email, String phone, String id, String dep, String availability, String uid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.id = id;
        this.dep = dep;
        this.availability = availability;
        this.uid = uid;
    }

    public workModel(String name, String email, String phone, String id, String dep, String availability) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.id = id;
        this.dep = dep;
        this.availability = availability;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public workModel() {
    }

    public String getName() {
        return name;
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

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }
}
