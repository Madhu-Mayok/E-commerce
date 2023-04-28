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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class UserInterface {
    GridPane loginPage;
    GridPane signUpPage;
    HBox headerBar;
    HBox footerBar;
    Button signInButton;
    Button signUpButton;
    Button ordersButton;
    Button logoutButton;
    Button cartButton;
    Label welcomeLabel;
    TextField searchBar;
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
        createSignUpPage();
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
                    //messageDisplay.setText("Welcome" + loggedInCustomer.getName());--no use

                    userName.clear(); // clear the text fields if login is successful
                    password.clear();
                    // welcome msg for the user
                    welcomeLabel.setText("Welcome-"+loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel); // showing the msg  on header bar


                    // directing to product page
                    body.getChildren().clear();
                    body.getChildren().add(productPage);

                    headerBar.getChildren().remove(signUpButton);
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

    private void createSignUpPage()
    {
        Text nameText = new Text("Your Name");
        Text mobileText = new Text("Mobile No.");
        Text gmailText = new Text("Email");
        Text passwordText = new Text("Password");

        TextField name = new TextField();
        TextField mobile = new TextField();
        TextField gmail = new TextField();
        PasswordField password = new PasswordField();

        name.setPromptText("Enter Your Name");
        mobile.setPromptText("Enter Your Number");
        gmail.setPromptText("Enter Your Mail");
        password.setPromptText("Set Your Password");

        Label messageLabel = new Label();
        messageLabel.setText("Fill the details");

        signUpPage = new GridPane();
        signUpPage.setAlignment(Pos.CENTER); // place the components at the center of signuppage
        signUpPage.setHgap(10); // add horizontal gap btw components
        signUpPage.setVgap(10);// add vertical gap btw components


        signUpPage.setStyle("-fx-border-color: black;-fx-border-width: 2pt;-fx-padding: 10pt;-fx-max-width: 300pt");
        signUpPage.setAlignment(Pos.CENTER);
        signUpPage.setHgap(10);
        signUpPage.setVgap(10);
        signUpPage.add(nameText,0,0);
        signUpPage.add(name,1,0);
        signUpPage.add(gmailText,0,1);
        signUpPage.add(gmail,1,1);
        signUpPage.add(mobileText,0,2);
        signUpPage.add(mobile,1,2);
        signUpPage.add(passwordText,0,3);
        signUpPage.add(password,1,3);
        signUpPage.add(messageLabel,0,4);

        // on click of signup button...customer should sign up
        Button sign_upButton = new Button("Sign up");
        signUpPage.add(sign_upButton,1,4);

        sign_upButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
            if (gmail.getText().isEmpty() || mobile.getText().isEmpty() || name.getText().isEmpty() || password.getText().isEmpty()){
               // messageLabel.setText("All fields are required");
                showDialog("All fields are required");
                return;
            }
            String givenGmail = gmail.getText();
            String givenName = name.getText();
            String givenMobile = mobile.getText();
            String givenPassword = password.getText();

            //create new customer and add to database
            Customer customer = new Customer(givenName,givenGmail,givenMobile,givenPassword);
            //adding to db
            boolean res =  signUp.customerLogin(customer);
            if (res){
                showDialog("Registered Successfully");
                gmail.clear();
                name.clear();
                mobile.clear();
                password.clear();
            }else {
                showDialog("Registration failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            }
        });

    }

    private void createHeaderBar(){
        //creating home button with image and show it in the header bar
        Button homeButtom = new Button();
        Image image = new Image("C:\\Accio Project\\E-COMMERCE\\src\\home icon.jpg");
        ImageView viewImage = new ImageView();
        viewImage.setImage(image);
        viewImage.setFitHeight(20);
        viewImage.setFitWidth(20);
        homeButtom.setGraphic(viewImage);

        // creating text field for search bar
        searchBar = new TextField();
        searchBar.setPromptText("Search Here"); // adding Placeholder text in the searchbar field
        searchBar.setPrefWidth(200); // we can increase the width of text field by using this method
        // If we want, we can also use the same method to increase buttonsize.

        Button searchButton = new Button(); // creating search button for search bar
        Image searchImage = new Image("C:\\Accio Project\\E-COMMERCE\\src\\search icon.png");
        ImageView viewSearchImage = new ImageView();
        viewSearchImage.setImage(searchImage);
        viewSearchImage.setFitHeight(20);
        viewSearchImage.setFitWidth(20);
        searchButton.setGraphic(viewSearchImage);


        signInButton = new Button();
        Image signInImage = new Image("C:\\Accio Project\\E-COMMERCE\\src\\Sign-in-icon.jpg");
        ImageView viewSignInImage = new ImageView();
        viewSignInImage.setImage(signInImage);
        viewSignInImage.setFitHeight(20);
        viewSignInImage.setFitWidth(20);
        signInButton.setGraphic(viewSignInImage);

        signUpButton = new Button("Sign up");
        welcomeLabel = new Label();

        cartButton = new Button(); // creating cart button
        Image cartImage = new Image("C:\\Accio Project\\E-COMMERCE\\src\\cart icon.png");
        ImageView viewCartImage = new ImageView();
        viewCartImage.setImage(cartImage);
        viewCartImage.setFitHeight(20);
        viewCartImage.setFitWidth(20);
        cartButton.setGraphic(viewCartImage);

        //orders button to show our orders
        ordersButton = new Button("Orders");


        headerBar = new HBox(15); // gives 15px horizontal gap between components
        headerBar.setPadding(new Insets(15)); // padding gives space around elements inside any defined border
        headerBar.setAlignment(Pos.CENTER);
       // headerBar.setStyle("-fx-background-color:gray;"); // adds background color to our hbox layout
        headerBar.getChildren().addAll(homeButtom,searchBar,searchButton,signInButton,cartButton,signUpButton,ordersButton);


        ordersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedInCustomer == null)
                {
                    showDialog("Login first !!");
                    return;
                }
                VBox orderPage = productlist.getOrdersData(loggedInCustomer);
                orderPage.setSpacing(10);
                orderPage.setAlignment(Pos.CENTER);

                body.getChildren().clear();
                body.getChildren().add(orderPage);

                footerBar.setVisible(false); // on cart page we dont need it... but in other pages it should be visiable .. so handle other cases
            }
        });

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
                VBox cartPage = productlist.getProductsInCart(itemsInCart);
                cartPage.setSpacing(10);
                cartPage.setAlignment(Pos.CENTER);
                cartPage.getChildren().add(placeOrderButton);
                body.getChildren().clear(); // clear the page
               // if(itemsInCart != null)
                body.getChildren().add(cartPage); // add cart page

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
               // body.getChildren().clear();
               // itemsInCart=null;
                if(count != 0) {
                    showDialog("Order for " + count + " products is placed Successfully !!");
                    itemsInCart.clear();
                }
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

                //if no user logged in and signin button is not present on header then only add signin button to header bar
                if(loggedInCustomer == null && headerBar.getChildren().indexOf(signInButton) == -1) {
                    headerBar.getChildren().remove(cartButton);
                    headerBar.getChildren().remove(signUpButton);
                    headerBar.getChildren().remove(ordersButton);

                    headerBar.getChildren().addAll(signInButton,cartButton,signUpButton,ordersButton);
                }
                else if(loggedInCustomer==null && headerBar.getChildren().indexOf(signInButton) != -1)
                {
                    headerBar.getChildren().remove(cartButton);
                    headerBar.getChildren().remove(signUpButton);
                    headerBar.getChildren().remove(ordersButton);
                    headerBar.getChildren().addAll(cartButton,signUpButton,ordersButton);
                }
            }
        });

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                headerBar.getChildren().remove(cartButton);
                headerBar.getChildren().remove(signUpButton);
                headerBar.getChildren().remove(ordersButton);
                footerBar.setVisible(false);
                body.getChildren().add(signUpPage);
            }
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String search = searchBar.getText();

                VBox searchPage = productlist.getSearchData(search);
                searchPage.setSpacing(10);
                searchPage.setAlignment(Pos.CENTER);
                body.getChildren().clear(); // clear the page

                body.getChildren().add(searchPage); // add search results page
            }
        });

    }

    private void createFooterBar(){

        Button buyNowButton = new Button("Buy Now");

        Button addToCartButton = new Button("Add to Cart");
        Image addToCartImage = new Image("C:\\Accio Project\\E-COMMERCE\\src\\addCart.png");
        ImageView viewaddToCartImage = new ImageView();
        viewaddToCartImage.setImage(addToCartImage);
        viewaddToCartImage.setFitHeight(20);
        viewaddToCartImage.setFitWidth(20);
        addToCartButton.setGraphic(viewaddToCartImage);

        footerBar = new HBox();
        footerBar = new HBox(15); // gives 15px horizontal gap between components
        footerBar.setPadding(new Insets(15)); // padding gives space around elements inside any defined border
        footerBar.setAlignment(Pos.CENTER);
        // headerBar.setStyle("-fx-background-color:gray;"); // adds background color to our hbox layout
        logoutButton = new Button("LogOut");
        footerBar.getChildren().addAll(buyNowButton,addToCartButton,logoutButton);

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

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedInCustomer == null)
                {
                    showDialog("Please..LogIn first !!");
                }
                else {
                    loggedInCustomer = null;
                    body.getChildren().clear();
                    headerBar.getChildren().remove(welcomeLabel);
                    headerBar.getChildren().remove(cartButton);
                    headerBar.getChildren().remove(ordersButton);
                    headerBar.getChildren().addAll(signInButton,cartButton,signUpButton,ordersButton);
                    body.getChildren().add(productPage);
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
