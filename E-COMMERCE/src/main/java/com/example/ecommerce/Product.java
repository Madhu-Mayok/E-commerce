package com.example.ecommerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public Product(int id, String name, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }
    // creating query , to get products
    public static ObservableList<Product> getAllProducts()
    {
        String query = "SELECT id,name,price FROM product";
        return fetchProductsData(query); // gets the products data from db

    }
    public static ObservableList<Product> getSearchData(String searchText)
    {
        String query = "select * from product where name like '%"+searchText+"%';";
        return fetchProductsData(query);
    }
    //Connecting to database and getting products data from db
    public static ObservableList<Product> fetchProductsData(String query)
    {
        ObservableList<Product> data = FXCollections.observableArrayList();
        DbConnection con = new DbConnection();
        try {
            ResultSet rs = con.getQueryTable(query);
            while(rs.next())
            {
                Product products = new Product(rs.getInt("id"),rs.getString("name"),rs.getDouble("price"));
                data.add(products);
            }
            return data;
        }
        catch(Exception e){
            e.printStackTrace();;
        }
        return null;
    }
    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }

}
