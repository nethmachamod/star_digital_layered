package lk.ijse.star_digitalBook.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.star_digitalBook.App;
import lk.ijse.star_digitalBook.controller.models.UserModel;
import lk.ijse.star_digitalBook.dto.UserDto;


public class LoginController  {

   

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

   


    private final UserModel userModel = new UserModel();

   

    @FXML
    private void login() {

      
try{
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
               new Alert(Alert.AlertType.ERROR, "Failed to load next employee ID").show();
            } else {

                UserDto userDto = new UserDto(username,password);
                boolean result = userModel.userDetails(userDto);

                if(result){

                    App.setRoot("mainmenu");
 
                }
       

            }
}catch(Exception e){
    e.printStackTrace();
}
                    
    }
}

