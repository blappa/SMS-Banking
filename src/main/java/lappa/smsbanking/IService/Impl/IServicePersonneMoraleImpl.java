/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.IDao.ICompte;
import lappa.smsbanking.IDao.IOperation;
import lappa.smsbanking.IDao.IPersonne;
import lappa.smsbanking.IDao.IPersonneMorale;
import lappa.smsbanking.IDao.ISmsCompte;
import IServiceSupport.IServicePersonneMoral;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServicePersonneMoraleImpl implements IServicePersonneMoral {

    private IPersonneMorale iPersonneMorale;
    private IPersonne iPersonne;
    private ICompte iCompte;
    private IOperation iOperation;
    private ISmsCompte iSmsCompte;

    @Override
    public void save(PersonneMorale u, Long idp) throws DataAccessException {
        Personne p = iPersonne.findByIdP(idp);
        u.setIdPersonne(p);
        iPersonneMorale.create(u);
    }

    @Override
    public void delete(Long id) throws DataAccessException {
        PersonneMorale a = iPersonneMorale.findByIdP(id);
        iPersonneMorale.delete(a);
    }

    @Override
    public void update(PersonneMorale u) throws DataAccessException {
        iPersonneMorale.update(u);
    }

    @Override
    public List<PersonneMorale> findAll() throws DataAccessException {
        return iPersonneMorale.findAll();
    }

    @Override
    public PersonneMorale findById(Long id) throws DataAccessException {
        return iPersonneMorale.findByIdP(id);
    }

    @Override
    public PersonneMorale findByNomEntreprise(String nom) throws DataAccessException {
        return iPersonneMorale.findByNomEntreprise(nom);
    }

    @Override
    public PersonneMorale findBySecteurActivite(String secteurActivite) throws DataAccessException {
        return  iPersonneMorale.findBySecteurActivite(secteurActivite);
    }

    @Override
    public PersonneMorale findByNumEnregistrement(String numEnregistrement) throws DataAccessException {
       return iPersonneMorale.findByNumEnregistrement(numEnregistrement);
    }

    @Override
    public PersonneMorale findByIdP(Long id) throws DataAccessException {
      return iPersonneMorale.findByIdP(id); 
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

    public IPersonneMorale getiPersonneMorale() {
        return iPersonneMorale;
    }

    public void setiPersonneMorale(IPersonneMorale iPersonneMorale) {
        this.iPersonneMorale = iPersonneMorale;
    }
}
