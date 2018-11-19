/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.OffreSpeciale;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.IDao.IOffreSpeciale;
import lappa.smsbanking.IDao.IPersonne;
import IServiceSupport.IServiceOffreSpeciale;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceOffreSpecialeImpl implements IServiceOffreSpeciale {

    private IOffreSpeciale iOffreSpeciale;
    private IPersonne iPersonne;

    @Override
    public OffreSpeciale findByIdO(Long id) throws DataAccessException {
        return iOffreSpeciale.findByIdO(id);
    }

    @Override
    public void save(OffreSpeciale o, String idu) throws DataAccessException {
        Personne p = iPersonne.findByIdClient(idu);
        o.setIdPersonne(p);
        iOffreSpeciale.create(o);
    }

    @Override
    public void delete(Long id) throws DataAccessException {
      OffreSpeciale o=iOffreSpeciale.findByIdO(id);
      iOffreSpeciale.delete(o);
    }

    @Override
    public void update(OffreSpeciale c) throws DataAccessException {
        iOffreSpeciale.delete(c);
    }

    @Override
    public List<OffreSpeciale> findAll() throws DataAccessException {
     return iOffreSpeciale.findAll();
    }

    @Override
    public OffreSpeciale findById(Long id) throws DataAccessException {
       return iOffreSpeciale.findById(id);
    }

    public IOffreSpeciale getiOffreSpeciale() {
        return iOffreSpeciale;
    }

    public void setiOffreSpeciale(IOffreSpeciale iOffreSpeciale) {
        this.iOffreSpeciale = iOffreSpeciale;
    }

    public IPersonne getiPersonne() {
        return iPersonne;
    }

    public void setiPersonne(IPersonne iPersonne) {
        this.iPersonne = iPersonne;
    }
}
