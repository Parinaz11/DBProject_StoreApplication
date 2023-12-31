package samplePac;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


// Github project link: 'https://github.com/Parinaz11/DBProject_StoreApplication.git/'



public class Main extends Application {
    @Override
    //  create a window
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        primaryStage.setTitle("Store Application");
        // Set the icon for the title bar
        primaryStage.getIcons().add(new Image("src/images/icon.png"));
        primaryStage.setScene(new Scene(root)); // , 300, 275
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}

//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class Main extends Application {
////    @Override
////    public void start(Stage stage) throws IOException {
////
////        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
////        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
////        stage.setTitle("Hello!");
////        stage.setScene(scene);
////        stage.show();
////    }
//
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Attendance");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }
//    public static void main(String[] args) {
//        launch();
//    }
//}