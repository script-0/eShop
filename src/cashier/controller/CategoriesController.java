/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Eshop;
import static model.Eshop.primaryStage;
import model.I18N;

/**
 * FXML Controller class
 *
 * @author Shaquillo
 */
public class CategoriesController implements Initializable {
    
    public static boolean loadProdFromCat = false;

    @FXML
    private Label labelUsername;

    @FXML
    private JFXButton buttonDisconnect;
    
    @FXML
    private JFXButton buttonFactorisation;

    @FXML
    private JFXButton buttonProduct;

    @FXML
    private JFXButton buttonCllient;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableColumn<?, ?> columnQty;

    @FXML
    private JFXButton buttonProducts;

    @FXML
    private TextField textfieldSearch;

    @FXML
    private Button buttonSearch;

    @FXML
    private JFXComboBox<String> comboBoxLanguage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        comboBoxLanguage.getItems().addAll(I18N.get("language.english"),rb.getString("language.french" ));
        if(I18N.getLocale() == Locale.ENGLISH){
            comboBoxLanguage.getSelectionModel().selectFirst();
        } else {
            comboBoxLanguage.getSelectionModel().selectLast();
        } 
        comboBoxLanguage.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(I18N.get("language.english"))){
                I18N.setLocale(Locale.ENGLISH);
            } else {
                I18N.setLocale(Locale.FRENCH);
            }
            try {
                loadPage("/view/pages/categories.fxml", I18N.getLocale());
            } catch (IOException ex) {
                Logger.getLogger(DailyBillController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }  
    
    @FXML
    void loadClientPage(ActionEvent event) throws IOException {
        loadPage("/view/pages/clients.fxml", I18N.getLocale());
    }

    @FXML
    void loadFactorisationPage(ActionEvent event) throws IOException {
        loadPage("/view/pages/dailyBill.fxml", I18N.getLocale());
    }

    @FXML
    void loadProductCat(ActionEvent event) throws IOException {
        loadProdFromCat = true;
        loadPage("/view/pages/products.fxml", I18N.getLocale());
    }

    @FXML
    void loadProductPage(ActionEvent event) throws IOException {
        loadPage("/view/pages/products.fxml", I18N.getLocale());
    }

    @FXML
    void search(ActionEvent event) {

    }
    
    @FXML
    void logout(ActionEvent event) {

    }
    
    private void loadPage(String path, Locale locale) throws IOException{
        Eshop.bundle = ResourceBundle.getBundle("language/Bundle", locale);
        Parent root = FXMLLoader.load(getClass().getResource(path), Eshop.bundle);
        primaryStage.getScene().setRoot(root);
    }
    
}
