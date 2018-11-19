/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.IDao.IPersonne;

/**
 *
 * @author lappa
 */
public class IPersonneImpl extends GenericDao<Personne, Long> implements IPersonne{
    
    @Override
    public Personne findByNom(String nom) throws DataAccessException {
        return (Personne)getManager().createQuery("SELECT u FROM Personne u WHERE u.nomClient = :nom")
         .setParameter("nom", nom).getSingleResult();  
    }

    @Override
    public Personne findByCode(String code) throws DataAccessException {
       return (Personne)getManager().createQuery("SELECT u FROM Personne u WHERE u.codeProduit = :code")
         .setParameter("code", code).getSingleResult();
    }

    @Override
    public Personne findByIdP(Long id) throws DataAccessException {
        return (Personne)getManager().createQuery("SELECT u FROM Personne u WHERE u.id = :id")
         .setParameter("id", id).getSingleResult();
    }
    
    @Override
    public Personne findByIdClient(String id) throws DataAccessException {
        return (Personne)getManager().createQuery("SELECT u FROM Personne u WHERE u.idClient = :id")
         .setParameter("id", id).getSingleResult();
    }
    
      @Override
     public Personne findByNumeroCompte(String numeroCompte) throws DataAccessException {
        return (Personne) getManager().createQuery("SELECT c FROM Personne c WHERE c.numeroCompte = :numeroCompte")
                .setParameter("numeroCompte", numeroCompte).getSingleResult();
    }
}
