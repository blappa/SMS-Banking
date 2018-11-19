/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.CompteParent;
import lappa.smsbanking.IDao.ICompteParent;

/**
 *
 * @author lappa
 */
public class ICompteParentImpl extends GenericDao<CompteParent, Long> implements ICompteParent {

    @Override
    public CompteParent findById(Long id) throws DataAccessException {
        return (CompteParent) getManager().createQuery("SELECT s FROM CompteParent s WHERE s.id = :id")
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public CompteParent findByIdClient(String idClient) throws DataAccessException {
        return (CompteParent) getManager().createQuery("SELECT s FROM CompteParent s WHERE s.idClient = :idClient")
                .setParameter("idClient", idClient).getSingleResult();
    }

}