/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Utils.Internationalization;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    private Label lien;

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
    
    public void loadResource(String language){
       titres =Internationalization.getBundle(language);
       setResource();
    }
    
    public void setResource(){
        slogan.setText(titres.getString("SLOGAN"));
        user.setText(titres.getString("USER"));
        lien.setText(titres.getString("DASHBOARD"));
        question.setText(titres.getString("QUESTION"));
        langue.setPromptText(titres.getString("LANG"));
        magasinier.setText(titres.getString("MAGASINIER"));
        caissier.setText(titres.getString("CAISSIER"));
        stats.setText(titres.getString("STATS"));
        factures.setText(titres.getString("FACTURES"));
    }
    
    @FXML
    void logOut() {
        try {
             Stage tmp = (Stage)lien.getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/login/login.fxml"));
             Scene newScene = new Scene(loader.load());
             loginController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmp.hide();
             tmp.setScene(newScene);
             tmp.show();
         } catch (IOException ex) {
             Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Internationalization.initLanguage(langue);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
    }
}
