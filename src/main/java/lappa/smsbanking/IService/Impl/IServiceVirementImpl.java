/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.Entities.Virement;
import lappa.smsbanking.IDao.ICompte;
import lappa.smsbanking.IDao.IVirement;
import IServiceSupport.IServiceVirement;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceVirementImpl implements IServiceVirement {
    
    private IVirement iVirement;
    private ICompte iCompte;
    
    @Override
    public void save(Virement u, Long idC) throws DataAccessException {
        Compte c=iCompte.findByIdC(idC);
        u.setIdCompte(c);
        iVirement.create(u);
    }
    
    @Override
    public void save(Virement u) throws DataAccessException {
        iVirement.create(u);
    }
    
    @Override
    public void delete(Long id) throws DataAccessException {
        Virement p = iVirement.findByIdV(id);
        iVirement.delete(p);
    }
    
    @Override
    public void update(Virement u) throws DataAccessException {
        iVirement.update(u);
    }
    
    @Override
    public List<Virement> findAll() throws DataAccessException {
        return iVirement.findAll();
    }

    @Override
    public Virement findByNumCompte(String num) throws DataAccessException {
       return iVirement.findByNumCompte(num);
    }

    @Override
    public Virement findByCodeDocument(String code) throws DataAccessException {
        return iVirement.findByCodeDocument(code);
    }

    @Override
    public Virement findByIdV(Long id) throws DataAccessException {
       return iVirement.findById(id); 
    }

    @Override
    public Virement findByCodeFichier(String codeF) throws DataAccessException {
        return iVirement.findByCodeFichier(codeF);
    }
    
    @Override
    public Virement findByTypeDocument(String type) throws DataAccessException {
       return iVirement.findByTypeDocument(type);
    }

    public IVirement getiVirement() {
        return iVirement;
    }

    public void setiVirement(IVirement iVirement) {
        this.iVirement = iVirement;
    }

    public ICompte getiCompte() {
        return iCompte;
    }

    public void setiCompte(ICompte iCompte) {
        this.iCompte = iCompte;
    }

    
    
    
}
