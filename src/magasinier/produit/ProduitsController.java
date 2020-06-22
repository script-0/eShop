/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.produit;

import magasinier.categorie.CategoriesController;
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
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.LoginController;
import magasinier.dashboard.DashboardController;

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
    
    /************ Pagination ************/
    
    @FXML
    private Pagination pagination;
    
     void initPagination(){
        if(sortedData.isEmpty()) {
            pagination.setPageCount(0);
            pagination.setCurrentPageIndex(0);
            pagination.setMaxPageIndicatorCount(0);
            tableView.getItems().clear();
            return;
        }
        int tmp =  (int)Math.ceil(sortedData.size()/5.0);
        pagination.setPageCount(tmp);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(tmp);
        tableView.getItems().clear();

        int f = 5;
        if(f >= sortedData.size()) f = sortedData.size();

        tableView.getItems().addAll(sortedData.subList(0, f));

        pagination.setPageFactory((param) -> {
        tableView.getItems().clear();

        int fin = 5*param+5;
        if(fin >= sortedData.size()) fin = sortedData.size();

        if(sortedData.size()>5*param) tableView.getItems().addAll(sortedData.subList(5*param, fin));

        return this.tableView;
    });            
    }
    
    
    /******************** User popup ********************/
    
    @FXML
    private Pane userPopup;
    @FXML
    private JFXButton historique;
    
    @FXML
    public void hideUserPopup(MouseEvent e){
        ControllerUtils.closeTransition(userPopup);
        userPopup.setVisible(false);
    }
    
    @FXML
    public void showUserPopup(MouseEvent e){        
        userPopup.setVisible(true);
        ControllerUtils.openTransition(userPopup);
    }
    
    /********************** Tableview Popup *************************/
    
    @FXML
    private Pane popup;
    
    public void showPopup(MouseEvent e){
        popup.relocate(e.getSceneX(), e.getSceneY()+3);
        popup.setVisible(true);
        ControllerUtils.openTransition(popup);
    }
    
    @FXML
    public void hidePopup(MouseEvent e){
        ControllerUtils.closeTransition(popup);
        popup.setVisible(false);
    }
    
    @FXML
    public void modifyPopup(){
        modify();
        hidePopup(null);
    }
    
    @FXML
    public void afficherPopup(){
        afficher();
        hidePopup(null);
    }
    
    @FXML
    public void deletePopup(){
        delete();
        hidePopup(null);
    }
    
    /************************** /******************************/ 
    @FXML
    private JFXButton delete;
    @FXML
    void delete() {
            
         ObservableList<Produits> items = tableView.getSelectionModel().getSelectedItems();
         if(items == null)
         {
             //message d'erreur "Pas d'elements selectionnes
         }else{
              //**supprimer l'enregistrement selectionne
             //**Retirer a la tableView
             items.forEach((data) -> {
                // fournisseursUtils.delete(data); //System.out.println("Deleting to DB Successfully");
                tableView.getItems().remove(data);
                listProduits.remove(data);
             });
             initPagination();
         }
    }    
    /****************************** /*************************************/

    @FXML
    void add() {
        launchEditProduit(-1);
    }
    
    @FXML
    void modify() {
        launchEditProduit(listProduits.indexOf(tableView.getSelectionModel().getSelectedItem()) );
    }
    
    void afficher(){
        launchAfficherProduit(listProduits.indexOf(tableView.getSelectionModel().getSelectedItem()));
    }
    
    private void launchAfficherProduit(int i) {
        Stage tmp = new Stage();
             
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/produit/viewProduits.fxml"));
             
             try{ 
             Pane pane = (Pane)loader.load();
             ViewProduitsController con = loader.getController();
             con.setup(listProduits, i);
             Scene newScene = new Scene(pane);
             newScene.setFill(Color.TRANSPARENT);
             tmp.setScene(newScene);   
             tmp.setResizable(false);             
             
             tmp.initStyle(StageStyle.TRANSPARENT);
             tmp.initModality(Modality.APPLICATION_MODAL);
             tmp.showAndWait();
             } catch (IOException ex) {
             Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    /* ***************************table view ****************/
    @FXML
    private TableView<Produits> tableView;

    @FXML
    private TableColumn<Produits, String> codeCol;

    @FXML
    private TableColumn<Produits, String> nomCol;

    @FXML
    private TableColumn<Produits, Double> prixCol;

    @FXML
    private TableColumn<Produits, Double> qteCol;

    @FXML
    private TableColumn<Produits, String> dateCol;

    @FXML
    private TableColumn<Produits, String> etatCol;

    @FXML
    private TableColumn<Produits, String> categorieCol;
    
    static ObservableList<Produits> listProduits = FXCollections.observableArrayList();
    
    FilteredList<Produits> filteredData;
    
    SortedList<Produits> sortedData ;
    
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
    
    ObservableList<Produits> elements = FXCollections.observableArrayList();
    
    public void loadSuppliers(){
        //Load Suppliers from Database
        tableView.setItems(elements);
/* 4 ******************** Lire les produits de la B.D.*/
        listProduits.add(new Produits("P001","Broli-Spaghetti",1120,1100,1000,"Patte alimentaire (Sphatteti) Broli","f081","23-04-2020","Enabled","Agro Alimentaire"));
        listProduits.add(new Produits("E018","Micro-Onde",101000,100000,50,"Micro onde","f011","20-03-2020","Enabled","Electro-Menager"));
        listProduits.add(new Produits("C081","Carotina",2000,2200,80,"Lait de Toilette Carotina","f101","04-05-2020","Enabled","Cosmetique"));
        
        listProduits.add(new Produits("P001","Broli-Spaghetti",1120,1100,1000,"Patte alimentaire (Sphatteti) Broli","f081","23-04-2020","Enabled","Agro Alimentaire"));
        listProduits.add(new Produits("E018","Micro-Onde",101000,100000,50,"Micro onde","f011","20-03-2020","Enabled","Electro-Menager"));
        listProduits.add(new Produits("C081","Carotina",2000,2200,80,"Lait de Toilette Carotina","f101","04-05-2020","Enabled","Cosmetique"));
        
        listProduits.add(new Produits("P001","Broli-Spaghetti",1120,1100,1000,"Patte alimentaire (Sphatteti) Broli","f081","23-04-2020","Enabled","Agro Alimentaire"));
        listProduits.add(new Produits("E018","Micro-Onde",101000,100000,50,"Micro onde","f011","20-03-2020","Enabled","Electro-Menager"));
        listProduits.add(new Produits("C081","Carotina",2000,2200,80,"Lait de Toilette Carotina","f101","04-05-2020","Enabled","Cosmetique"));
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
                        if(person.getCode().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 1:
                        if(person.getName().toLowerCase().contains(lowerCaseFilter)) return true;
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
           initPagination();
        });
        
        // Wrap the FilteredList in a SortedList. 
        sortedData = new SortedList<>(filteredData);
        
        //Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        
        //Add sorted (and filtered) data to the table.
        initPagination();
        
        /*//Sauvegarde le plus grand Id pour les ajouts futurs
        maxId = listSuppliers.get(listSuppliers.size()-1).getId();*/
        
    }
    
    void launchEditProduit(int p){
        try {
             Stage tmp = new Stage();
             //tmp.initStyle(StageStyle.UNDECORATED);
             tmp.initModality(Modality.APPLICATION_MODAL);
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/produit/editProduits.fxml"));
             
             Pane pane = (StackPane)loader.load();
             EditProduitsController con = loader.getController();
             con.setItems(listProduits);
             if(p!=-1){
                 con.loadData(p);
             }else{
                 con.setType(true);
             }
             Scene newScene = new Scene(pane);
             tmp.setScene(newScene);
             tmp.setResizable(false);             
             tmp.showAndWait();
             ControllerUtils.launchTransition(pane);
         } catch (IOException ex) {
             Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @FXML 
    public void tableViewClicked(MouseEvent e){
        if(e.isPopupTrigger()) showPopup(e);
        
        if(e.getClickCount()>2){
            if(!tableView.getSelectionModel().getSelectedIndices().isEmpty())
                launchEditProduit(tableView.getSelectionModel().getSelectedIndices().get(0));
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
    void categories() {
        try {
             Stage tmp=(Stage) lien.getScene().getWindow();  
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/categorie/categories.fxml"));
             Scene newScene = new Scene(loader.load());
             CategoriesController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmp.setScene(newScene);
             tmp.hide();
             tmp.show();
             tmp.setMaximized(true);
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
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
             FXMLLoader loader=new FXMLLoader(getClass().getResource(test?"/magasinier/dashboard/dashboard.fxml":"/login/login.fxml"));
            Scene newScene;
             if(test){
                  AnchorPane root = (AnchorPane)loader.load();
                  newScene =  new Scene(root);
                  DashboardController controller = loader.getController();
                 controller.loadResource(langue.getValue());
             }else{
                BorderPane root = (BorderPane)loader.load();
                newScene =  new Scene(root);
                LoginController controller = loader.getController();
                controller.loadResource(langue.getValue());
             }
             tmp.setScene(newScene);
             tmp.sizeToScene();
             /*tmp.hide();
             tmp.show();*/
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @FXML
    void print(ActionEvent event) {

    }

    @FXML
    void refresh() {
        initPagination();
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
        popup.setVisible(false);
        userPopup.setVisible(false);
        titres = Internationalization.initLanguage(langue);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
        initSearch();
        initializeTableView();
        modify.setDisable(true);
        delete.setDisable(true);
    }
    
}
