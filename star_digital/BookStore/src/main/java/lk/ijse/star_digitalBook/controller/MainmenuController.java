package lk.ijse.star_digitalBook.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.star_digitalBook.App;

public class MainmenuController implements Initializable {
    
    @FXML
    private AnchorPane maincontent;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPage("menumanagement");
    }
    
    public void loadContent(Parent content) {
        if (maincontent != null) {
            maincontent.getChildren().setAll(content);
        }
    }
    
    @FXML
    private void clickmenu() throws IOException {
        Parent menuFXML = App.loadFXML("menumanagement");
        maincontent.getChildren().setAll(menuFXML);
    }
    
    @FXML
    private void clicksales() throws IOException {
        Parent menuFXML = App.loadFXML("salesreport");
        maincontent.getChildren().setAll(menuFXML);
    }
    
    @FXML
    private void clickorder() throws IOException {
        Parent menuFXML = App.loadFXML("ordermanagement");
        maincontent.getChildren().setAll(menuFXML);
    }
    
    @FXML
    private void clicksitems() throws IOException {
        Parent menuFXML = App.loadFXML("itemmanage");
        maincontent.getChildren().setAll(menuFXML);
    }
    
    @FXML
    private void clickemp() throws IOException {
        Parent menuFXML = App.loadFXML("employeemanage");
        maincontent.getChildren().setAll(menuFXML);
    }
    
    @FXML
    private void clickstock() throws IOException {
        Parent menuFXML = App.loadFXML("inventorymanage");
        maincontent.getChildren().setAll(menuFXML);
    }
    
    @FXML
    private void clicksup() throws IOException {
        Parent menuFXML = App.loadFXML("supplier");
        maincontent.getChildren().setAll(menuFXML);
    }
    
    @FXML
    private void loadPage(String page) {
        try {
            Parent root = App.loadFXML(page);
            maincontent.getChildren().setAll(root);
        } catch(IOException e) {
        }
    }
    
    @FXML
    private void clickpay()throws IOException{
        Parent menuFXML = App.loadFXML("paymentmanage");
        maincontent.getChildren().setAll(menuFXML);
        
    }
    
    @FXML
    private void logout()throws IOException{
        Stage currentStage = (Stage) maincontent.getScene().getWindow();
         FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/lk/ijse/star_digitalBook/login.fxml")
                        );
                        Parent root = loader.load();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                        
                        currentStage.close();
    }
}