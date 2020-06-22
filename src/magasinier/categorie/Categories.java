/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.categorie;

import java.util.ArrayList;
import magasinier.produit.Produits;

/**
 *
 * @author Isaac
 */
public class Categories {
    private String name;
    private String code;
    private ArrayList<Produits> produits;

    public Categories(String name, String code, ArrayList<Produits> produits) {
        this.name = name;
        this.code = code;
        this.produits = produits;
    }

    public ArrayList<Produits> getProduits() {
        return produits;
    }

    public void setProduits(ArrayList<Produits> produits) {
        this.produits = produits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
}
