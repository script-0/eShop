/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier;

import Utils.Internationalization;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import Utils.ControllerUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.LoginController;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class ProduitsController implements Initializable {

     @FXML
    private Label lien;

    @FXML
    private Label lien1;

    @FXML
    private Label slogan;

    @FXML
    private Label username;

    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton produits;

    @FXML
    private JFXButton categories;
    
    @FXML
    private JFXButton print;

    @FXML
    private JFXButton refresh;
    
    @FXML
    private JFXButton add;

    @FXML
    private JFXButton modify;

    /************************** /******************************/ 
    @FXML
    private JFXButton delete;
    @FXML
    void delete() {
            
         ObservableList<produits> items = tableView.getSelectionModel().getSelectedItems();
         if(items == null)
         {
             //message d'erreur "Pas d'elements selectionnes
         }else{
              //**supprimer l'enregistrement selectionne
             //**Retirer a la tableView
             items.forEach((data) -> {
                // fournisseursUtils.delete(data); //System.out.println("Deleting to DB Successfully");
                 listProduits.remove(data);
             });
         }
    }    
    /****************************** /*************************************/

    @FXML
    void add(ActionEvent event) {
        launchEditProduit();
    }
    
     @FXML
    void modify(ActionEvent event) {
        launchEditProduit();
    }
    
    /* ***************************table view ****************/
    @FXML
    private TableView<produits> tableView;

    @FXML
    private TableColumn<produits, String> codeCol;

    @FXML
    private TableColumn<produits, String> nomCol;

    @FXML
    private TableColumn<produits, Double> prixCol;

    @FXML
    private TableColumn<produits, Double> qteCol;

    @FXML
    private TableColumn<produits, String> dateCol;

    @FXML
    private TableColumn<produits, String> etatCol;

    @FXML
    private TableColumn<produits, String> categorieCol;
    
    ObservableList<produits> listProduits = FXCollections.observableArrayList();
    
    FilteredList<produits> filteredData;
    
    SortedList<produits> sortedData ;
    
    int selectedSearchColumn = 1;/* 1 => Code
                                    2 => Nom
                                    3 => prixVente
                                    4 => quantite
                                    5 => date
                                    6 => etat
                                    7 => categorie
                                 */
    
    ArrayList<String> listColumnNames = new ArrayList<>();
    
    public void initializeTableView(){
        //id Column
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        
//name Column
        nomCol.setText(titres.getString("NAME"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        //address Column
        
       prixCol.setText(titres.getString("PRICE_VENTE"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prixV"));
        
        //tel Column
        
        qteCol.setText(titres.getString("QUANTITY"));
        qteCol.setCellValueFactory(new PropertyValueFactory<>("qte"));
       
        //email column
        
        dateCol.setText(titres.getString("DATE_INSERTION"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        
        etatCol.setText(titres.getString("STATE"));
        etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));    
 
        categorieCol.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        categorieCol.setText(titres.getString("CATEGORY"));
        
        TableView.TableViewSelectionModel model = tableView.getSelectionModel();
        model.setSelectionMode(SelectionMode.MULTIPLE);//On peut selectionner plusieurs entrees a la fois
        model.selectedItemProperty().addListener((ObservableValue obs, Object oldSelection, Object newSelection) -> {
            modify.setDisable((newSelection == null));
            delete.setDisable((newSelection == null));
        });
         
        tableView.setPlaceholder(new Label(titres.getString("Produits_PlaceHolder")));
        
        loadSuppliers();
    }
    
     /** Lire la BD et charger tous les fournisseurs dans la tableView i.e. Les ajouter dans l'ArrayList listSuppliers*/
    public void loadSuppliers(){
        //Load Suppliers from Database
        tableView.setItems(null);
        listProduits.add(new produits("P001","Broli-Spaghetti",1120,1100,1000,"Patte alimentaire (Sphatteti) Broli","f081","04-06-2020","Good","Patte Alimentaire"));
        
        /*fournisseursUtils.list().forEach((e)->{
            listSuppliers.add(e);
        });*/
        
        //Wrap the ObservableList in a FilteredList (initially display all data).
        filteredData = new FilteredList<>(listProduits,p->true);
        
        //Set the filter Predicate whenever the filter changes.
        searchTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate(person->{
                if(newValue == null || newValue.isEmpty()){ 
                    searchButton.setVisible(true);
                    stopSearchButton.setVisible(false);
                    return true;
                }
                
                searchButton.setVisible(false);
                stopSearchButton.setVisible(true);
                
                // Compare name of every suppliers with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                int i =searchColumn.getItems().indexOf(searchColumn.getValue());
                switch(i)
                {
                    case 0:
                        if(person.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 1:
                        if(person.getCode().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 2:
                        if(String.valueOf(person.getPrixV()).toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 3:
                        if(String.valueOf(person.getQte()).toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 4:
                        if(person.getDate().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 5:
                        if(person.getEtat().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 6:
                        if(person.getCategorie().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    default:
                        break;
                }
                return false;
            });
        });
        
        // Wrap the FilteredList in a SortedList. 
        sortedData = new SortedList<>(filteredData);
        
        //Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        
        //Add sorted (and filtered) data to the table.
        tableView.setItems(sortedData);
        
        /*//Sauvegarde le plus grand Id pour les ajouts futurs
        maxId = listSuppliers.get(listSuppliers.size()-1).getId();*/
        
    }
    
    void launchEditProduit(){
        try {
             Stage tmp = new Stage();
             //tmp.initStyle(StageStyle.TRANSPARENT);
             tmp.initModality(Modality.APPLICATION_MODAL);
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/editProduits.fxml"));
             Pane pane = (StackPane)loader.load();
             Scene newScene = new Scene(pane);
             tmp.setScene(newScene);             
             tmp.setWidth(488);
             tmp.setHeight(555);
             tmp.setResizable(false);             
             tmp.showAndWait();
             ControllerUtils.launchTransition(pane);
         } catch (IOException ex) {
             Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @FXML void tableViewClicked(MouseEvent e){
        if(e.getClickCount()>2) launchEditProduit();
    }
    
 /* *************************************************************** */ 
    
    /********* search option *********************/
    @FXML
    private TextField searchTextField;

    @FXML
    private JFXButton searchButton;
    
    @FXML
    private JFXButton stopSearchButton;
    
    @FXML
    private Label searchBy;

    @FXML
    private JFXComboBox<String> searchColumn;
    
    void initSearch(){
        searchColumn.getItems().addAll("Code",  titres.getString("NAME"),
                                                titres.getString("PRICE_VENTE"),
                                                titres.getString("QUANTITY"),
                                                titres.getString("DATE_INSERTION"),
                                                titres.getString("STATE"),
                                                titres.getString("CATEGORY"));
        searchColumn.setValue("Code");
        initSearchPane();
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            stopSearchButton.setVisible(!newValue.isEmpty());
            searchButton.setVisible(newValue.isEmpty());
        });
    }
    
    @FXML
    void initSearchPane(){
        searchTextField.clear();
        searchButton.setVisible(true);
        stopSearchButton.setVisible(false);
    }
/*********************************************************/
    
    @FXML
    void categories(ActionEvent event) {

    }

    @FXML
    void logOut() {
        loadInterface(false);
    }

    @FXML
    void dashboard() {
        loadInterface(true);
    }

    void loadInterface(boolean test){
        try {
             Stage tmp = (Stage) lien.getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource(test?"/magasinier/dashboard.fxml":"/login/login.fxml"));
             Scene newScene = new Scene(loader.load());
             if(test){
                 DashboardController controller = loader.getController();
                 controller.loadResource(langue.getValue());
             }else{
                LoginController controller = loader.getController();
                controller.loadResource(langue.getValue());
             }
             tmp.setScene(newScene);
             tmp.hide();
             tmp.show();
             tmp.setMaximized(true);
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @FXML
    void print(ActionEvent event) {

    }

    @FXML
    void refresh(ActionEvent event) {

    }

    /********** Gestion de la langue *************/
    
    @FXML
    private JFXComboBox<String> langue;
    
    ResourceBundle titres;  
    
    public void loadResource(String language){
       titres =Internationalization.getBundle(language);
       setResource();
    }
    
    public void setResource(){
        /* ******** A Completer *************/
        lien.setText(titres.getString("MAGASINIER"));
        lien1.setText(titres.getString("PRODUITS"));
        username.setText(titres.getString("USER"));
        dashboard.setText(titres.getString("DASHBOARD"));
        produits.setText(titres.getString("PRODUITS"));
        categories.setText(titres.getString("CATEGORY"));
        slogan.setText(titres.getString("SLOGAN"));
        
        
        add.setText(titres.getString("ADD")); 
        delete.setText(titres.getString("DELETE")); 
        refresh.setText(titres.getString("REFRESH")); 
        print.setText(titres.getString("PRINT"));
        modify.setText(titres.getString("MODIFY"));
        searchBy.setText(titres.getString("SEARCH_BY"));
        searchTextField.setPromptText(titres.getString("SEARCH_PRODUCTS"));
        
        nomCol.setText(titres.getString("NAME"));
        
        prixCol.setText(titres.getString("PRICE_VENTE"));
        
        qteCol.setText(titres.getString("QUANTITY"));
        
        dateCol.setText(titres.getString("DATE_INSERTION"));
        
        etatCol.setText(titres.getString("STATE"));
        
        categorieCol.setText(titres.getString("CATEGORY"));        
        
        ((Label)tableView.getPlaceholder()).setText(titres.getString("Produits_PlaceHolder"));
        
        int i = searchColumn.getItems().indexOf(searchColumn.getValue());
        searchColumn.getItems().clear();
        searchColumn.getItems().addAll("Code",  titres.getString("NAME"),
                                                titres.getString("PRICE_VENTE"),
                                                titres.getString("QUANTITY"),
                                                titres.getString("DATE_INSERTION"),
                                                titres.getString("STATE"),
                                                titres.getString("CATEGORY"));
        searchColumn.setValue(searchColumn.getItems().get(i));
        
    }

    /************************************************/

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titres = Internationalization.initLanguage(langue);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
        initSearch();
        initializeTableView();
    }    
    
}
