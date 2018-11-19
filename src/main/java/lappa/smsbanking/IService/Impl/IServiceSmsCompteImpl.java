/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.Entities.PersonnePhysique;
import lappa.smsbanking.Entities.SmsCompte;
import lappa.smsbanking.IDao.IPersonneMorale;
import lappa.smsbanking.IDao.IPersonnePhys;
import lappa.smsbanking.IDao.ISmsCompte;
import lappa.smsbanking.IService.IServiceSmsCompte;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceSmsCompteImpl implements IServiceSmsCompte {

    private ISmsCompte iSmsCompte;
    private IPersonneMorale iPersonneMorale;
    private IPersonnePhys iPersonnePhys;

    @Override
    public void save(SmsCompte s) throws DataAccessException {
        iSmsCompte.create(s);
    }

    @Override
    public void save(SmsCompte s, Long id1, Long id2) throws DataAccessException {
        PersonneMorale p1 = iPersonneMorale.findByIdP(id1);
        PersonnePhysique p2 = iPersonnePhys.findByIdP(id2);
        if (p1 != null) {
            s.setIdPersonneMoral(p1);
            iSmsCompte.create(s);
        }
        if (p2 != null) {
            s.setIdPersonnePhysique(p2);
            iSmsCompte.create(s);
        }
    }

    @Override
    public void delete(Long id) throws DataAccessException {
        SmsCompte s = iSmsCompte.findByIdS(id);
        iSmsCompte.delete(s);
    }

    @Override
    public void update(SmsCompte s) throws DataAccessException {
      iSmsCompte.update(s);
    }

    @Override
    public List<SmsCompte> findAll() throws DataAccessException {
     return iSmsCompte.findAll();
    }

    @Override
    public SmsCompte findByIdS(Long id) throws DataAccessException {
     return iSmsCompte.findByIdS(id);
    }

    @Override
    public SmsCompte findByMobile(String mobile) throws DataAccessException {
        return iSmsCompte.findByMobile(mobile);
    }

    @Override
    public SmsCompte findByTypeCompte(String typeCompte) throws DataAccessException {
       return iSmsCompte.findByTypeCompte(typeCompte);
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

    public IPersonnePhys getiPersonnePhys() {
        return iPersonnePhys;
    }

    public void setiPersonnePhys(IPersonnePhys iPersonnePhys) {
        this.iPersonnePhys = iPersonnePhys;
    }

    @Override
    public SmsCompte findByIdClient(String idClient) throws DataAccessException {
      return iSmsCompte.findByIdClient(idClient);
    }

    public SmsCompte findByPin(int pint) throws DataAccessException {
       return iSmsCompte.findByPin(pint);
    }
    
}
