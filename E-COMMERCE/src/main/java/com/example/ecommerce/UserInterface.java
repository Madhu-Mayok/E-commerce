package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;
    Button signInButton;

    Label welcomeLabel;
    Customer loggedInCustomer;

    ProductList productlist = new ProductList();
    VBox productPage;
    VBox body;
    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
    Button placeOrderButton = new Button("Place Order"); // for cart page
    BorderPane createContent()
    {
        BorderPane root = new BorderPane();
        root.setPrefSize(600,400);
      //root.setCenter(loginPage); // add the login page to the center of border layout container

        productPage = productlist.getProductsData();

        body = new VBox();
        body.setPadding(new Insets(10));
        body.getChildren().add(productPage);
        body.setAlignment(Pos.CENTER); // aligns the children to center in body

        root.setTop(headerBar); // place the header bar on the top of border layout container
        root.setCenter(body); // sets body to center in borderpane
        root.setBottom(footerBar); // sets foooter bar at the bottom of boderpane

        return root;
    }

    public UserInterface() // when userInterface instance is created in application class , create the login page
    {
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }

    private void createLoginPage()
    {
        // creating text and field controls for username and password

        Text userNameText = new Text("Username");
        TextField userName = new TextField(); // creating field for username
        userName.setPromptText("Enter Your Username");
        //userName.setText("ravi@gmail.com");

        Text passwordText = new Text("Password");
        PasswordField password = new PasswordField();
        password.setPromptText("Enter Your Password");
        //password.setText("12456");

        // initialising gridpane
        loginPage = new GridPane();
        //loginPage.setStyle("-fx-background-color:lime;"); // adds background color to our loginpage grid layout

        loginPage.setAlignment(Pos.CENTER); // place the components at the center of loginpage
        loginPage.setHgap(10); // add horizontal gap btw components
        loginPage.setVgap(10);// add vertical gap btw components

        // adding the controls to the login page (layout gird)
        //Username control
        loginPage.add(userNameText,0,0);
        loginPage.add(userName,1,0);

        //Password control
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);

        //message label to welcome user and display some info if wrong credentials are provided
        Label messageDisplay = new Label();
        messageDisplay.setText("Enter Your Credentials");
        loginPage.add(messageDisplay,0,2);

        //creating and adding login button control
        Button loginButton = new Button("Login");
        loginPage.add(loginButton,1,2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();
                Login login = new Login();
                loggedInCustomer = login.customerLogin(name,pass);

                if(loggedInCustomer != null) { // successful login
                    messageDisplay.setText("Welcome" + loggedInCustomer.getName());
                    // welcome msg for the user
                    welcomeLabel.setText("Welcome-"+loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel); // showing the msg  on header bar

                    // directing to product page
                    body.getChildren().clear();
                    body.getChildren().add(productPage);

                    footerBar.setVisible(true);
                }
                else {
                    messageDisplay.setText("Login failed !! Please give correct username and password");
                   // welcomeLabel.setText("Wrong Credentials");
                   // headerBar.getChildren().add(welcomeLabel); // showing the msg  on header bar
                }
            }
        });
    }
    private void createHeaderBar(){
        //creating home button with image and show it in the header bar
        Button homeButtom = new Button();
        Image image = new Image("C:\\Accio Project\\E-COMMERCE\\src\\home image.jpg");
        ImageView viewImage = new ImageView();
        viewImage.setImage(image);
        viewImage.setFitHeight(20);
        viewImage.setFitWidth(40);
        homeButtom.setGraphic(viewImage);

        // creating text field for search bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Here"); // adding Placeholder text in the searchbar field
        searchBar.setPrefWidth(200); // we can increase the width of text field by using this method
        // If we want, we can also use the same method to increase buttonsize.

        Button searchButton = new Button("Search"); // creating search button for search bar
        signInButton = new Button("Sign in");
        welcomeLabel = new Label();

        Button cartButton = new Button("Cart"); // creating cart button

        //orders button to show our orders
        //Button orderButton = new Button("Orders");

        headerBar = new HBox(15); // gives 15px horizontal gap between components
        headerBar.setPadding(new Insets(15)); // padding gives space around elements inside any defined border
        headerBar.setAlignment(Pos.CENTER);
       // headerBar.setStyle("-fx-background-color:gray;"); // adds background color to our hbox layout
        headerBar.getChildren().addAll(homeButtom,searchBar,searchButton,signInButton,cartButton);


        //on click of sign in button.. direct to login page

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear(); // clear everything in body
                body.getChildren().add(loginPage); // add login page

                headerBar.getChildren().remove(signInButton); // removing signin button after directing to login page
                footerBar.setVisible(false);
            }
        });

        // on click of cart button it should items added to cart in new page

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear(); // clear the page
                VBox cartPage = productlist.getProductsInCart(itemsInCart);
                cartPage.setSpacing(10);
                cartPage.setAlignment(Pos.CENTER);
                cartPage.getChildren().add(placeOrderButton);
                body.getChildren().add(cartPage);
                footerBar.setVisible(false); // on cart page we dont need it... but in other pages it should be visiable .. so handle other cases
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //need list of products and a customer
                if(loggedInCustomer == null) // if user try to place order without logging in ...send an alert to login first
                {
                    showDialog("Please ! login first to place order ..");
                    return;
                }
                if(itemsInCart == null) // if user try to place order without  any product in cart ...send an alert to add product to cart
                {
                    showDialog("Please !Add some products in the cart to place order..");
                    return;
                }
                // if the user logged in and selected a product then try to place the order
                int count = Order.placeMultipleOrder(loggedInCustomer,itemsInCart); // if order placed in order table then
                if(count != 0)
                    showDialog("Order for "+count+" products is placed Successfully !!");
                else
                    showDialog("Order failed !!");
            }
        });

        homeButtom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);

                //if no used logged in and signin button is not present on header then only add signin button to header bar
                if(loggedInCustomer == null && headerBar.getChildren().indexOf(signInButton) == -1)
                    headerBar.getChildren().add(signInButton);
            }
        });

    }

    private void createFooterBar(){

        Button buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add to Cart");

        footerBar = new HBox();
        footerBar = new HBox(15); // gives 15px horizontal gap between components
        footerBar.setPadding(new Insets(15)); // padding gives space around elements inside any defined border
        footerBar.setAlignment(Pos.CENTER);
        // headerBar.setStyle("-fx-background-color:gray;"); // adds background color to our hbox layout
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);

        //on click of buynow button place an order
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productlist.getSelectedProduct();
                if(loggedInCustomer == null) // if user try to place order without logging in ...send an alert to login first
                {
                    showDialog("Please ! login first to place order ..");
                    return;
                }
                if(product == null) // if user try to place order without selecting any product ...send an alert to select a product to place order
                {
                    showDialog("Please ! Select a product to place order ...");
                    return;
                }
                // if the user logged in and selected a product then try to place the order
                boolean status = Order.placeOrder(loggedInCustomer,product); // if order placed in order table then status is true
                if(status)
                    showDialog("Order Placed Successfully !!");
                else
                    showDialog("Order failed !!");
            }
        });

        //On click of addtocart button it should add an item to cart

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productlist.getSelectedProduct();
                if(product == null)
                {
                    showDialog("Please ! select an item to add to cart..");
                }
                else {
                    itemsInCart.add(product);
                    showDialog("Selected item added to the cart successfully !!");
                }
            }
        });

    }

    private void showDialog(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }


}
