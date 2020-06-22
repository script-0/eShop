/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.categorie;

import Utils.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.LoginController;
import magasinier.produit.EditProduitsController;
import magasinier.produit.ProduitsController;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class ViewCategoriesController implements Initializable {
    
   @FXML
    private ImageView img;

    @FXML
    private FontAwesomeIconView editImg;

    @FXML
    private Label nom;

    @FXML
    private Label code;

    @FXML
    private Pagination pagination;

    @FXML
    public VBox listProduits;

    @FXML
    private JFXButton valider;

    @FXML
    private JFXButton annuler;

    @FXML
    private JFXButton add;
    
    Categories data;

    public void loadData(Categories c){
        data = c;
        nom.setText(data.getName());
        code.setText(data.getCode());
        initPagination();
    }
    
    public void initPagination(){
        int tmp =  (int)Math.ceil(data.getProduits().size()/4.0);
        
        pagination.setPageCount(tmp);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(tmp);
        
        listProduits.getChildren().clear();

        int f = data.getProduits().size();
        if(f >= 4 ) f = 4;

        for(int i=0;i<f;i++){
            listProduits.getChildren().add(loadElement(i));
        }
        pagination.setPageFactory((param) -> {
            listProduits.getChildren().clear();

            int fin =data.getProduits().size();
            if(fin >=4*param+4) fin = 4*param+4;

            for(int i=4*param;i<fin;i++){
               listProduits.getChildren().add(loadElement(i));
            }
            return this.listProduits;
        });            
    }
    
    public HBox loadElement(int index){
        try { 
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/categorie/elements.fxml"));
             HBox p = loader.load();
             ElementsController controller = loader.getController();  
             controller.loadData(data.getProduits(),index,this);
             return p;
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
        return null;
    }
    
    
    @FXML
    void add() {
        
    }

    @FXML
    void annuler() {
       Stage tmp = (Stage)code.getScene().getWindow();
       tmp.close();
    }

    @FXML
    void editImg() {
        
    }

    @FXML
    void valider() {
        annuler();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    
}
