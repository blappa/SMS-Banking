/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.SmsCompte;
import lappa.smsbanking.IDao.ISmsCompte;

/**
 *
 * @author lappa
 */
public class ISmsCompteImpl extends GenericDao<SmsCompte, Long> implements ISmsCompte {

    @Override
    public SmsCompte findByIdS(Long id) throws DataAccessException {
        return (SmsCompte) getManager().createQuery("SELECT s FROM SmsCompte s WHERE s.id = :id")
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public SmsCompte findByMobile(String mobile) throws DataAccessException {
        return (SmsCompte) getManager().createQuery("SELECT s FROM SmsCompte s WHERE s.mobile = :mobile")
                .setParameter("mobile", mobile).getSingleResult();
    }

    @Override
    public SmsCompte findByTypeCompte(String typeCompte) throws DataAccessException {
        return (SmsCompte) getManager().createQuery("SELECT s FROM SmsCompte s WHERE s.typeCompte = :typeCompte")
                .setParameter("typeCompte", typeCompte).getSingleResult();
    }

    @Override
    public SmsCompte findByIdClient(String idClient) throws DataAccessException {
        return (SmsCompte) getManager().createQuery("SELECT s FROM SmsCompte s WHERE s.idClient = :idClient")
                .setParameter("idClient", idClient).getSingleResult();
    }

    public SmsCompte findByPin(int pin) throws DataAccessException {
        return (SmsCompte) getManager().createQuery("SELECT s FROM SmsCompte s WHERE s.pin = :pin")
                .setParameter("pin", pin).getSingleResult();
    }
}