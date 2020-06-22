/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.EntitiesImpl;

import SingletonConnection.SingletonConnection;
import dao.IProduct;
import entity.Categorie;
import entity.Produit;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author TontonLaForce
 */
public class ProductImpl implements IProduct {
    EntityManager em = SingletonConnection.getConnection();
    
    @Override
    public List<Produit> readProduct() {
        return em.createQuery("select p from Produit p").getResultList();
    }

    @Override
    public List<String> findProductName() {
        return em.createQuery("select p.nomPro from Produit p").getResultList();
    }

    @Override
    public List<String> findProductCode() {
        return em.createQuery("select p.codePro from Produit p").getResultList();
    }

    @Override
    public boolean save(Produit produit) {

        List<Produit> produits = em.createQuery("select p from Produit p where p.codePro=:code or p.nomPro=:nom").setParameter("code" , produit.getCodePro()).setParameter("nom" , produit.getNomPro()).getResultList();
        if( !produits.isEmpty() ) return false ; // le produit existe dans la base de données
        em.persist(produit);
        return true; // le produit a ete ajoutee dans la base de donnee
    }

    @Override
    public boolean update(Produit produit) {
        List<Produit> produits = em.createQuery("select p from Produit p where p.nomPro=:nom").setParameter("nom", produit.getNomPro()).getResultList();
        if( !produits.isEmpty() ) return  false ;// le produit n'existe pas dans la BD ou le nouveau produit est a une propriete similaire a un un produit de la bd
        em.merge(produit);
        return true;
    }

    @Override
    public List<Produit> findProductByCategory(Categorie categorie) {
        return (List<Produit>) em.createQuery("select p from Produit p where p.categorieIdcat=:param").setParameter("param" , categorie.getIdCat());
    }

    @Override
    public List<Produit> readProductperCategorie(long categoryCode) {
        List<Produit> productPerCategory = null ;
        try{
            Query query = em.createQuery("select p from Produit p where p.categorieIdcat=:categoryCode");
            query.setParameter("categoryCode" , (int)categoryCode );
            productPerCategory = (List<Produit>)query.getResultList();
        }catch(IllegalStateException e){
            SingletonConnection.getEntityTransaction().rollback();
        }
        return productPerCategory ;
    }
    
}
