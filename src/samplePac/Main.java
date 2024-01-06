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