/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.Emploi;
import lappa.smsbanking.IDao.IEmploi;

/**
 *
 * @author lappa
 */
public class IEmploiImpl extends GenericDao<Emploi, Long> implements IEmploi {

    @Override
    public Emploi findByIdE(Long id) throws DataAccessException {
       return (Emploi) getManager().createQuery("SELECT c FROM Emploi c WHERE c.id = :id")
                .setParameter("id", id).getSingleResult();   
    }

    @Override
    public Emploi findByTypeEmploi(String typeEmploi) throws DataAccessException {
      return (Emploi) getManager().createQuery("SELECT c FROM Emploi c WHERE c.typeEmploi = :typeEmploi")
                .setParameter("typeEmploi", typeEmploi).getSingleResult();   
    }

    @Override
    public Emploi findByProfession(String profession) throws DataAccessException {
       return (Emploi) getManager().createQuery("SELECT c FROM Emploi c WHERE c.profession = :profession")
                .setParameter("profession", profession).getSingleResult();
    }
}
