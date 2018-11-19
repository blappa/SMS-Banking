/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.IDao.ICompte;

/**
 *
 * @author lappa
 */
public class ICompteImpl extends GenericDao<Compte, Long> implements ICompte {

    @Override
    public Compte findByIdC(Long id) throws DataAccessException {
        return (Compte) getManager().createQuery("SELECT c FROM Compte c WHERE c.id = :id")
                .setParameter("id", id).getSingleResult();
    }
    
    
    @Override
     public Compte findByNumeroCompte(String numeroCompte) throws DataAccessException {
//        return (Compte) getManager().createQuery("SELECT c FROM Compte c WHERE c.numeroCompte = :numeroCompte")
//                .setParameter("numeroCompte", numeroCompte).getSingleResult();
        return null;
    }
}
