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

    public VBox getProductsData()
    {
        ObservableList<Product> data = Product.getAllProducts();
        return createTable(data);
    }

    // to get selected product from table
    public Product getSelectedProduct()
    {
        return productTable.getSelectionModel().getSelectedItem();
    }

    public VBox getProductsInCart(ObservableList<Product> data)
    {
        return createTable(data);
    }
}