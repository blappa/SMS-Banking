/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Operation;
import lappa.smsbanking.Entities.PersonneMorale;
import lappa.smsbanking.Entities.PersonnePhysique;
import lappa.smsbanking.Entities.Sms;
import lappa.smsbanking.IDao.IOperation;
import lappa.smsbanking.IDao.ISms;
import lappa.smsbanking.IDao.IPersonne;
import lappa.smsbanking.IDao.IPersonneMorale;
import lappa.smsbanking.IDao.IPersonnePhys;
import lappa.smsbanking.Entities.Support;
import lappa.smsbanking.IDao.ISupport;
import lappa.smsbanking.IService.IServiceSms;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceSmsImpl implements IServiceSms {

    private ISms iSms;
    private IPersonne iPersonne;
    private IOperation iOperation;
    private IPersonneMorale iPersonneMorale;
    private IPersonnePhys iPersonnePhys;
    private ISupport iSupport;

    @Override
    public void save(Sms s) throws DataAccessException {
        iSms.create(s);
    }

    @Override
    public void save(Sms s, Long idu, Long ido) throws DataAccessException {
        PersonneMorale p1 = iPersonneMorale.findByIdP(idu);
        PersonnePhysique p2 = iPersonnePhys.findByIdP(idu);
        if (p1 != null) {
            Operation o = iOperation.findByIdO(ido);
            s.setIdPersonneMoral(p1);
            iOperation.create(o);
        }
        if (p2 != null) {
            Operation o = iOperation.findByIdO(ido);
            s.setIdPersonnePhysique(p2);
            iOperation.create(o);
        }
        iSms.create(s);
    }

    public void saveSupport(Support s) throws DataAccessException {
        iSupport.create(s);
    }
    
    @Override
    public void delete(Long id) throws DataAccessException {
        Sms s = iSms.findByIdC(id);
        iSms.delete(s);
    }

    @Override
    public void update(Sms s) throws DataAccessException {
       iSms.update(s);
    }
    
    @Override
    public void updateSupport(Support s) throws DataAccessException {
       iSupport.update(s);
    }

    @Override
    public List<Sms> findAll() throws DataAccessException {
        return  iSms.findAll();
    }

    @Override
    public List<Support> findAllS() throws DataAccessException {
        return  iSupport.findAll();
    }
    
    @Override
    public Sms findByIdS(Long id) throws DataAccessException {
      return iSms.findById(id);
    }
    
     @Override
    public Support findByIdSup(Long id) throws DataAccessException {
      return iSupport.findById(id);
    }

    public ISms getiSms() {
        return iSms;
    }

    public void setiSms(ISms iSms) {
        this.iSms = iSms;
    }

    public IPersonne getiPersonne() {
        return iPersonne;
    }

    public void setiPersonne(IPersonne iPersonne) {
        this.iPersonne = iPersonne;
    }

    public IOperation getiOperation() {
        return iOperation;
    }

    public void setiOperation(IOperation iOperation) {
        this.iOperation = iOperation;
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

    public ISupport getiSupport() {
        return iSupport;
    }

    public void setiSupport(ISupport iSupport) {
        this.iSupport = iSupport;
    }
    
    
}
