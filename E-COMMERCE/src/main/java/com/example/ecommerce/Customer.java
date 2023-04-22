package com.example.ecommerce;

public class Customer {
    private int id;
    private String name,gmail,mobile;

    public Customer(int id, String name, String gmail, String mobile) {
        this.id = id;
        this.name = name;
        this.gmail = gmail;
        this.mobile = mobile;
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
