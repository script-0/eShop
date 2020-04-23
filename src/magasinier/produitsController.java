/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import controllerUtils.controllerUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import login.loginController;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class produitsController implements Initializable {

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

    }
    
     @FXML
    void modify(ActionEvent event) {

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
      //  idColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
        
//name Column
        nomCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        //nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        //address Column
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prixV"));
        
        //tel Column
        qteCol.setCellValueFactory(new PropertyValueFactory<>("qte"));
       
        //email column
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));    
 
        categorieCol.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        
        TableView.TableViewSelectionModel model = tableView.getSelectionModel();
        model.setSelectionMode(SelectionMode.MULTIPLE);//On peut selectionner plusieurs entrees a la fois
        model.selectedItemProperty().addListener((ObservableValue obs, Object oldSelection, Object newSelection) -> {
            modify.setDisable((newSelection == null));
            delete.setDisable((newSelection == null));
        });
         
        tableView.setPlaceholder(new Label("Pas de produit"));
        
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
                
                switch(searchColumn.getValue())
                {
                    case "Nom":
                        if(person.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case "Code":
                        if(person.getCode().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case "PrixVente":
                        if(String.valueOf(person.getPrixV()).toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case "Quantité":
                        if(String.valueOf(person.getQte()).toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case "Date":
                        if(person.getDate().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case "Etat":
                        if(person.getEtat().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case "Catégorie":
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
    
    @FXML void tableViewClicked(MouseEvent e){
         try {
             Stage tmp = new Stage();
             //tmp.initStyle(StageStyle.TRANSPARENT);
             tmp.initModality(Modality.APPLICATION_MODAL);
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/editProduits.fxml"));
             Pane pane = (Pane)loader.load();
             Scene newScene = new Scene(pane);
             tmp.setScene(newScene);             
             tmp.setWidth(488);
             tmp.setHeight(5551);
             tmp.setResizable(false);
             
             tmp.showAndWait();
             controllerUtils.launchTransition(pane);
         } catch (IOException ex) {
             Logger.getLogger(produitsController.class.getName()).log(Level.SEVERE, null, ex);
         }
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
    
    void loadColumnNames(){
        listColumnNames.add("Code");
        listColumnNames.add("Nom");
        listColumnNames.add("PrixVente");
        listColumnNames.add("Quantité");
        listColumnNames.add("Date");
        listColumnNames.add("Etat");
        listColumnNames.add("Catégorie");
    }
    
    void initSearch(){
        loadColumnNames();
        searchColumn.getItems().addAll("Code","Nom","PrixVente","Quantité","Date","Etat","Catégorie");
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
    void logOut(MouseEvent event) {
        loadInterface(false,event);
    }

    @FXML
    void dashboard(MouseEvent event) {
        loadInterface(true,event);
    }

    void loadInterface(boolean test,MouseEvent event){
        try {
             Stage tmp = (Stage) ((JFXButton)event.getSource()).getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource(test?"/magasinier/dashboard.fxml":"/login/login.fxml"));
             Scene newScene = new Scene(loader.load());
             if(test){
                 dashboardController controller = loader.getController();
                 controller.loadResource(lang);
             }else{
                loginController controller = loader.getController();
                controller.loadResource(lang);
             }
             
             
             tmp.setScene(newScene);
             tmp.setMaximized(false);
             tmp.setMaximized(true);
         } catch (IOException ex) {
             Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    Locale en = new Locale("en");
    
    Locale fr = new Locale("fr");    
    
    String lang = "En";
    
    @FXML
    void languageChanged(){
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
        /* slogan.setText(titres.getString("SLOGAN"));
        user.setText(titres.getString("USER"));
        question.setText(titres.getString("QUESTION"));
        langue.setPromptText(titres.getString("LANG"));
        magasinier.setText(titres.getString("MAGASINIER"));
        caissier.setText(titres.getString("CAISSIER"));
        stats.setText(titres.getString("STATS"));
        factures.setText(titres.getString("FACTURES"));*/
    }

    /************************************************/

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        langue.getItems().addAll("Francais","English");
        langue.setValue("English");
        initSearch();
        initializeTableView();
    }    
    
}
