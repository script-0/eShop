/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import Utils.Internationalization;
import magasinier.DashboardController;
import caissier.FacturationController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Isaac
 */
public class LoginController implements Initializable {
    
     @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXCheckBox asAdmin;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton forget;
    
    @FXML
    private JFXComboBox<String> langue;
     
    @FXML
    private Label welcome;

    @FXML
    private Label slogan;
    
    ResourceBundle titres;

    @FXML
    void logIn(ActionEvent event) {
        boolean test = asAdmin.isSelected();
         try {
             Stage tmp = (Stage) ((JFXButton)event.getSource()).getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource(test?"/magasinier/dashboard.fxml":"/caissier/facturation.fxml"));
             Scene newScene = new Scene(loader.load());
             if(test){
                 DashboardController controller = loader.getController();
                 controller.loadResource(langue.getValue());
             }else{
                 FacturationController controller = loader.getController();
                 controller.loadResource(langue.getValue());
             }
             
             tmp.setScene(newScene);
             
             tmp.hide();
             tmp.show();
             tmp.setMaximized(true);
            // tmp.sizeToScene();
             //tmp.centerOnScreen();*/
             //controllerUtils.launchTransition(newScene.getRoot());
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }

    @FXML
    void passwordForget(ActionEvent event) {
        
    }
    
    public void loadResource(String language){
        titres =Internationalization.getBundle(language);
        setResource();
    }
    
    public void setResource(){
        welcome.setText(titres.getString("WELCOME"));
        slogan.setText(titres.getString("SLOGAN"));
        user.setPromptText(titres.getString("USER"));
        pass.setPromptText(titres.getString("PASS"));
        langue.setPromptText(titres.getString("LANG"));
        forget.setText(titres.getString("PASSFORGET"));
        asAdmin.setText(titres.getString("LOGINASADMIN"));
        login.setText(titres.getString("LOG"));
    }    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        titres = Internationalization.initLanguage(langue);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
    }
    
}
