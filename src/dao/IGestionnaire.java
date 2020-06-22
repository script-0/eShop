package dao;

import entity.Gestionnaire;

import java.util.List;

public interface IGestionnaire {

    public void loadOnBD(Gestionnaire gestionnaire);
    public void deleteFromBD(List<Gestionnaire> gestionnaires);
    public List<Gestionnaire> readFromBD();
}
