/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier;

/**
 *
 * @author Isaac
 */
public class Produits {
    private String code;
    private String name;
    private double prixV;
    private double prixA;
    private double qte;    
    private String description;
    private String fournisseur;
    private String date;
    private String etat;
    private String Categorie;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrixV() {
        return prixV;
    }

    public void setPrixV(double prixV) {
        this.prixV = prixV;
    }

    public double getPrixA() {
        return prixA;
    }

    public void setPrixA(double prixA) {
        this.prixA = prixA;
    }

    public double getQte() {
        return qte;
    }

    public void setQte(double qte) {
        this.qte = qte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String Categorie) {
        this.Categorie = Categorie;
    }

    public Produits(String code, String name, double prixV, double prixA, double qte, String description, String fournisseur, String date, String etat, String Categorie) {
        this.code = code;
        this.name = name;
        this.prixV = prixV;
        this.prixA = prixA;
        this.qte = qte;
        this.description = description;
        this.fournisseur = fournisseur;
        this.date = date;
        this.etat = etat;
        this.Categorie = Categorie;
    }
    
    
}
