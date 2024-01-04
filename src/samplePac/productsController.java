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
import javafx.scene.layout.VBox;

public class productsController implements Initializable {

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private ListView<String> productListView;

    @FXML
    private TextField searchTextField;

    private ObservableList<String> allProducts;

    @FXML
    private VBox vboxID;

    // Initialize the controller
    @Override
    public void initialize(URL loation, ResourceBundle resources) {
        // Create categories
        ObservableList<String> categories = FXCollections.observableArrayList(
                "All", "Book", "Makeup", "Car", "Laptop", "TV", "Cellphone", "Motorcycle", "Education", "Clothing"
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

        // Initialize all products
        allProducts = FXCollections.observableArrayList(
                "Product 1", "Product 2", "Product 3", "Digital Product A", "Digital Product B", "Digital Product C",
                "Vacation Package X", "Vacation Package Y", "Vacation Package Z", "Education Course 1", "Education Course 2", "Education Book X",
                "Clothing T-Shirt", "Clothing Jeans", "Clothing Jacket", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C"
        );

        // Set up event handling for product selection
//        categoryListView.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> {
//                    try {
//                        // Load the FXML file for the new page
//                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewPage.fxml"));
//
//                        // Create the scene
//                        Scene scene = new Scene(fxmlLoader.load());
//
//                        // Create the stage
//                        Stage stage = new Stage();
//                        stage.setScene(scene);
//
//                        // Show the new stage
//                        stage.show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//        );

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

