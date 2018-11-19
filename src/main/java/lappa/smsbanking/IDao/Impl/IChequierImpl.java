/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.Chequier;
import lappa.smsbanking.IDao.IChequier;

/**
 *
 * @author lappa
 */
public class IChequierImpl extends GenericDao<Chequier, Long> implements IChequier {

    @Override
    public Chequier findByIdC(Long id) throws DataAccessException {
        return (Chequier) getManager().createQuery("SELECT c FROM Chequier c WHERE c.id = :id")
                .setParameter("id", id).getSingleResult();
    }
    
    
    @Override
    public Chequier findByNumReference(String numReference) throws DataAccessException {
        return (Chequier) getManager().createQuery("SELECT c FROM Chequier c WHERE c.numReference = :numReference")
                .setParameter("numReference", numReference).getSingleResult();
    }
}
