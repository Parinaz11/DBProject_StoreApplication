package samplePac;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    private final static String HOST = "localhost";
    private final static int PORT = 27017;
    public static String userNamE;
    @FXML TextField username;
    @FXML TextField pw, rpw;
    @FXML Button exitButton, loginButton, signUpButton, mainSignUpButton, backButton;
    @FXML Label fillUsername, fillPassword, loginLabel, createAccountLabel, notMember;
    @FXML ImageView blurryBack, blueBack;
    @FXML Line pinkLine, greenLine;

    public static MongoClient mongoclient = new MongoClient(HOST, PORT);
    public static MongoDatabase db = mongoclient.getDatabase("Store_Application");
    public static MongoCollection<Document> loginInfo = db.getCollection("LoginInfo");
    public static MongoCollection<Document> productCollection = db.getCollection("Products");

    public void signUpScene(){
        loginButton.setVisible(false);
        signUpButton.setVisible(false);
        mainSignUpButton.setVisible(true);
        fillUsername.setVisible(false);
        fillPassword.setVisible(false);
        loginLabel.setVisible(false);
        createAccountLabel.setVisible(true);
        rpw.setVisible(true);
        notMember.setVisible(false);
        blurryBack.setVisible(false);
        blueBack.setVisible(true);
        backButton.setVisible(true);
        greenLine.setVisible(false);
        pinkLine.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        setDataInDataBase(); // use when there is no data in the data base!!!

        exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-background-color: #660000; -fx-background-radius: 15;"));
        exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-background-color: #8B0000; -fx-background-radius: 15;"));


        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #7ec016; -fx-background-radius: 90;"));//40aa00
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color:  #76ff03; -fx-background-radius: 90;"));

        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle("-fx-background-color: #0077B6; -fx-background-radius: 90;"));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle("-fx-background-color: #26A7De; -fx-background-radius: 90;"));

        mainSignUpButton.setOnMouseEntered(e -> mainSignUpButton.setStyle("-fx-background-color: #b91297; -fx-background-radius: 90;"));// #187bcd
        mainSignUpButton.setOnMouseExited(e -> mainSignUpButton.setStyle("-fx-background-color:  #ff00d0; -fx-background-radius: 90;"));

        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #726859; -fx-background-radius: 15;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color:   #928E85; -fx-background-radius: 15;"));

    }

    @FXML
    public void onExitButtonClicked(ActionEvent actionEvent){
        System.out.println("Exiting.");
        System.exit(0);
    }

    @FXML
    public void onLoginClicked(ActionEvent actionEvent) throws IOException{
        String password = pw.getText();
        String name = username.getText();
        userNamE = name;
        boolean athrize = false;
        boolean filled = true;
        fillPassword.setVisible(false);
        fillUsername.setVisible(false);

        if (password.isEmpty()){
            System.out.println("Fill in the password field");
            fillPassword.setText("Fill in the Password");
            fillPassword.setVisible(true);
            filled = false;
        }
        if (name.isEmpty()){
            System.out.println("Fill in the name field");
            fillUsername.setText("Fill in the Username");
            fillUsername.setVisible(true);
            filled = false;
        }
        if (filled){

            MongoCursor<Document> cursor2 = loginInfo.find().iterator();
            try {
                while (cursor2.hasNext()) {
                    Document document = cursor2.next();
                    if (document.getString("Username").equals(name)
                            && document.getString("Password").equals(password)) athrize = true;
                }
            }
            finally {
                cursor2.close();
            }
            if (athrize){
                System.out.println("User with name " + name + " and password " + password + " authorized.");
                System.out.println("Going to the shop...");
                // Enter the products page
                EnterProductsPage();
            }
            else{
                // Show the label that the username already exists
                fillUsername.setText("No such account exists.");
                fillUsername.setVisible(true);
            }
        }
    }

    public void EnterProductsPage() throws IOException{
        Parent backParent = FXMLLoader.load(getClass().getResource("products.fxml"));
        Scene backScene = new Scene(backParent);
        Stage window = (Stage)loginButton.getScene().getWindow();
        window.setScene(backScene);
        window.show();
    }

    @FXML
    public void onSignUpClicked(ActionEvent actionEvent){
        System.out.println("Going to the sign up scene");
        signUpScene(); // change the scene then go the main product page
    }

    public void onMainSignUpClicked(ActionEvent actionEvent) throws IOException {
        // Add the username and password to our DB

        // checking to see if the username is already taken by another user
        boolean taken = false;
        MongoCursor<Document> cursor2 = loginInfo.find().iterator();
        try {
            while (cursor2.hasNext()) {
                Document document = cursor2.next();
                if (document.getString("Username").equals(username.getText())) taken = true;
            }
        }
        finally {
            cursor2.close();
        }

        if (username.getText().isEmpty() && pw.getText().isEmpty()){
            fillPassword.setVisible(true);
            fillUsername.setText("Fill in the Username");
            fillUsername.setVisible(true);
        }
        else if (!pw.getText().equals(rpw.getText()) || pw.getText().isEmpty()){
            fillPassword.setVisible(true);
        }
        else if (username.getText().isEmpty()){
            fillUsername.setText("Fill in the Username");
            fillUsername.setVisible(true);
        }
        else if (taken){
            fillUsername.setText("Account already exists, choose another name");
            fillUsername.setVisible(true);
            fillPassword.setVisible(false);
        }
        else{
            fillPassword.setVisible(false);
            Document newUser = new Document("Username",username.getText()).append("Password", pw.getText());
            loginInfo.insertOne(newUser);
            System.out.println("--- Insertion completed ---");
            // Now go the products page
            EnterProductsPage();
        }


    }

    public void onBackButtonClicked(ActionEvent actionEvent){
        loginButton.setVisible(true);
        signUpButton.setVisible(true);
        mainSignUpButton.setVisible(false);
        fillUsername.setVisible(false);
        fillPassword.setVisible(false);
        loginLabel.setVisible(true);
        createAccountLabel.setVisible(false);
        rpw.setVisible(false);
        notMember.setVisible(true);
        blurryBack.setVisible(true);
        blueBack.setVisible(false);
        backButton.setVisible(false);
        pinkLine.setVisible(false);
        greenLine.setVisible(true);
    }

    public void setDataInDataBase(){
        // Inserting a single record by creating collection and document. (Commented, since we already did so.)

//        MongoCollection<Document> collection = db.getCollection("Products");

//        MongoCollection<Document> collection = db.getCollection("LoginInfo"); // for fake user info
//
//
        try {
            // Read all lines from the file into a List
//            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\parin\\Desktop\\Data\\Mobile\\mobile.txt")); // Inserting Cellphones
//            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\parin\\Desktop\\Data\\Car\\car.txt")); // Inserting Cars
//            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\parin\\Desktop\\Data\\Laptop\\laptop.txt")); // Inserting Laptops
//            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\parin\\Desktop\\Data\\TV\\tv.txt")); // Inserting TV
//            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\parin\\Desktop\\Data\\Book\\book.txt")); // Inserting Books
            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\parin\\Desktop\\Data\\Bike\\bike.txt")); // Inserting Bikes
//            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\parin\\Desktop\\Data\\Makeup\\makeup.txt")); // Inserting Makeup
//            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\parin\\Desktop\\Data\\Clothing\\clothing.txt")); // Inserting Makeup

//            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\parin\\Desktop\\Data\\UserInfo.txt")); // Inserting username and passwords
            // Process each line
            int countImage = 1;
            for (String line : lines) {
                // Assuming each line has the format "name,price,brand,category"
                String[] parts = line.split(",");

                // condition for product data
                if (parts.length == 4) {
                    String name = parts[0];
                    String price = parts[1];
                    String brand = parts[2];
                    String cat = parts[3];
                    String imageID = parts[3].toLowerCase() + countImage + ".jpg";
                    countImage++;
                    Document p = new Document("Name", name)
                            .append("Price", price)
                            .append("Brand", brand).append("Category", cat).append("ImageID", imageID);
//                        org.bson.Document doc  = (org.bson.Document) new org.bson.Document("Name:", name);

                    productCollection.insertOne(p);
                    System.out.println("--- Insertion completed ---");
                    System.out.println("Name: " + name + ", Price: " + price + ", Brand: " + brand);

                }

                // condition for user data
//                if (parts.length == 2) {
//                    String name = parts[0];
//                    String password = parts[1];
//                    Document book = new Document("Username", name)
//                            .append("Password", password);
////                        org.bson.Document doc  = (org.bson.Document) new org.bson.Document("Name:", name);
//
//                    loginInfo.insertOne(book);
//                    System.out.println("--- Insertion completed ---");
//
//                    // Do something with the data (e.g., print it)
//                    System.out.println("Username: " + name + ", Password: " + password);
//
//                }

                else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            // Handle file reading errors
            e.printStackTrace();
        }

    }

//    public void getFieldValues(ActionEvent event){
//
//
//        try{
//            // creating a mongodb client
//            MongoClient mongoclient = new MongoClient("localhost", 27017);
//            System.out.println("Created mongo connection successfully");
//
//            MongoDatabase db = mongoclient.getDatabase("OnlineShop");
//            System.out.println("Get database was successful");
//
//            System.out.println("Below are a list of databases present in MongoDB");
//            // To get all the database names
//            MongoCursor<String> dbsCursor = mongoclient.listDatabaseNames().iterator();
//            while (dbsCursor.hasNext()) {
//                System.out.println(dbsCursor.next());
//            }
//
//
//
//            // the second document in collection1
//            // Retrieve the second document from the collection
//            org.bson.Document secondDocument = collection.find().skip(1).limit(1).first();
//            if (secondDocument != null) {
//                System.out.println("Second Document: " + secondDocument.toJson());
//            } else {
//                System.out.println("No second document found in the collection.");
//            }
//
//            mongoclient.close();
//        }
//        catch (Exception e){
//            System.out.println(e.getClass().getName() + ": " + e.getMessage());
//        }

}