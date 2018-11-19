/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.Support;
import lappa.smsbanking.IDao.ISupport;


/**
 *
 * @author lappa
 */
public class ISupportImpl extends GenericDao<Support, Long> implements ISupport {

    @Override
    public Support findByIdC(Long id) throws DataAccessException {
        return (Support) getManager().createQuery("SELECT s FROM Support s WHERE s.id = :id")
                .setParameter("id", id).getSingleResult();
    }
    
}