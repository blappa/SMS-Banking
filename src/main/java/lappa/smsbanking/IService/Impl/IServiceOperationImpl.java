/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Compte;
import lappa.smsbanking.Entities.Operation;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.Entities.PersonnePhysique;
import lappa.smsbanking.IDao.ICompte;
import lappa.smsbanking.IDao.IOperation;
import lappa.smsbanking.IDao.IPersonneMorale;
import lappa.smsbanking.IDao.IPersonnePhys;
import IServiceSupport.IServiceOperation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceOperationImpl implements IServiceOperation {

    private IOperation iOperation;
    private ICompte iCompte;
    private IPersonneMorale iPersonneMorale;
    private IPersonnePhys iPersonnePhys;

    @Override
    public void save(Operation o) throws DataAccessException {
        iOperation.create(o);
    }

    @Override
    public void save(Operation o, Long id1, Long id2, Long idu) throws DataAccessException {
        Compte c1 = iCompte.findByIdC(id1);
        Compte c2 = iCompte.findByIdC(id2);
        PersonneMorale p1 = iPersonneMorale.findByIdP(id1);
        PersonnePhysique p2 = iPersonnePhys.findByIdP(id2);
        if (p1 != null) {
            o.setCpteSrc(c1);
            o.setCpteSrc(c2);
            o.setIdPersonneMoral(p1);
            iOperation.create(o);
        }
        if (p2 != null) {
            o.setCpteSrc(c1);
            o.setCpteSrc(c2);
            o.setIdPersonnePhysique(p2);
            iOperation.create(o);
        }
    }

    @Override
    public void delete(Long id) throws DataAccessException {
      Operation o=iOperation.findByIdO(id);
      iOperation.delete(o);
    }

    @Override
    public void update(Operation o) throws DataAccessException {
     iOperation.update(o);
    }

    @Override
    public List<Operation> findAll() throws DataAccessException {
     return iOperation.findAll();
    }

    @Override
    public Operation findById(Long id) throws DataAccessException {
      return iOperation.findByIdO(id);
    }

    @Override
    public Operation findByNomOperation(String nomOperation) throws DataAccessException {
      return iOperation.findByNomOperation(nomOperation);
    }

    public IOperation getiOperation() {
        return iOperation;
    }

    public void setiOperation(IOperation iOperation) {
        this.iOperation = iOperation;
    }

    public ICompte getiCompte() {
        return iCompte;
    }

    public void setiCompte(ICompte iCompte) {
        this.iCompte = iCompte;
    }

    public IPersonneMorale getiPersonneMorale() {
        return iPersonneMorale;
    }

    public void setiPersonneMorale(IPersonneMorale iPersonneMorale) {
        this.iPersonneMorale = iPersonneMorale;
    }

    public IPersonnePhys getiPersonnePhys() {
        return iPersonnePhys;
    }

    public void setiPersonnePhys(IPersonnePhys iPersonnePhys) {
        this.iPersonnePhys = iPersonnePhys;
    }

    
}
