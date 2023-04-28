package com.example.ecommerce;

public class Customer {
    public int id;
    public String name,gmail,mobile,password;

    public Customer(int id, String name, String gmail, String mobile) {
        this.id = id;
        this.name = name;
        this.gmail = gmail;
        this.mobile = mobile;
    }
    public Customer( String name, String gmail, String mobile, String password) {
        this.name = name;
        this.gmail = gmail;
        this.mobile = mobile;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGmail() {
        return gmail;
    }

    public String getMobile() {
        return mobile;
    }
}
