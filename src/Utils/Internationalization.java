/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.jfoenix.controls.JFXComboBox;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Isaac
 */
public final class Internationalization {
    static final Locale EN = new Locale("en");
    
    static final Locale FR = new Locale("fr");  
    
    static String lang = "En";
    
    public static ResourceBundle initLanguage(JFXComboBox<String> langue){
        langue.getItems().addAll("Francais","English");
        langue.setValue(lang.equals("En")?"English":"Francais");
       return getBundle(lang);
    }
    
    public static Locale getCurrentLocale(){
        return lang.equalsIgnoreCase("En")?EN:FR;
    }
    
    public static Locale getLocale(){
       lang=lang.equalsIgnoreCase("En")?"Fr":"En";
       return getCurrentLocale();
    }
    
    public static ResourceBundle getBundle(String language){
        if(language.toLowerCase().startsWith(lang.toLowerCase()))
            return ResourceBundle.getBundle("resources/languages/bundle", getCurrentLocale());
        else
            return ResourceBundle.getBundle("resources/languages/bundle", getLocale());
    }
}
