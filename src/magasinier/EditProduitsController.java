/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class EditProduitsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Label lien;

    @FXML
    private Label lien1;

    @FXML
    private Label lien11;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXButton valider;

    @FXML
    private JFXButton annuler;

    @FXML
    private JFXTextField code;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField quantite;

    @FXML
    private JFXTextField fournisseur;

    @FXML
    private JFXToggleButton actif;

    @FXML
    private JFXTextField prixA;

    @FXML
    private JFXTextField prixV;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXComboBox<String> categorie;

    @FXML
    void annuler() {
        close();
    }
    
    @FXML
    void valider() {

    }   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML void close(){
        Stage tmp = (Stage)lien.getScene().getWindow();
        tmp.close();
    }
}
