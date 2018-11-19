/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Agence;
import lappa.smsbanking.IDao.IAgence;
import lappa.smsbanking.IDao.IPersonne;
import IServiceSupport.IServiceAgence;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceAgenceImpl implements IServiceAgence {
    
    private IAgence iAgence;

    @Override
    public Agence findByIdA(Long id) throws DataAccessException {
       return iAgence.findByIdA(id);
    }

    @Override
    public Agence findByNomAgence(String nomAgence) throws DataAccessException {
       return iAgence.findByNomAgence(nomAgence);
    }

    @Override
    public Agence findByCodeAgence(String codeAgence) throws DataAccessException {
      return iAgence.findByCodeAgence(codeAgence);
    }

    @Override
    public void save(Agence c) throws DataAccessException {
        iAgence.create(c);
    }

    @Override
    public void delete(Long id) throws DataAccessException {
        Agence a=iAgence.findById(id);
        iAgence.delete(a);
    }

    @Override
    public void update(Agence c) throws DataAccessException {
        iAgence.update(c);
    }

    @Override
    public List<Agence> findAll() throws DataAccessException {
      return iAgence.findAll();
    }

    @Override
    public Agence findById(Long id) throws DataAccessException {
        return iAgence.findById(id);
                }

    public IAgence getiAgence() {
        return iAgence;
    }

    public void setiAgence(IAgence iAgence) {
        this.iAgence = iAgence;
    }

    
}
