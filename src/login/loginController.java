/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import magasinier.dashboardController;
import caissier.facturationController;
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
import java.util.Locale;
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
public class loginController implements Initializable {
    
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
    
    Locale en = new Locale("en");
    
    Locale fr = new Locale("fr");    
    
    String lang = "En";

    @FXML
    void logIn(ActionEvent event) {
        boolean test = asAdmin.isSelected();
         try {
             Stage tmp = (Stage) ((JFXButton)event.getSource()).getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource(test?"/magasinier/dashboard.fxml":"/caissier/facturation.fxml"));
             Scene newScene = new Scene(loader.load());
             if(test){
                 dashboardController controller = loader.getController();
                 controller.loadResource(lang);
             }else{
                 facturationController controller = loader.getController();
                 controller.loadResource(lang);
             }
             
             
             tmp.setScene(newScene);
             //tmp.setMaximized(true);
            /* tmp.sizeToScene();
             tmp.centerOnScreen();*/
             tmp.setMaximized(false);
             tmp.setMaximized(true);
         } catch (IOException ex) {
             Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }

    @FXML
    void passwordForget(ActionEvent event) {
        
    }

    @FXML
    void languageChanged(ActionEvent event) {
        loadResource(lang.equals("En")?"Fr":"En");
    }
    
    public void loadResource(String language){
        if(language.equalsIgnoreCase("Fr")){
            titres = ResourceBundle.getBundle("resources/languages/login/login",fr);
            lang = "Fr";
        }
        else{
            titres = ResourceBundle.getBundle("resources/languages/login/login",en);
            lang="En";
        }
        setResource();
    
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        langue.getItems().addAll("Francais","English");
        langue.setValue("English");
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
    
}
