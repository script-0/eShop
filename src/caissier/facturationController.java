/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caissier;

import Utils.Internationalization;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.loginController;

public class facturationController implements Initializable {

    @FXML
    private Label lien;

    @FXML
    private Label slogan;

    @FXML
    private Label username;

    @FXML
    private JFXButton facturation;

    @FXML
    private JFXButton produits;

    @FXML
    private JFXButton categories;

    @FXML
    private JFXButton clients;

    @FXML
    private JFXTextField idClient;

    @FXML
    private JFXTextField codeProduit;

    @FXML
    private JFXTextField remise;

    @FXML
    private Label stock;

    @FXML
    private JFXTextField quantite;

    @FXML
    private JFXToggleButton eMoney;

    @FXML
    private ImageView add;

    @FXML
    private Label total;

    @FXML
    private Label net;

    @FXML
    private Label reliquat;

    @FXML
    private JFXTextField montantRemi;

    @FXML
    private JFXButton valider;

    @FXML
    private JFXButton apercu;

    @FXML
    private JFXButton annuler;

    @FXML
    private JFXButton del;

    @FXML
    private JFXButton recettes;
    

    @FXML
    private TableView<achats> panier;
    
    @FXML
    private TableColumn<achats, String> codeCol;

    @FXML
    private TableColumn<achats, Double> qteCol;

    @FXML
    private TableColumn<achats, Double> prixUCol;

    @FXML
    private TableColumn<achats, Double> prixTCol;

    
    @FXML
    private Text nomP;

    @FXML
    private Text prixP;

    @FXML
    private Text stateP;

    @FXML
    private Text dateP;

    @FXML
    private Text categorieP;
    
    
    @FXML
    private JFXComboBox<String> langue;

    ObservableList<achats> listAchats = FXCollections.observableArrayList();
    
    double tmp,tmp2,tmp3;
    @FXML
    void add(MouseEvent event) {
        //apres verification
        if(!quantite.getText().equals("") && !prixP.getText().equals("NaN")){
            tmp = Double.valueOf(quantite.getText().replace(" ",""));
            tmp2 =  Double.valueOf(prixP.getText().replace(" ",""));
            try{
             tmp3 = Double.valueOf(remise.getText().replace(" ",""));
            }catch(Exception e){
                tmp3=0;
            }
            
            System.out.println(">new Achats: ["+codeProduit.getText()+", "+tmp+", "+tmp2+", "+tmp*tmp2*(1-tmp3/100));
            
            listAchats.add(new achats(codeProduit.getText(),tmp,tmp2,tmp*tmp2*(1-tmp3/100)));

            stock.setText( ""+(Integer.valueOf(stock.getText().replace(" ","")) - Integer.valueOf(quantite.getText().replace(" ",""))));
        }
    }

    @FXML
    void annuler(ActionEvent event) {

    }

    @FXML
    void apercu(ActionEvent event) {

    }

    @FXML
    void categories(ActionEvent event) {

    }

    @FXML
    void client(ActionEvent event) {

    }

    @FXML
    void del(ActionEvent event) {
        panier.getSelectionModel().getSelectedItems().forEach((a)->{
            panier.getItems().remove(a);
        });
    }

    @FXML
    void logOut() {
        try {
             Stage tmpStage = (Stage)lien.getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/login/login.fxml"));
             Scene newScene = new Scene(loader.load());
             loginController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmpStage.hide();
             tmpStage.setScene(newScene);
             tmpStage.show();
         } catch (IOException ex) {
             Logger.getLogger(facturationController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    void produits(ActionEvent event) {

    }

    @FXML
    void valider(ActionEvent event) {

    }

    ResourceBundle titres;
    
    public void loadResource(String language){
        titres =Internationalization.getBundle(language);
        setResource();
    }
    
    public void setResource(){
        /*********************** ***********A Faire /*****************************/
    }
     
    void buildReliquat(){
         try{
                totalValue = Double.valueOf(net.getText());
                montantValue = Double.valueOf(montantRemi.getText().replace(" ",""));
                reliquat.setText(""+(montantValue - totalValue));
            }catch(NumberFormatException e){
                reliquat.setText("NaN");
            }
    }
    
    double totalValue,montantValue;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        net.textProperty().addListener((observable, oldValue, newValue) -> {
            buildReliquat();
        });
        
        listAchats.addListener((javafx.beans.Observable observable) -> {
            totalValue = 0.0;
            if(!listAchats.isEmpty()){
                listAchats.forEach((a) -> {
                    totalValue+=a.getPrixT();
                });
                total.setText(String.format("%.2f",totalValue));
                net.setText(String.format("%.2f",totalValue));
            }else{
                total.setText("0.00");
                net.setText("0.00");
            }
        });
        
        montantRemi.textProperty().addListener((observable, oldValue, newValue) -> {
           buildReliquat();
        });
        
        titres = Internationalization.initLanguage(langue);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
        
        initializeTableView();
        
        initializeProductInfos();
        
        
   } 
    
   public void initializeLanguage(){
         langue.getItems().addAll("Francais","English");
        langue.setValue("English");
    }
   
   
   Label placeHolder = new Label("No products");
    
   public void initializeTableView(){
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        qteCol.setCellValueFactory(new PropertyValueFactory<>("qte"));
        
        prixUCol.setCellValueFactory(new PropertyValueFactory<>("prixU"));
        
        prixTCol.setCellValueFactory(new PropertyValueFactory<>("prixT"));
        
        TableView.TableViewSelectionModel model = panier.getSelectionModel();
        model.setSelectionMode(SelectionMode.MULTIPLE);//On peut selectionner plusieurs entrees a la fois
        
        model.selectedItemProperty().addListener((ObservableValue obs, Object oldSelection, Object newSelection) -> {
            //modify.setDisable((newSelection == null));
            del.setDisable(model.getSelectedItems().isEmpty());
        });
         
        panier.setPlaceholder(placeHolder);
        
        panier.setItems(null);
        panier.setItems(listAchats);
    }
     
   public void initializeProductInfos(){
         codeProduit.textProperty().addListener((observable, oldValue, newValue) -> {
             // si une certaine condition, charge les infos relatives aux produits
             if(newValue.length() >3){
                nomP.setText("Broli-Spaghetti");
                prixP.setText("1 120");
                stateP.setText("Good");
                dateP.setText("04-06-20");
                categorieP.setText("Pate Alimentaire");

                stock.setText("1000");
             }
         });
     }   

}
