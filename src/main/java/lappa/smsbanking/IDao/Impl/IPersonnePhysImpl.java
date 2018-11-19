/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.PersonnePhysique;
import lappa.smsbanking.IDao.IPersonnePhys;

/**
 *
 * @author lappa
 */
public class IPersonnePhysImpl extends GenericDao<PersonnePhysique, Long> implements IPersonnePhys{
    
    @Override
    public PersonnePhysique findByNom(String nom) throws DataAccessException {
        return (PersonnePhysique)getManager().createQuery("SELECT u FROM PersonnePhysique u WHERE u.nom = :nom")
         .setParameter("nom", nom).getSingleResult();  
    }

    @Override
    public PersonnePhysique findByCni(String cni) throws DataAccessException {
       return (PersonnePhysique)getManager().createQuery("SELECT u FROM PersonnePhysique u WHERE u.cni = :cni")
         .setParameter("cni", cni).getSingleResult();
    }

    @Override
    public PersonnePhysique findByIdP(Long id) throws DataAccessException {
        return (PersonnePhysique)getManager().createQuery("SELECT u FROM PersonnePhysique u WHERE u.id = :id")
         .setParameter("id", id).getSingleResult();
    }

    
}
