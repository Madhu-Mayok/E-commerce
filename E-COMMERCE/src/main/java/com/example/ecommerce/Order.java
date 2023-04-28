package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    // to place single order
    public static boolean placeOrder(Customer customer,Product product)
    {
        String groupOrderId = "select max(group_order_id)+1 id from orders";
        DbConnection con = new DbConnection();
        try{
            ResultSet rs = con.getQueryTable(groupOrderId);
            if(rs.next()) // if true then we place the order
            {
                String placeOrder = "INSERT into orders(group_order_id,product_id,customer_id) values("+rs.getInt("id")+","+product.getId()+","+customer.getId()+")";
                return con.updateDatabase(placeOrder) != 0; // means order placed
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    // to place multiple orders
    public static int placeMultipleOrder(Customer customer, ObservableList<Product> productList)
    {
        String groupOrderId = "select max(group_order_id)+1 id from orders";
        DbConnection con = new DbConnection();
        try{
            ResultSet rs = con.getQueryTable(groupOrderId);
            int count=0;
            if(rs.next()) // if true then we place the order
            {
                for(Product product : productList) {
                    String placeOrder = "INSERT into orders(group_order_id,product_id,customer_id) values(" + rs.getInt("id") + "," + product.getId() + "," + customer.getId() + ")";
                    count += con.updateDatabase(placeOrder); // count number of products placed
                }
                return count;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
