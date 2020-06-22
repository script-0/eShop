/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 *
 * @author Isaac
 */
public class CGUController implements Initializable{    
    @FXML
    private FontAwesomeIconView close;

    @FXML
    public void close() {
        Stage tmp = (Stage)close.getScene().getWindow();
        tmp.close();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
