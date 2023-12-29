package samplePac;

//
//import javafx.fxml.Initializable;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class productsController implements Initializable {
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources){
//        System.out.println("Hello!");
//    }
//}

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class productsController implements Initializable {

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private ListView<String> productListView;

    @FXML
    private TextField searchTextField;

    private ObservableList<String> allProducts;

    // Initialize the controller
    @Override
    public void initialize(URL loation, ResourceBundle resources) {
        // Create categories
        ObservableList<String> categories = FXCollections.observableArrayList(
                "All", "Digital", "Vacation", "Education", "Clothing", "Books"
        );

        // Set categories to categoryListView
        categoryListView.setItems(categories);

        // Set up event handling for category selection
        categoryListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Update product list based on the selected category
                    updateProductList(newValue);
                }
        );

        // Initialize all products (replace with your actual product list)
        allProducts = FXCollections.observableArrayList(
                "Product 1", "Product 2", "Product 3", "Digital Product A", "Digital Product B", "Digital Product C",
                "Vacation Package X", "Vacation Package Y", "Vacation Package Z", "Course 1", "Course 2", "Book X",
                "T-Shirt", "Jeans", "Jacket", "Book A", "Book B", "Book C"
        );

        // Set up event handling for search
        searchTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Filter products based on the search term
                    filterProducts(newValue);
                }
        );
    }

    // Update the product list based on the selected category
    private void updateProductList(String selectedCategory) {
        if ("All".equals(selectedCategory)) {
            productListView.setItems(allProducts);
        } else {
            // Filter products based on the selected category
            ObservableList<String> filteredProducts = allProducts.filtered(product -> product.startsWith(selectedCategory));
            productListView.setItems(filteredProducts);
        }
    }

    // Filter products based on the search term
    private void filterProducts(String searchTerm) {
        ObservableList<String> filteredProducts = allProducts.filtered(product -> product.toLowerCase().contains(searchTerm.toLowerCase()));
        productListView.setItems(filteredProducts);
    }
}

