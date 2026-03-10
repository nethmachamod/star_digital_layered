package lk.ijse.star_digitalBook;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        setRoot("login");
        stage.show();
        
    }

    public static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(App.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
