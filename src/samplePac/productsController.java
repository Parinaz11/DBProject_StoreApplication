package samplePac;

import com.mongodb.client.MongoCursor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import org.bson.Document;

import static samplePac.Controller.productCollection;

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


    // Initialize the controller
    @Override
    public void initialize(URL loation, ResourceBundle resources) {

        // Create categories
        ObservableList<String> categories = FXCollections.observableArrayList(
                "All", "book", "Makeup", "Car", "Laptop", "TV", "Cellphone", "Motorcycle", "Clothing"
        );
        // Set categories to categoryListView
        categoryListView.setItems(categories);
        productListView.setVisible(true);
        infoBox.setVisible(false);
        productPic.setVisible(false);


        // Set up event handling for category selection
        categoryListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Update product list based on the selected category
                    productPic.setVisible(false);
                    infoBox.setVisible(false);
                    productListView.setVisible(true);
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

//        allProducts = FXCollections.observableArrayList(
//                "Product 1", "Product 2", "Product 3", "Digital Product A", "Digital Product B", "Digital Product C",
//                "Vacation Package X", "Vacation Package Y", "Vacation Package Z", "Education Course 1", "Education Course 2", "Education Book X",
//                "Clothing T-Shirt", "Clothing Jeans", "Clothing Jacket", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C"
//        );


        // Set up event handling for product selection
        productListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {




//                    ObservableList<String> filteredProducts = allProducts.filtered(product -> product.equals(newValue));
//
                    MongoCursor<Document> cursor2 = productCollection.find().iterator();
                    ObservableList<String> filteredProducts = FXCollections.observableArrayList();
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

//                                String address  = "src/productImages/makeupppp14.jpg";
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
                                            "Price: " + document.getString("Price") + "$");
                                    // Set categories to categoryListView
                                    productINFO.setItems(productInfo);
                                }
                                else{
                                    ObservableList<String> productInfo = FXCollections.observableArrayList(
                                            "Name: " + document.getString("Name"), "Brand: " + document.getString("Brand"),
                                            "Price: " + document.getString("Price") + "$");
                                    // Set categories to categoryListView
                                    productINFO.setItems(productInfo);
                                }

                                productPic.setVisible(true);
                                infoBox.setVisible(true);
                                productListView.setVisible(false);
                            }
                        }
                    } finally {
                        cursor2.close();
                    }
//
//
//
//                    // Find the selected document based on its name
//                    Document selectedDocument = allProducts.stream()
//                            .filter(document -> document.getString("Name").equals(newValue))
//                            .findFirst()
//                            .orElse(null);
//
//                    if (selectedDocument != null) {
//                        // Use the selected document's fields as needed
//                        String imageID = selectedDocument.getString("imageID");
//                        File file = new File("src/productImages/" + imageID + ".jpg");
//
//                        if (file.exists()) {
//                            Image productImage = new Image(file.toURI().toString());
//                            productPic.setVisible(true);
//                            productPic.setImage(productImage);
//                        } else {
//                            System.out.println("Image file not found for document: " + selectedDocument.getString("Name"));
//                        }
//                    }
//
//                    // Additional handling for displaying document info
//                    productPic.setVisible(true);
//                    infoBox.setVisible(true);
//                    productListView.setVisible(false);

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
                }
        );
    }


    // Update the product list based on the selected category
    private void updateProductList(String selectedCategory) {
        if ("All".equals(selectedCategory)) {
            productListView.setItems(allProducts);
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
//            ObservableList<String> filteredProducts = allProducts.filtered(product -> product.startsWith(selectedCategory));
            productListView.setItems(filteredProducts);
        }
    }

    // Filter products based on the search term
    private void filterProducts(String searchTerm) {
        ObservableList<String> filteredProducts = allProducts.filtered(product -> product.toLowerCase().contains(searchTerm.toLowerCase()));
        productListView.setItems(filteredProducts);
    }
}

