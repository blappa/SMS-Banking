/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.Entities.PersonnePhysique;
import lappa.smsbanking.IDao.ICompte;
import lappa.smsbanking.IDao.IOperation;
import lappa.smsbanking.IDao.IPersonne;
import lappa.smsbanking.IDao.IPersonneMorale;
import lappa.smsbanking.IDao.IPersonnePhys;
import lappa.smsbanking.IDao.ISmsCompte;
import IServiceSupport.IServicePersonnePhys;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServicePersonnePhysImpl implements IServicePersonnePhys {

    private IPersonnePhys iPersonnePhys;
    private IPersonne iPersonne;
    private ICompte iCompte;
    private IOperation iOperation;
    private ISmsCompte iSmsCompte;

    @Override
    public void save(PersonnePhysique u, Long idp) throws DataAccessException {
        Personne p = iPersonne.findByIdP(idp);
        u.setIdPersonne(p);
        iPersonnePhys.create(u);
    }

    @Override
    public void delete(Long id) throws DataAccessException {
        PersonnePhysique a = iPersonnePhys.findByIdP(id);
        iPersonnePhys.delete(a);
    }

    @Override
    public void update(PersonnePhysique u) throws DataAccessException {
        iPersonnePhys.update(u);
    }

    @Override
    public List<PersonnePhysique> findAll() throws DataAccessException {
        return iPersonnePhys.findAll();
    }

    @Override
    public PersonnePhysique findById(Long id) throws DataAccessException {
        return iPersonnePhys.findByIdP(id);
    }

   
    @Override
    public PersonnePhysique findByIdP(Long id) throws DataAccessException {
      return iPersonnePhys.findByIdP(id); 
    }
    
    @Override
    public PersonnePhysique findByNom(String nom) throws DataAccessException {
      return iPersonnePhys.findByNom(nom);
    }

    @Override
    public PersonnePhysique findByCni(String cni) throws DataAccessException {
       return iPersonnePhys.findByCni(cni);
    }


    public IPersonne getiPersonne() {
        return iPersonne;
    }

    public void setiPersonne(IPersonne iPersonne) {
        this.iPersonne = iPersonne;
    }

    public ICompte getiCompte() {
        return iCompte;
    }

    public void setiCompte(ICompte iCompte) {
        this.iCompte = iCompte;
    }

    public IOperation getiOperation() {
        return iOperation;
    }

    public void setiOperation(IOperation iOperation) {
        this.iOperation = iOperation;
    }

    public ISmsCompte getiSmsCompte() {
        return iSmsCompte;
    }

    public void setiSmsCompte(ISmsCompte iSmsCompte) {
        this.iSmsCompte = iSmsCompte;
    }

    public IPersonnePhys getiPersonnePhys() {
        return iPersonnePhys;
    }

    public void setiPersonnePhys(IPersonnePhys iPersonnePhys) {
        this.iPersonnePhys = iPersonnePhys;
    }
    
}
