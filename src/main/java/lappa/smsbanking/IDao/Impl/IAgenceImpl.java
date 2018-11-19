/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.IDao.IAgence;

/**
 *
 * @author lappa
 */
public class IAgenceImpl extends GenericDao<Agence, Long> implements IAgence {

    @Override
    public Agence findByIdA(Long id) throws DataAccessException {
        return (Agence) getManager().createQuery("SELECT c FROM Agence c WHERE c.id = :id")
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public Agence findByCodeAgence(String codeAgence) throws DataAccessException {
        return (Agence) getManager().createQuery("SELECT c FROM Agence c WHERE c.codeAgence = :codeAgence")
                .setParameter("codeAgence", codeAgence).getSingleResult();
    }

    @Override
    public Agence findByNomAgence(String nomAgence) throws DataAccessException {
      return (Agence) getManager().createQuery("SELECT c FROM Agence c WHERE c.nomAgence = :nomAgence")
                .setParameter("nomAgence", nomAgence).getSingleResult();
    }
}
