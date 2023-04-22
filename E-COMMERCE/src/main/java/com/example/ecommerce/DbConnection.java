package com.example.ecommerce;
import java.sql.*;

public class DbConnection {
    private final String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";
    private final String userName = "root";
    private final String password = "12345";

    private Statement getStatement() {
        try {
            Connection connection = DriverManager.getConnection(dbUrl, userName, password);
            return connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // executes the query we

    public ResultSet getQueryTable(String query)
    {
        try{
            Statement statement = getStatement();
            return statement.executeQuery(query);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public int updateDatabase(String query) // when order placed we need to insert data into order table
    {
        try{
            Statement statement = getStatement();
            return statement.executeUpdate(query);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    // just to check connection succesful or not
    public static void main(String[] args) {
        DbConnection con = new DbConnection();
        ResultSet rs = con.getQueryTable("SELECT * from customer");

        if(rs != null) System.out.println("Connection successful");
        else System.out.println("Connection failed");
    }
}
