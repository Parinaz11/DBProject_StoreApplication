package samplePac;

import com.mongodb.Cursor;
import com.mongodb.client.MongoCursor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

import static samplePac.Controller.*;

public class productsController implements Initializable {

    @FXML
    private ListView<String> categoryListView, productINFO;

    @FXML
    private ListView<String> productListView;

    @FXML
    private TextField searchTextField;

    private ObservableList<String> allProducts;

    @FXML
    private ImageView productPic;
    @FXML
    private VBox infoBox;
    @FXML
    private Button signOutButton, productsButton, addToCartButton;
    @FXML
    private Label notAvailableLabel;


    // Initialize the controller
    @Override
    public void initialize(URL loation, ResourceBundle resources) {

        addToCartButton.setOnMouseEntered(e -> addToCartButton.setStyle("-fx-background-color: #11555a; -fx-background-radius: 15;"));
        addToCartButton.setOnMouseExited(e -> addToCartButton.setStyle("-fx-background-color:  #849e93; -fx-background-radius: 15;"));

        signOutButton.setOnMouseEntered(e -> signOutButton.setStyle("-fx-background-color: #660000; -fx-background-radius: 15;"));
        signOutButton.setOnMouseExited(e -> signOutButton.setStyle("-fx-background-color: #8B0000; -fx-background-radius: 15;"));

        productsButton.setOnMouseEntered(e -> productsButton.setStyle("-fx-background-color: #5e4930; -fx-background-radius: 15;"));
        productsButton.setOnMouseExited(e -> productsButton.setStyle("-fx-background-color:  #8f6f46; -fx-background-radius: 15;"));

        // Create categories
        ObservableList<String> categories = FXCollections.observableArrayList(
                "All", "Book", "Makeup", "Car", "Laptop", "TV", "Cellphone", "Motorcycle", "Clothing"
        );
        // Set categories to categoryListView
        categoryListView.setItems(categories);
        productListView.setVisible(true);
        infoBox.setVisible(false);
        productPic.setVisible(false);
        productsButton.setVisible(false);
        addToCartButton.setVisible(false);
        notAvailableLabel.setVisible(false);

        // Set up event handling for category selection
        categoryListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Update product list based on the selected category
                    productPic.setVisible(false);
                    infoBox.setVisible(false);
                    productsButton.setVisible(false);
                    productListView.setVisible(true);
                    addToCartButton.setVisible(false);
                    notAvailableLabel.setVisible(false);
                    updateProductList(newValue);
                }
        );



        // Initialize all products from "Name" field in DB

        MongoCursor<Document> cursor = productCollection.find().iterator();
        allProducts = FXCollections.observableArrayList();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                // Get the "name" field from the document and add it to the ObservableList
                String productName;
                if (document.getString("Category").equals("book")){
                    productName = document.getString("Name") + "\t\t" + document.getString("Author");
                }
                else{
                    productName = document.getString("Name") + "\t\t" + document.getString("Brand");
                }
                allProducts.add(productName);
            }
        } finally {
            cursor.close();
        }

        // Set up event handling for product selection
        productListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

//                    ObservableList<String> filteredProducts = allProducts.filtered(product -> product.equals(newValue));
//
                    MongoCursor<Document> cursor2 = productCollection.find().iterator();
                    try {
                        while (cursor2.hasNext()) {
                            Document document = cursor2.next();
                            // Get the "name" field from the document and add it to the ObservableList
                            String productName;
                            if (document.getString("Category").equals("book")){
                                productName = document.getString("Name") + "\t\t" + document.getString("Author");
                            }
                            else{
                                productName = document.getString("Name") + "\t\t" + document.getString("Brand");
                            }

                            if (productName.equals(newValue)){
                                // show the image of that product

                                String address = "src/" + document.getString("Category") + "/photos/" + document.getString("ImageID");
                                File file = new File(address);// "src/book/photos/book1.jpg"
                                if (file.exists()) {
                                    Image productImage = new Image(file.toURI().toString());
                                    productPic.setVisible(true);
                                    productPic.setImage(productImage);
                                } else {
                                    System.out.println("Image file not found.");
                                }

                                // Set product info

                                if (document.getString("Category").equals("book")){
                                    ObservableList<String> productInfo = FXCollections.observableArrayList(
                                            "Name: " + document.getString("Name"), "Author: " + document.getString("Author"),
                                            "Price: " + document.getString("Price") + "$", "Item Count: " + document.getInteger("itemsLeft"),
                                            "Product ID: " + document.getObjectId("_id"));
                                    // Set categories to categoryListView
                                    productINFO.setItems(productInfo);
                                }
                                else{
                                    ObservableList<String> productInfo = FXCollections.observableArrayList(
                                            "Name: " + document.getString("Name"), "Brand: " + document.getString("Brand"),
                                            "Price: " + document.getString("Price") + "$", "Item Count: " + document.getInteger("itemsLeft"),
                                            "Product ID: " + document.getObjectId("_id"));
                                    // Set categories to categoryListView
                                    productINFO.setItems(productInfo);
                                }
                                productINFO.setVisible(true);
                                productPic.setVisible(true);
                                infoBox.setVisible(true);
                                productListView.setVisible(false);
                                productsButton.setVisible(true);
                                addToCartButton.setVisible(true);
                                notAvailableLabel.setVisible(false);
                            }
                        }
                    } finally {
                        cursor2.close();
                    }

                }
        );

        // Set up event handling for search
        searchTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Filter products based on the search term
                    filterProducts(newValue);
                    productPic.setVisible(false);
                    infoBox.setVisible(false);
                    productListView.setVisible(true);
                    productsButton.setVisible(false);
                    addToCartButton.setVisible(false);
                    notAvailableLabel.setVisible(false);
                }
        );
    }

    @FXML
    public void onSignOutButtonClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        // Go to the login page is the client wanted to sign out
        Parent backParent = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene backScene = new Scene(backParent);
        Stage window = (Stage)signOutButton.getScene().getWindow();
        window.setScene(backScene);
        window.show();
    }

    @FXML
    public void onProductsButtonClicked(javafx.event.ActionEvent actionEvent){
        productListView.setVisible(true);
        infoBox.setVisible(false);
        productPic.setVisible(false);
        productsButton.setVisible(false);
        addToCartButton.setVisible(false);
        notAvailableLabel.setVisible(false);
    }


    // Update the product list based on the selected category
    private void updateProductList(String selectedCategory) {

        if ("All".equals(selectedCategory)) {
            productListView.setItems(allProducts);
            productListView.setVisible(true);
            productPic.setVisible(false);
            productINFO.setVisible(false);
            infoBox.setVisible(false);
            addToCartButton.setVisible(false);
            notAvailableLabel.setVisible(false);
        } else {
            // Filter products based on the selected category

            MongoCursor<Document> cursor = productCollection.find().iterator();
            ObservableList<String> filteredProducts = FXCollections.observableArrayList();
            try {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    // Get the "name" field from the document and add it to the ObservableList
                    String productCat = document.getString("Category");
                    if (productCat.toLowerCase().equals(selectedCategory.toLowerCase())){

                        if (document.getString("Category").equals("book")){
                            filteredProducts.add(document.getString("Name") + "\t\t" + document.getString("Author"));
                        }
                        else{
                            filteredProducts.add(document.getString("Name") + "\t\t" + document.getString("Brand"));
                        }

                    }
                }
            } finally {
                cursor.close();
            }
            productListView.setItems(filteredProducts);
        }
        productsButton.setVisible(false);
    }

    // Filter products based on the search term
    private void filterProducts(String searchTerm) {
        ObservableList<String> filteredProducts = allProducts.filtered(product -> product.toLowerCase().contains(searchTerm.toLowerCase()));
        productListView.setItems(filteredProducts);
    }

    public void onAddtoCartButtonClicked(javafx.event.ActionEvent actionEvent) {

        ObservableList<String> items = productINFO.getItems();
        if (!items.isEmpty()) {

            // Checking for the item existing (count not being 0)
            if (items.get(3).equals("Item Count: 0")){

                System.out.println("Count is zero.");
                notAvailableLabel.setVisible(true);
            }
            else{
                ObjectId prodID = new ObjectId(items.get(4).replace("Product ID: ", ""));

                // Enter the product ID into the db for userlogin info
                Document query = new Document("Username", userNamE);
                Document update = new Document("$push", new Document("boughtProducts", prodID));
                loginInfo.updateOne(query, update);
                System.out.println("Product added to the user's list.");

                // Update the item count
                // Finding a product with that ID
                MongoCursor<Document> cursor = productCollection.find().iterator();
                try {
                    while (cursor.hasNext()) {
                        Document document = cursor.next();
                        // Get the "name" field from the document and add it to the ObservableList
                        ObjectId productID = document.getObjectId("_id");
                        if (prodID.equals(productID)){
                            Document update2 = new Document("$set", new Document("itemsLeft", Integer.parseInt(items.get(3).replace("Item Count: ", "")) - 1));
                            productCollection.updateOne(document, update2);

                            // Showing the changes
                            if (document.getString("Category").equals("book")){
                                ObservableList<String> productInfo = FXCollections.observableArrayList(
                                        "Name: " + document.getString("Name"), "Author: " + document.getString("Author"),
                                        "Price: " + document.getString("Price") + "$", "Item Count: " + (document.getInteger("itemsLeft") - 1),
                                        "Product ID: " + document.getObjectId("_id"));
                                // Set categories to categoryListView
                                productINFO.setItems(productInfo);
                            }
                            else{
                                ObservableList<String> productInfo = FXCollections.observableArrayList(
                                        "Name: " + document.getString("Name"), "Brand: " + document.getString("Brand"),
                                        "Price: " + document.getString("Price") + "$", "Item Count: " + (document.getInteger("itemsLeft") - 1),
                                        "Product ID: " + document.getObjectId("_id"));
                                // Set categories to categoryListView
                                productINFO.setItems(productInfo);
                            }

                            break;
                        }
                    }
                } finally {
                    cursor.close();
                }
            }
        } else {
            System.out.println("The list is empty.");
        }

    }
}

