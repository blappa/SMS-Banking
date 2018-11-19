/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.Sms;
import lappa.smsbanking.IDao.ISms;


/**
 *
 * @author lappa
 */
public class ISmsImpl extends GenericDao<Sms, Long> implements ISms {

    @Override
    public Sms findByIdC(Long id) throws DataAccessException {
        return (Sms) getManager().createQuery("SELECT s FROM Sms s WHERE s.id = :id")
                .setParameter("id", id).getSingleResult();
    }
    
}