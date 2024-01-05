package samplePac;

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
import javafx.scene.layout.VBox;

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
                "All", "Book", "Makeup", "Car", "Laptop", "TV", "Cellphone", "Motorcycle", "Education", "Clothing"
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

        // Initialize all products
        allProducts = FXCollections.observableArrayList(
                "Product 1", "Product 2", "Product 3", "Digital Product A", "Digital Product B", "Digital Product C",
                "Vacation Package X", "Vacation Package Y", "Vacation Package Z", "Education Course 1", "Education Course 2", "Education Book X",
                "Clothing T-Shirt", "Clothing Jeans", "Clothing Jacket", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C", "Books Book A", "Books Book B", "Books Book C"
        );


        ObservableList<String> productInfo = FXCollections.observableArrayList(
                "Name: ", "Brand: ", "Price: "
        );
        // Set categories to categoryListView
        productINFO.setItems(productInfo);

        // Set up event handling for product selection
        productListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    File file = new File("src/images/bike1.jpg");
                    if (file.exists()) {
                        Image productImage = new Image(file.toURI().toString());
                        productPic.setVisible(true);
                        productPic.setImage(productImage);
                    } else {
                        System.out.println("Image file not found.");
                    }

                    productPic.setVisible(true);
                    infoBox.setVisible(true);
                    productListView.setVisible(false);
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

