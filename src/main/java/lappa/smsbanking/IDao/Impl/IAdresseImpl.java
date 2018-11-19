/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.Entities.Adresse;
import lappa.smsbanking.IDao.IAdresse;

/**
 *
 * @author lappa
 */
public class IAdresseImpl extends GenericDao<Adresse, Long> implements IAdresse {

    @Override
    public Adresse findByIdA(Long id) throws DataAccessException {
        return (Adresse) getManager().createQuery("SELECT c FROM Adresse c WHERE c.id = :id")
                .setParameter("id", id).getSingleResult();
    }
    
    
    @Override
    public Adresse findByTypeAdresse(String typeAdresse) throws DataAccessException {
        return (Adresse) getManager().createQuery("SELECT c FROM Adresse c WHERE c.typeAdresse = :typeAdresse")
                .setParameter("typeAdresse", typeAdresse).getSingleResult();
    }
}
