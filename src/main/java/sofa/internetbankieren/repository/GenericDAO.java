package sofa.internetbankieren.repository;

import sofa.internetbankieren.model.Medewerker;

import java.util.List;

/**
 * @author Wendy Ellens
 *
 * Alle DAO's implementeren onderstaande methodes
 */
public interface GenericDAO<T> {
    List<T> getAll();
    T getOneByID(int id);
    void storeOne(T type);
    int updateOne(T type);
    int deleteOne(T type);
}