/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Utils.Internationalization;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import entity.Gestionnaire;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Stephen
 */
public class EditGestionnaireController implements Initializable {

    @FXML
    private Pane rootPane;
    @FXML
    private Label lien;
    @FXML
    private Label lien1;
    @FXML
    private Label lien11;
    @FXML
    private Label title;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXComboBox<String> type;
    @FXML
    private JFXTextField login;
    @FXML
    private JFXTextField nom;
    @FXML
    private JFXTextField adresse;
    @FXML
    private JFXTextField matricule;
    @FXML
    private JFXTextField contact;
    @FXML
    private JFXButton annuler;
    @FXML
    private JFXButton valider;
    @FXML
    private ImageView apercuImg;
    @FXML
    private Label apercuNom;
    @FXML
    private Label apercuMatricule;
    @FXML
    private Label apercuContact;
    @FXML
    private Label apercuType;
    @FXML
    private JFXButton editImg;
    @FXML
    private JFXComboBox<String> langue;
    
    ObservableList<Gestionnaire> sortedData ;
    int index = -1;
    @FXML
    private Label apercuEmail;
    @FXML
    private Label apercuAdresse;
    @FXML
    private Label apercuLogin;
    @FXML
    private JFXToggleButton actif;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Internationalization.initLanguage(langue);
        /*langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });*/
        
        apercuNom.textProperty().bindBidirectional(nom.textProperty());
        apercuMatricule.textProperty().bindBidirectional(matricule.textProperty());
        apercuContact.textProperty().bindBidirectional(contact.textProperty());
        apercuType.textProperty().bindBidirectional(type.valueProperty());
        apercuEmail.textProperty().bindBidirectional(email.textProperty());
        apercuAdresse.textProperty().bindBidirectional(adresse.textProperty());
        apercuLogin.textProperty().bindBidirectional(login.textProperty());
        
        
        remplissage();
    }    

    @FXML
    private void annuler(MouseEvent event) {
        close();
    }

    @FXML
    private void valider(ActionEvent event) {
        Gestionnaire p = new Gestionnaire(Integer.parseInt(matricule.getText()),
                                nom.getText(),
                                type.getValue(), 
                                email.getText(),
                                contact.getText(),
                                adresse.getText(),
                                login.getText(),
                                !actif.isDisable());
        //GestionnairesController.updateBD(p);
        close();
    }

    @FXML
    private void editImg(ActionEvent event) {
    }
    
    public void setType(Boolean b){
        if(b){
            lien11.setText("Ajouter un Produit");
            title.setText("Ajouter un Produit");
        }
    }
    
    public void setItems(ObservableList<Gestionnaire> items){
        sortedData = items;
    }
    
     public void loadData(int i){
        index = i;
        Gestionnaire p = sortedData.get(i);
        matricule.setText(p.getMatricule());
        nom.setText(p.getNom());
        type.setValue(p.getType());
        email.setText(p.getEmail());
        contact.setText(p.getContact());
        adresse.setText(p.getAdresse());
        login.setText(p.getLogin());
        actif.setDisable(!p.getActif());
        
    }
    
    public void loadTypes(){
        type.getItems().addAll("Magasinier","Caissier","Administrateur");
    }
    
    public void initType() {
        type.getItems().addAll("Admin", "Magasinier", "Caissier");
        type.setValue("Admin");
    }
    
    public void remplissage() {
        initType();
        valider.disableProperty().bind(
            Bindings.isEmpty(matricule.textProperty())
            .or(Bindings.isEmpty(nom.textProperty()))
            .or(Bindings.isEmpty(email.textProperty()))
            .or(Bindings.isEmpty(contact.textProperty()))
            .or(Bindings.isEmpty(adresse.textProperty()))
            .or(Bindings.isEmpty(login.textProperty()))
        );
    }
    
    public void close() {
        Stage tmp = (Stage) lien.getScene().getWindow();
        tmp.close();
    }
    
  /**** UPDATE_MONTHE ******/  
    
    public void loadOnBD(Gestionnaire e) {
        
        /********************************/
            // Mise à jour des éléments dans la BD
        /********************************/
    }
    
    public int getFormat() {
        return Integer.parseInt(matricule.getText().substring(0, 3) + matricule.getText().substring(4, 7));
    }
}
