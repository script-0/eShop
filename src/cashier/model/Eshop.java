/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Shaquillo
 */
public class Eshop extends Application {
    public static Stage primaryStage = null;
    public static ResourceBundle bundle = ResourceBundle.getBundle("language/Bundle",I18N.getDefaultLocale());
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/pages/dailyBill.fxml"), bundle);
        
        Scene scene = new Scene(root);
        
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(650);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
