/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.Eshop;
import static model.Eshop.primaryStage;
import model.I18N;

/**
 *
 * @author Shaquillo
 */
public class DailyBillController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label labelUsername;

    @FXML
    private JFXButton buttonDisconnect;

    @FXML
    private JFXButton buttonFactorisation;

    @FXML
    private JFXButton buttonCategory;

    @FXML
    private JFXButton buttonProduct;

    @FXML
    private JFXButton buttonCllient;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> columnClientId;

    @FXML
    private TableColumn<?, ?> columnProductId;

    @FXML
    private TableColumn<?, ?> columnProductQty;

    @FXML
    private TableColumn<?, ?> columnTotalPrice;

    @FXML
    private JFXDatePicker facturationDate;

    @FXML
    private JFXComboBox<String> comboBoxSearchBy;
    
    @FXML
    private TextField textfieldSearch;

    @FXML
    private Button buttonSearch;

    @FXML
    private JFXComboBox<String> comboBoxLanguage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxSearchBy.getItems().addAll(I18N.get("column.clientId"), I18N.get("column.productId"), I18N.get("column.qty"), I18N.get("column.totalPrice"));
        comboBoxSearchBy.getSelectionModel().selectFirst();
        
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
                loadPage("/view/pages/dailyBill.fxml", I18N.getLocale());
            } catch (IOException ex) {
                Logger.getLogger(DailyBillController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    @FXML
    void loadCategoriesPage(ActionEvent event) throws IOException {
        loadPage("/view/pages/categories.fxml", I18N.getLocale());
    }

    @FXML
    void loadClientPage(ActionEvent event) throws IOException {
        loadPage("/view/pages/clients.fxml", I18N.getLocale());
    }

    @FXML
    void loadFactoryPage(ActionEvent event) throws IOException {
        
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
