/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.loginController;

/**
 *
 * @author Isaac
 */
public class dashboardController implements Initializable{
    @FXML
    private Label user;

    @FXML
    private Label question;

    @FXML
    private Label magasinier;

    @FXML
    private Label caissier;

    @FXML
    private Label factures;

    @FXML
    private Label stats;

    @FXML
    private Label slogan;
    
    @FXML
    private JFXComboBox<String> langue;

    ResourceBundle titres;
    
    Locale en = new Locale("en");
    
    Locale fr = new Locale("fr");    
    
    String lang = "En";

    
    @FXML
    void languageChanged(ActionEvent event) {
        loadResource(lang.equals("En")?"Fr":"En");
    }
    
    public void loadResource(String language){
        if(language.equalsIgnoreCase("Fr")){
            titres = ResourceBundle.getBundle("resources/languages/admin/dashboard/dashboard",fr);
            lang = "Fr";
            langue.setValue("Francais");
        }
        else{
            titres = ResourceBundle.getBundle("resources/languages/admin/dashboard/dashboard",en);
            lang="En";langue.setValue("English");
        }
        setResource();
    }
    
    public void setResource(){
        slogan.setText(titres.getString("SLOGAN"));
        user.setText(titres.getString("USER"));
        question.setText(titres.getString("QUESTION"));
        langue.setPromptText(titres.getString("LANG"));
        magasinier.setText(titres.getString("MAGASINIER"));
        caissier.setText(titres.getString("CAISSIER"));
        stats.setText(titres.getString("STATS"));
        factures.setText(titres.getString("FACTURES"));
    }
    
    @FXML
    void logOut(MouseEvent event) {
        try {
             Stage tmp = (Stage) ((Pane)event.getSource()).getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/login/login.fxml"));
             Scene newScene = new Scene(loader.load());
             loginController controller = loader.getController();
             controller.loadResource(lang);
             tmp.setScene(newScene);
             //tmp.setMaximized(true);
            /* tmp.sizeToScene();
             tmp.centerOnScreen();*/
             tmp.setMaximized(false);
             tmp.setMaximized(true);
         } catch (IOException ex) {
             Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        langue.getItems().addAll("Francais","English");
    }
}
