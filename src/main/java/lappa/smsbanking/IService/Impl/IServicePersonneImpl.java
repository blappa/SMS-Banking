/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.IDao.IPersonne;
import IServiceSupport.IServicePersonne;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServicePersonneImpl implements IServicePersonne {
    
    private IPersonne iPersonne;
    
    @Override
    public void save(Personne u) throws DataAccessException {
        iPersonne.create(u);
    }
    
    @Override
    public void delete(Long id) throws DataAccessException {
        Personne p = iPersonne.findByIdP(id);
        iPersonne.delete(p);
    }
    
    @Override
    public void update(Personne u) throws DataAccessException {
        iPersonne.update(u);
    }
    
    @Override
    public List<Personne> findAll() throws DataAccessException {
        return iPersonne.findAll();
    }
    
    @Override
    public Personne findById(Long id) throws DataAccessException {
        return iPersonne.findByIdP(id);
    }
    
    @Override
    public Personne findByNom(String nom) throws DataAccessException {
        return iPersonne.findByNom(nom);
    }
    
    @Override
    public Personne findByPays(String pays) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Personne findByVille(String ville) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Personne findByCode(String code) throws DataAccessException {
        return iPersonne.findByCode(code);
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

    @Override
    public Personne findByNumeroCompte(String numeroCompte) throws DataAccessException {
       return iPersonne.findByNumeroCompte(numeroCompte);
    }
}
