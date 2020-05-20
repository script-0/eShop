/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class EditProduitsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Label lien;

    @FXML
    private Label lien1;

    @FXML
    private Label lien11;
    
    @FXML
    private Label title;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXButton valider;

    @FXML
    private JFXButton annuler;

    @FXML
    private JFXTextField code;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField quantite;

    @FXML
    private JFXTextField fournisseur;

    @FXML
    private JFXToggleButton actif;

    @FXML
    private JFXTextField prixA;

    @FXML
    private JFXTextField prixV;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXComboBox<String> categorie;

    @FXML
    void annuler() {
        close();
    }
    
    @FXML
    void valider() {
        Produits p = new Produits(code.getText(),
                                        nom.getText(),
                                        Double.valueOf(prixV.getText()), 
                                        Double.valueOf(prixA.getText()),
                                        Double.valueOf(quantite.getText()),
                                        description.getText(),
                                        fournisseur.getText(),
                                        date.getValue().toString(),
                                        (actif.isSelected()?"Enabled":"Disabled"),
                                        categorie.getValue());
        if(index == -1){
            sortedData.add(p);
        }else{
            sortedData.set(index,p);
        }
    }   
    
    public void loadCategories(){
        categorie.getItems().addAll("Agro-Alimentaire","Electro-Menager","Cosmetique","Autres");
    }
    
    public void setType(Boolean b){
        if(b){
            lien11.setText("Ajouter un Produit");
            title.setText("Ajouter un Produit");
        }
    }
    
    ObservableList<Produits> sortedData ;
    int index = -1;
    
    public void setItems(ObservableList<Produits> items){
        sortedData = items;
    }
    
    public void loadData(int i){
        index = i;
        Produits p = sortedData.get(i);
        code.setText(p.getCode());
        nom.setText(p.getName());
        quantite.setText(String.valueOf( p.getQte() ) );
        fournisseur.setText(p.getFournisseur());
        actif.setSelected(true);
        prixA.setText( String.valueOf( p.getPrixA() ) );
        prixV.setText( String.valueOf( p.getPrixV() ) );
        String[] tmp = p.getDate().split("-");
        date.setValue( LocalDate.of(Integer.valueOf(tmp[2]), Integer.valueOf(tmp[1]), Integer.valueOf(tmp[0])) );
        description.setText(p.getDescription());
        actif.setSelected(p.getEtat().equalsIgnoreCase("enabled"));
        categorie.setValue(p.getCategorie());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO;
        date.setValue(LocalDate.now());
        valider.setDisable(false);
        changeLocaleOfDate();
        loadCategories();
    }    
    
    void changeLocaleOfDate(){
        date.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
    }
    
    @FXML void close(){
        Stage tmp = (Stage)lien.getScene().getWindow();
        tmp.close();
    }
}
