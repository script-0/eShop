/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

/**
 *
 * @author Stephen
 */
public class GestionStock {
    private String codeStock;
    private int qte;
    private String date;
    private String operation;
    private String employe;
    private String produit;

    public GestionStock(String codeStock, int qte, String date, String operation, String employe, String produit) {
        this.codeStock = codeStock;
        this.qte = qte;
        this.date = date;
        this.operation = operation;
        this.employe = employe;
        this.produit = produit;
    }

    public String getCodeStock() {
        return codeStock;
    }

    public void setCodeStock(String codeStock) {
        this.codeStock = codeStock;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getGestionnaire() {
        return employe;
    }

    public void setGestionnaire(String employe) {
        this.employe = employe;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }
    
}
