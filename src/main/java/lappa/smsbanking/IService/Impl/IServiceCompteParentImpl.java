/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.CompteParent;
import lappa.smsbanking.IDao.ISmsCompte;
import lappa.smsbanking.IDao.ICompteParent;
import lappa.smsbanking.IService.IServiceCompteParent;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceCompteParentImpl implements IServiceCompteParent {

    private ISmsCompte iSmsCompte;
    private ICompteParent iCompteParent;

    public void save(CompteParent s) throws DataAccessException {
        iCompteParent.create(s);
    }

    public void delete(Long id) throws DataAccessException {
        CompteParent c = iCompteParent.findById(id);
        iCompteParent.delete(c);
    }

    public void update(CompteParent s) throws DataAccessException {
        iCompteParent.update(s);
    }

    public List<CompteParent> findAll() throws DataAccessException {
        return iCompteParent.findAll();
    }

    public CompteParent findById(Long id) throws DataAccessException {
        return iCompteParent.findById(id);
    }

    public ISmsCompte getiSmsCompte() {
        return iSmsCompte;
    }

    public void setiSmsCompte(ISmsCompte iSmsCompte) {
        this.iSmsCompte = iSmsCompte;
    }

    public ICompteParent getiCompteParent() {
        return iCompteParent;
    }

    public void setiCompteParent(ICompteParent iCompteParent) {
        this.iCompteParent = iCompteParent;
    }
    
    @Override
    public CompteParent findByIdClient(String idClient) throws DataAccessException {
      return iCompteParent.findByIdClient(idClient);
    }
    
}
