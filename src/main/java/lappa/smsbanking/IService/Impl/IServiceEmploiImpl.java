/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import java.util.List;
import lappa.smsbanking.Entities.Emploi;
import lappa.smsbanking.Entities.Personne;
import lappa.smsbanking.IDao.IEmploi;
import lappa.smsbanking.IDao.IPersonne;
import IServiceSupport.IServiceEmploi;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceEmploiImpl implements IServiceEmploi {
    
    private IEmploi iEmploi;
    private IPersonne iPersonne;

    @Override
    public Emploi findByIdE(Long id) throws DataAccessException {
     return iEmploi.findByIdE(id);
    }

    @Override
    public Emploi findByTypeEmploi(String typeEmploi) throws DataAccessException {
        return iEmploi.findByTypeEmploi(typeEmploi);
    }

    @Override
    public Emploi findByProfession(String profession) throws DataAccessException {
      return iEmploi.findByProfession(profession);
    }

    @Override
    public void save(Emploi c, Long idu) throws DataAccessException {
        Personne p = iPersonne.findByIdP(idu);
        c.setIdPersonne(p);
        iEmploi.create(c);
    }

    @Override
    public void delete(Long id) throws DataAccessException {
       Emploi e=iEmploi.findById(id);
       iEmploi.delete(e);
    }

    @Override
    public void update(Emploi c) throws DataAccessException {
       iEmploi.update(c);
    }

    @Override
    public List<Emploi> findAll() throws DataAccessException {
       return iEmploi.findAll();
    }

    @Override
    public Emploi findById(Long id) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IEmploi getiEmploi() {
        return iEmploi;
    }

    public void setiEmploi(IEmploi iEmploi) {
        this.iEmploi = iEmploi;
    }

    public IPersonne getiPersonne() {
        return iPersonne;
    }

    public void setiPersonne(IPersonne iPersonne) {
        this.iPersonne = iPersonne;
    }

    
}