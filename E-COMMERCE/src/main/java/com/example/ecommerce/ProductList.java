package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {

    private TableView<Product> productTable; // to create table
    private TableView<Product> cartTable;

    private TableView<OrderList> orderTable;

    public VBox createTable(ObservableList<Product> data)
    {
        //creating columns for the table and naming it
        TableColumn id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id")); // binding this id to product class id

        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable = new TableView<>(); // creating table
        productTable.getColumns().addAll(id,name,price); // adding colums to the table
        productTable.setItems(data); // adding data items to table
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(productTable);
        return vbox;

    }

    public VBox createTableCart(ObservableList<Product> data) {
        //creating columns for the table and naming it
        TableColumn id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id")); // binding this id to product class id

        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        cartTable = new TableView<>(); // creating table
        cartTable.getColumns().addAll(id, name, price); // adding colums to the table
        cartTable.setItems(data); // adding data items to table
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(cartTable);
        return vbox;
    }


        public VBox getProductsData()
    {
        ObservableList<Product> data = Product.getAllProducts();
        return createTable(data);
    }

    public VBox getSearchData(String searchText)
    {
        ObservableList<Product> data = Product.getSearchData(searchText);
        return createTable(data);
    }
    // to get selected product from table
    public Product getSelectedProduct()
    {
        return productTable.getSelectionModel().getSelectedItem();
    }

    public VBox getProductsInCart(ObservableList<Product> data)
    {
        return createTableCart(data);
    }

    public VBox createOrderTable(ObservableList<OrderList> data)
    {
        //creating columns for the table and naming it

        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn quantity = new TableColumn<>("QUANTITY");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity")); // binding this quantity to orderlist class

        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn orderDate = new TableColumn("ORDER DATE");
        orderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));

        orderTable = new TableView<>(); // creating table
        orderTable.getColumns().addAll(name,quantity,price,orderDate); // adding columns to the table
        orderTable.setItems(data); // adding data items to table
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(orderTable);
        return vbox;

    }
    public VBox getOrdersData(Customer logged)
    {
        ObservableList<OrderList> data = OrderList.getAllProducts(logged);
        return createOrderTable(data);
    }
}
