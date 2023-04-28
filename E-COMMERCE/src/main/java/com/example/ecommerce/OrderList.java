package com.example.ecommerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class OrderList {

    private SimpleIntegerProperty quantity;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleStringProperty order_date;

    public OrderList(int id, String name, double price,String order_date) {
        this.quantity = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.order_date = new SimpleStringProperty(order_date);
    }

    // creating query , to get products
    public static ObservableList<OrderList> getAllProducts(Customer customer) {
        String query = "select orders.id,product.name,orders.quantity,orders.order_date,ordered_status,product.price from orders join product on orders.product_id = product.id where customer_id ="+customer.id+";";
        return fetchProductsData(query); // gets the products data from db

    }

    //Connecting to database and getting products data from db
    public static ObservableList<OrderList> fetchProductsData(String query) {
        ObservableList<OrderList> data = FXCollections.observableArrayList();
        DbConnection con = new DbConnection();
        try {
            ResultSet rs = con.getQueryTable(query);
            while (rs.next()) {
                OrderList orders = new OrderList(rs.getInt("quantity"), rs.getString("name"), rs.getDouble("price"),rs.getString("order_date"));
                data.add(orders);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
        return null;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }

    public String getOrder_date() {
        return order_date.get();
    }
}



