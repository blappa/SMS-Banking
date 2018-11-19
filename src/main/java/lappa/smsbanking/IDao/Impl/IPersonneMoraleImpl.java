/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.IDao.IPersonneMorale;

/**
 *
 * @author lappa
 */
public class IPersonneMoraleImpl extends GenericDao<PersonneMorale, Long> implements IPersonneMorale{
    
    
    @Override
    public PersonneMorale findByIdP(Long id) throws DataAccessException {
        return (PersonneMorale)getManager().createQuery("SELECT u FROM PersonneMorale u WHERE u.id = :id")
         .setParameter("id", id).getSingleResult();
    }

    @Override
    public PersonneMorale findByNomEntreprise(String nomEntreprise) throws DataAccessException {
        return (PersonneMorale)getManager().createQuery("SELECT u FROM PersonneMorale u WHERE u.nomEntreprise = :nomEntreprise")
         .setParameter("nomEntreprise", nomEntreprise).getSingleResult();   
    }

    @Override
    public PersonneMorale findBySecteurActivite(String secteurActivite) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonneMorale findByNumEnregistrement(String numEnregistrement) throws DataAccessException {
       return (PersonneMorale)getManager().createQuery("SELECT u FROM PersonneMorale u WHERE u.numEnregistrement = :numEnregistrement")
         .setParameter("numEnregistrement", numEnregistrement).getSingleResult();   
    }

    
}
