package samplePac;

import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.bson.Document;

import javax.annotation.Generated;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;


public class Controller implements Initializable{
    private final static String HOST = "localhost";
    private final static int PORT = 27017;

//    @FXML
//    private TextField firstname;
//    @FXML
//    private TextField lastname;
//    @FXML
//    private Label status;
//    @FXML
//    private TextField email;
//    @FXML
//    public ComboBox<String> gender;
//    @FXML
//    private TextField pnumber;
//    @FXML
//    private Button att;
//
////  create an observable list to hold the content of the combobox
//    ObservableList<String> list  = FXCollections.observableArrayList("Male", "Female");
//
////  create a primary stage object
//    Stage primaryStage = new Stage();


    @FXML TextField username, pw, rpw;
    @FXML Button exitButton, loginButton, signUpButton, mainSignUpButton;

    @FXML Label fillUsername, fillPassword, loginLabel, createAccountLabel, notMember;
    @FXML CheckBox rme;
    @FXML ImageView blurryBack, blueBack;

    MongoClient mongoclient = new MongoClient("localhost", 27017);
    MongoDatabase db = mongoclient.getDatabase("OnlineShop");
    MongoCollection<Document> loginInfo = db.getCollection("loginInfo");

    public void signUpScene(){
        loginButton.setVisible(false);
        signUpButton.setVisible(false);
        mainSignUpButton.setVisible(true);
        fillUsername.setVisible(false);
        fillPassword.setVisible(false);
        loginLabel.setVisible(false);
        createAccountLabel.setVisible(true);
        rme.setVisible(true);
        rpw.setVisible(true);
        notMember.setVisible(false);
        blurryBack.setVisible(false);
        blueBack.setVisible(true);


        // getting the info after setting up this scene and clicking Sign up
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//      set the items of the combobox
//        gender.setItems(list);
        exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-background-color: #660000; -fx-background-radius: 15;"));
        exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-background-color: #8B0000; -fx-background-radius: 15;"));


        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #40aa00; -fx-background-radius: 90;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color:  #76ff03; -fx-background-radius: 90;"));

        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle("-fx-background-color: #0077B6; -fx-background-radius: 90;"));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle("-fx-background-color: #26A7De; -fx-background-radius: 90;"));

    }

    @FXML
    public void onExitButtonClicked(ActionEvent actionEvent){
        System.out.println("Exiting.");
        System.exit(0);
    }

    @FXML
    public void onLoginClicked(ActionEvent actionEvent){
        String password = pw.getText();
        String name = username.getText();
        boolean athrize = true;
        boolean filled = true;

        if (password.isEmpty()){
            System.out.println("Fill in the password field");
            fillPassword.setVisible(true);
            filled = false;
        }
        if (name.isEmpty()){
            System.out.println("Fill in the name field");
            fillUsername.setVisible(true);
            filled = false;
        }
        if (filled){

            // Finding the user with the same name and password in our DB

            // -------------------------------------------------------------------------------------------------------------------------------------------
            // finding the usename and password in our DB and making sure this customer exists
            // -------------------------------------------------------------------------------------------------------------------------------------------



//                Document doc = loginInfo.find(Filters.and(Filters.eq("nameDB", username), Filters.eq("passwordDB", password))).first();
//                if (doc == null){
//                    System.out.println("You are not registered. Please sign up.");
//                    athrize = false;
//                }

//            mongoclient.close();

            if (athrize){
                System.out.println("User with name " + name + " and password " + password + " authorized.");
                System.out.println("Going to the shop...");
                // change the stage
            }
        }

        if (name.equals("parinaz")){
            signUpScene();
        }
    }

    @FXML
    public void onSignUpClicked(ActionEvent actionEvent){
        System.out.println("Going to the sign up scene");
        // change the scene
        // then to the shop
    }

    public void onMainSignUpClicked(ActionEvent actionEvent) {


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
////        // Inserting a single record by creating collection and document.
//            MongoCollection<Document> collection = db.getCollection("collection1");
//            try {
//                // Read all lines from the file into a List
//                List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\parin\\Desktop\\books.txt"));
//
//                // Process each line
//                for (String line : lines) {
//                    // Assuming each line has the format "name,price,author"
//                    String[] parts = line.split(",");
//                    if (parts.length == 3) {
//                        String name = parts[0];
//                        double price = Double.parseDouble(parts[1]);
//                        String author = parts[2];
//                        Document book = new Document("name", name)
//                                .append("price", price)
//                                .append("author", author);
////                        org.bson.Document doc  = (org.bson.Document) new org.bson.Document("Name:", name);
//
//                        collection.insertOne(book);
//                        System.out.println("--- Insertion completed ---");
//
//                        // Do something with the data (e.g., print it)
//                        System.out.println("Name: " + name + ", Price: " + price + ", Author: " + author);
//
//                    } else {
//                        System.out.println("Invalid line format: " + line);
//                    }
//                }
//            } catch (IOException e) {
//                // Handle file reading errors
//                e.printStackTrace();
//            }
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





//        try{
////          create a connection to mongodb server
//            MongoClient mongoClient = new MongoClient(HOST, PORT);
//
////          create a database name
//            MongoDatabase mongoDatabase = mongoClient.getDatabase("Confab");
//
////          create a collection
//            MongoCollection coll = mongoDatabase.getCollection("Attendance");
//
////          get the values of the fields
//            Document doc = new Document("firstname", firstname.getText())
//                    .append("lastname", lastname.getText())
//                    .append("email", email.getText())
//                    .append("gender", gender.getValue())
//                    .append("phone_number", pnumber.getText());
//
////          save the document
//            coll.insertOne(doc);
//
////          display a success message
//            status.setText("Saved Successfully!!!");
//
////          set the fields to null or empty
//            firstname.setText("");
//            lastname.setText("");
//            email.setText("");
//            gender.setValue(null);
//            pnumber.setText("");
//        }
//        catch (Exception e){
//            System.out.println(e.getClass().getName() + ": " + e.getMessage());
////          display the error message
//            status.setText("Failed to save");
//        }
    }

//    public void goToAttendanceList() throws Exception{
////      get the current window
//        Stage stage = (Stage)att.getScene().getWindow();
//
////      close the current window
//        stage.close();
//
////      load the attendance list window
//        Parent root = FXMLLoader.load(getClass().getResource("AttendanceList.fxml"));
//        primaryStage.setTitle("Attendance List");
//        primaryStage.setScene(new Scene(root, 747, 400));
//        primaryStage.show();
//
//    }


//}