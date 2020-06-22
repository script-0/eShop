package dao.EntitiesImpl;

import SingletonConnection.SingletonConnection;
import dao.IGestionnaire;
import entity.Gestionnaire;

import javax.persistence.EntityManager;
import java.util.List;

public class GestionnaireImpl implements IGestionnaire {
    EntityManager em = SingletonConnection.getConnection();
    @Override
    public void loadOnBD(Gestionnaire gestionnaire) {
        List<Gestionnaire> gestionnaires = (List<Gestionnaire>) em.createQuery("select g from Gestionnaire g where g.nomGest=:nom or g.email=:email or g.contact=:contact or g.login=:login").setParameter("nom", gestionnaire.getNom()).setParameter("email" ,gestionnaire.getEmail()).setParameter("contact" ,gestionnaire.getContact()).setParameter("login" , gestionnaire.getLogin());
        if( gestionnaires.isEmpty() && em.find(Gestionnaire.class , gestionnaire.getIdGest())!=null ) em.merge(gestionnaire);
    }

    @Override
    public void deleteFromBD(List<Gestionnaire> gestionnaires) {
        for (Gestionnaire gestionnaire : gestionnaires){
            if( em.find(Gestionnaire.class , gestionnaire.getIdGest())!=null )
                em.remove(gestionnaire);
        }
    }
    

    @Override
    public List<Gestionnaire> readFromBD() {
        return em.createQuery("select g from Gestionnaire g").getResultList();
    }
}
