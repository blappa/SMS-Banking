/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Chequier;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.IDao.IChequier;
import lappa.smsbanking.IDao.ICompte;
import IServiceSupport.IServiceChequier;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceChequierImpl implements IServiceChequier{
    
    private IChequier iChequier;
    private ICompte iCompte;
    
    @Override
    public Chequier findByIdC(Long id) throws DataAccessException {
        return iChequier.findByIdC(id);
    }
    
    @Override
    public Chequier findByNumReference(String numReferencee) throws DataAccessException {
        return iChequier.findByNumReference(numReferencee);
    }
    
    @Override
    public void save(Chequier c, Long idC) throws DataAccessException {
        Compte com = iCompte.findById(idC);
        c.setIdCompte(com);
        iChequier.create(c);
    }
    
    @Override
    public void delete(Long id) throws DataAccessException {
        Chequier c=iChequier.findByIdC(id);
       iChequier.delete(c);
    }
    
    @Override
    public void update(Chequier c) throws DataAccessException {
    iChequier.update(c);
    }
    
    @Override
    public List<Chequier> findAll() throws DataAccessException {
        return iChequier.findAll();
    }
    
    @Override
    public Chequier findById(Long id) throws DataAccessException {
      return iChequier.findByIdC(id);
    }

    public IChequier getiChequier() {
        return iChequier;
    }

    public void setiChequier(IChequier iChequier) {
        this.iChequier = iChequier;
    }

    public ICompte getiCompte() {
        return iCompte;
    }

    public void setiCompte(ICompte iCompte) {
        this.iCompte = iCompte;
    } 

    
}
