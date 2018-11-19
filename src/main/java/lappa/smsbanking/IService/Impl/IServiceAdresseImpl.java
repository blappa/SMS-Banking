/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Adresse;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.IDao.IAdresse;
import lappa.smsbanking.IDao.IPersonne;
import IServiceSupport.IServiceAdresse;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceAdresseImpl implements IServiceAdresse {
    
    private IAdresse iAdresse;
    private IPersonne iPersonne;
    
    @Override
    public Adresse findByIdA(Long id) throws DataAccessException {
        return iAdresse.findByIdA(id);
    }
    
    @Override
    public Adresse findByTypeAdresse(String typeAdresse) throws DataAccessException {
        return iAdresse.findByTypeAdresse(typeAdresse);
    }
    
    @Override
    public void save(Adresse c, Long idu) throws DataAccessException {
        Personne p = iPersonne.findByIdP(idu);
        c.setIdPersonne(p);
        iAdresse.create(c);
    }
    
    @Override
    public void delete(Long id) throws DataAccessException {
        Adresse a=iAdresse.findByIdA(id);
       iAdresse.delete(a);
    }
    
    @Override
    public void update(Adresse c) throws DataAccessException {
    iAdresse.update(c);
    }
    
    @Override
    public List<Adresse> findAll() throws DataAccessException {
        return iAdresse.findAll();
    }
    
    @Override
    public Adresse findById(Long id) throws DataAccessException {
      return iAdresse.findByIdA(id);
    }
    
    @Override
    public Personne findByIdClient(String idClient) throws DataAccessException {
     return iPersonne.findByIdClient(idClient);
    }
    
    public IPersonne getiPersonne() {
        return iPersonne;
    }
    
    public void setiPersonne(IPersonne iPersonne) {
        this.iPersonne = iPersonne;
    }
    
    public IAdresse getiAdresse() {
        return iAdresse;
    }
    
    public void setiAdresse(IAdresse iAdresse) {
        this.iAdresse = iAdresse;
    }

    
}
