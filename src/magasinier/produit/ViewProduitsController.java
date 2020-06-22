/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.produit;

/**
 *
 * @author Isaac
 */

import Utils.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import magasinier.produit.Produits;

public class ViewProduitsController implements Initializable{
    
    private ImageView apercuImg;

    @FXML
    private Label nom;

    @FXML
    private Label code;

    @FXML
    private Label prix;

    @FXML
    private Label quantite;

    @FXML
    private Label categorie;
    
    @FXML
    private Pane apercuPane;
    
    @FXML
    private Pagination pagination;
    
    ObservableList<Produits> items ;
    
     @FXML
    private JFXButton modify;

    @FXML
    void modify(){
        try {
             Stage tmp = new Stage();
             //tmp.initStyle(StageStyle.UNDECORATED);
             tmp.initModality(Modality.APPLICATION_MODAL);
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/produit/editProduits.fxml"));
             
             Pane pane = (StackPane)loader.load();
             EditProduitsController con = loader.getController();
             con.setItems(items);
             con.loadData(pagination.getCurrentPageIndex());
             
             Scene newScene = new Scene(pane);
             tmp.setScene(newScene);
             tmp.setResizable(false);             
             tmp.showAndWait();
             ControllerUtils.launchTransition(pane);
         } catch (IOException ex) {
             Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML void close(){
        Stage tmp = (Stage)nom.getScene().getWindow();
        tmp.close();
    }

    public void loadData(int i){
        Produits p = items.get(i);
        nom.setText(p.getName());
        code.setText(p.getCode());
        prix.setText(""+p.getPrixV());
        quantite.setText(""+p.getQte());
        categorie.setText(p.getCategorie());
    }
    
    public void setup(ObservableList<Produits> list, int selectedIndex){
        this.items = list;
        pagination.setPageCount(list.size());
        pagination.setCurrentPageIndex(selectedIndex);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory((param) -> {
            loadData(param);
            return this.apercuPane;
        });
        loadData(selectedIndex);
        this.items.addListener((Observable c) -> {
            loadData(pagination.getCurrentPageIndex());
        });
    }
}
