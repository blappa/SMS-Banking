/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.Virement;
import lappa.smsbanking.IDao.IVirement;

/**
 *
 * @author lappa
 */
public class IVirementImpl extends GenericDao<Virement, Long> implements IVirement{
    

    @Override
    public Virement findByNumCompte(String num) throws DataAccessException {
       return (Virement)getManager().createQuery("SELECT u FROM Virement u WHERE u.numCompte = :num")
         .setParameter("num", num).getSingleResult();  
    }

    @Override
    public Virement findByCodeDocument(String code) throws DataAccessException {
       return (Virement)getManager().createQuery("SELECT u FROM Virement u WHERE u.codeDocument = :code")
         .setParameter("code", code).getSingleResult();  
    }

    @Override
    public Virement findByIdV(Long id) throws DataAccessException {
       return (Virement)getManager().createQuery("SELECT u FROM Virement u WHERE u.id = :id")
         .setParameter("id", id).getSingleResult();  
    }

    @Override
    public Virement findByTypeDocument(String type) throws DataAccessException {
        return (Virement)getManager().createQuery("SELECT u FROM Virement u WHERE u.typeDocument = :type")
         .setParameter("type", type).getSingleResult();  
    }

    @Override
     public Virement findByCodeFichier(String codeF) throws DataAccessException{
         return (Virement)getManager().createQuery("SELECT u FROM Virement u WHERE u.codeFichier = :codeF")
         .setParameter("codeF", codeF).getSingleResult(); 
     }
   
}
