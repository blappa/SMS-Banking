/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.OffreSpeciale;
import lappa.smsbanking.IDao.IOffreSpeciale;

/**
 *
 * @author lappa
 */
public class IOffreSpecialeImpl extends GenericDao<OffreSpeciale, Long> implements IOffreSpeciale {

    @Override
    public OffreSpeciale findByIdO(Long id) throws DataAccessException {
        return (OffreSpeciale) getManager().createQuery("SELECT c FROM OffreSpeciale c WHERE c.id = :id")
                .setParameter("id", id).getSingleResult();
    }
    
}
