package com.example.ecommerce;

import java.sql.ResultSet;
import java.sql.SQLException;

public class signUp {
    public static boolean customerLogin(Customer customer )  {
        DbConnection dbConnection = new DbConnection();
        String query = "INSERT INTO customer(name,email,mobile,password) values("
                +"'"+customer.name+"'"+","+
                "'"+customer.gmail+"'"+","+
                "'"+customer.mobile+"'"+","+
                "'"+customer.password+"'"+")";
        DbConnection con = new DbConnection();
        int rs = con.updateDatabase(query);
        return rs != 0;
    }
}