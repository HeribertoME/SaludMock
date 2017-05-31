package com.hmelizarraraz.saludmock.data.api.model;

/**
 * Created by heriberto on 26/05/17.
 */

public class Affiliate {
    private String id;
    private String name;
    private String address;
    private String gender;
    private String token;

    public Affiliate(String id, String name, String address, String gender, String token) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
