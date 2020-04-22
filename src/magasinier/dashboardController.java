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
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.loginController;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class dashboardController implements Initializable {

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
    private ScrollPane chartPane;

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
    @FXML
    void categories(ActionEvent event) {

    }

    @FXML
    void logOut(MouseEvent event) {
       loadInterface(false,event);
    }
    
    void loadInterface(boolean test,MouseEvent event){
        try {
             Stage tmp = (Stage) ((JFXButton)event.getSource()).getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource(test?"/magasinier/produits.fxml":"/login/login.fxml"));
             Scene newScene = new Scene(loader.load());
             if(test){
                 produitsController controller = loader.getController();
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
    void produits(MouseEvent event) {
        loadInterface(true,event);
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        langue.getItems().addAll("Francais","English");
        langue.setValue("English");
        chartPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            chartPane.setFitToWidth(newValue.intValue() >1000);
        });
        
         // flowGraph
                initFlowGraph();
         

       //proportionsCategorie Graph
                initProportionsCategorie();
                
    	//typeProduit Graph
                initTypeProduit();

    	//salesGraph
                initSales();        
    }    
    
    
    /********** Flow graph *************/
    
    @FXML
    private JFXButton refreshFlow;
    
    @FXML
    private AreaChart<String, Number> flowGraph;
    
    XYChart.Series<String,Number> entryChart = new XYChart.Series<>();
   
    XYChart.Series<String,Number> exitChart = new XYChart.Series<>();
    
    XYChart.Series<String,Number> differenceChart = new XYChart.Series<>();
    
    XYChart.Series<String,Number> salesChart = new XYChart.Series<>();
    
    @FXML
    void refreshFlow(ActionEvent event) {
        differenceChart.getData().clear();
        loadDifferenceData();
        
        entryChart.getData().clear();
        loadEntryData();
        
        exitChart.getData().clear();
        loadExitData();
    }
    
    void initFlowGraph(){
        flowGraph.setTitle("Flow du stock");
        flowGraph.getXAxis().setLabel("Semaine");
        flowGraph.getYAxis().setLabel("nb Produits(x 100)");

        loadDifferenceData();
        flowGraph.getData().add(differenceChart);

        loadEntryData();
        flowGraph.getData().add(entryChart);

        loadExitData();
        flowGraph.getData().add(exitChart);
    }
    
    void loadDifferenceData(){
            differenceChart.setName("etat");
        /*// resultSet va contenir les jours et les differences du mois en cours
    		while(resultSet.next()){
    			differenceChart.getData.add(new XYChart.Data<Number, Number>(resultSet.getInt(),result.getDouble()));
    		}*/
                differenceChart.getData().add(new XYChart.Data<>("0",0));
                differenceChart.getData().add(new XYChart.Data<>("1",-11));
                differenceChart.getData().add(new XYChart.Data<>("2",-11));
                differenceChart.getData().add(new XYChart.Data<>("3",10));
                differenceChart.getData().add(new XYChart.Data<>("4",20));
                differenceChart.getData().add(new XYChart.Data<>("5",-3));
                differenceChart.getData().add(new XYChart.Data<>("6",10));
                differenceChart.getData().add(new XYChart.Data<>("7",20));
                differenceChart.getData().add(new XYChart.Data<>("8",13));
    }
    
    void loadEntryData(){
        entryChart.setName("Entrées");
        /*// resultSet va contenir les jours et les entree du mois en cours
    		while(resultSet.next()){
    			entryChart.getData.add(new XYChart.Data<Number, Number>(resultSet.getInt(),result.getDouble()));
    		}*/
                entryChart.getData().add(new XYChart.Data<>("0",2));
                entryChart.getData().add(new XYChart.Data<>("1",9));
                entryChart.getData().add(new XYChart.Data<>("2",11));
                entryChart.getData().add(new XYChart.Data<>("3",23));
                entryChart.getData().add(new XYChart.Data<>("4",20));
                entryChart.getData().add(new XYChart.Data<>("5",2));
                entryChart.getData().add(new XYChart.Data<>("6",25));
                entryChart.getData().add(new XYChart.Data<>("7",20));
                entryChart.getData().add(new XYChart.Data<>("8",20));
    }  
        
    public void loadExitData(){
        exitChart.setName("Sorties");
        /*// resultSet va contenir les jours et les sortie du mois en cours
    		while(resultSet.next()){
    			exitChart.getData.add(new XYChart.Data<Number, Number>(resultSet.getInt(),result.getDouble()));
    		}*/
                exitChart.getData().add(new XYChart.Data<>("0",2));
                exitChart.getData().add(new XYChart.Data<>("1",20));
                exitChart.getData().add(new XYChart.Data<>("2",21));
                exitChart.getData().add(new XYChart.Data<>("3",13));
                exitChart.getData().add(new XYChart.Data<>("4",0));
                exitChart.getData().add(new XYChart.Data<>("5",5));
                exitChart.getData().add(new XYChart.Data<>("6",15));
                exitChart.getData().add(new XYChart.Data<>("7",0));
                exitChart.getData().add(new XYChart.Data<>("8",7));
    }
    
    /************************************************************/
    
    /* ***************proportionsCategorie**************** */
    @FXML
    private JFXButton refreshProportionsCategorie;

    @FXML
    private PieChart proportionsCategorie;

    ObservableList<PieChart.Data> proportionsCategorieChartData;
    
    void loadProportionsCategorie(){
        proportionsCategorieChartData.addAll(new PieChart.Data("Agro-Alimentaire", 3000),
                                            new PieChart.Data("Cosmetique", 2000),
                                            new PieChart.Data("Electro-Menager", 1400),
                                            new PieChart.Data("Autre", 900));
    }
    
    void initProportionsCategorie(){
         proportionsCategorie.setTitle("Proportions des Categories");
         proportionsCategorieChartData = proportionsCategorie.getData();
                 
         loadProportionsCategorie();
    }
    
    @FXML
    void refreshProportionsCategorie(ActionEvent event) {
        proportionsCategorie.getData().clear();
        loadProportionsCategorie();
    }
    
    /********************************************************************/
    
    /* *********************** typeProduitGraph ******************** */
    @FXML
    private AreaChart<String, Number> typeProduitGraph;

    @FXML
    private JFXButton refreshTypeProduit;
    
    XYChart.Series<String,Number> typeProduitChart = new XYChart.Series<>();
    
    public void loadTypeProduitData(){
        /*// resultSet va contenir les jours et les  du mois en cours
    		while(resultSet.next()){
    			typeProduitChart.getData.add(new XYChart.Data<Number, Number>(resultSet.getInt(),result.getDouble()));
    		}*/
                typeProduitChart.getData().add(new XYChart.Data<>("Jan",25));
                typeProduitChart.getData().add(new XYChart.Data<>("Fev",30));
                typeProduitChart.getData().add(new XYChart.Data<>("Mar",30));
                typeProduitChart.getData().add(new XYChart.Data<>("Av",25));
                typeProduitChart.getData().add(new XYChart.Data<>("May",20));
                typeProduitChart.getData().add(new XYChart.Data<>("Jun",25));
                typeProduitChart.getData().add(new XYChart.Data<>("Jul",15));
                typeProduitChart.getData().add(new XYChart.Data<>("Au",20));
                typeProduitChart.getData().add(new XYChart.Data<>("Sep",17));
    }
    
    void initTypeProduit(){
        typeProduitGraph.getYAxis().setLabel("Types de Produit");
        typeProduitChart.setName("Types de Produit");

        loadTypeProduitData();
        typeProduitGraph.getData().add(typeProduitChart);
    }
    
    @FXML
    void refreshTypeProduit(ActionEvent event) {
        typeProduitChart.getData().clear();  
        loadTypeProduitData();
    }

    /********************************************************************/
    
    /*************** Sales Graph **********************/
    
    @FXML
    private Label date;

    @FXML
    private BarChart<String, Number> salesGraph;

    @FXML
    private JFXButton refreshSales;
    
    void initSales(){
        salesGraph.getYAxis().setLabel("Somme gagnee($ x 5000)");
        salesChart.setName("Ventes par Categorie");

        loadSalesData();
        salesGraph.getData().add(salesChart);
    }
    
    public void loadSalesData(){
        /*// resultSet va contenir les jours et les ventes du mois en cours
    		while(resultSet.next()){
    			salesChart.getData.add(new XYChart.Data<Number, Number>(resultSet.getInt(),result.getDouble()));
    		}*/
                
                salesChart.getData().add(new XYChart.Data<>("Pate Alimentaire",400));
                salesChart.getData().add(new XYChart.Data<>("Aperitifs",500));
                salesChart.getData().add(new XYChart.Data<>("Poisson",200));
                salesChart.getData().add(new XYChart.Data<>("electro-Menager",80));
                salesChart.getData().add(new XYChart.Data<>("Produits Laitiers",250));
                salesChart.getData().add(new XYChart.Data<>("Vetements",100));
                salesChart.getData().add(new XYChart.Data<>("Fruits et Legumes",750));
                salesChart.getData().add(new XYChart.Data<>("Autres",300));
                //salesChart.getData().add(new XYChart.Data<>("Stephen",7));
    }
    
    @FXML
    void refreshSales(ActionEvent event) {
        salesChart.getData().clear();
       loadSalesData();
    }
    
    
}
