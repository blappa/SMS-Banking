/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IService.Impl;

import com.douwe.generic.dao.DataAccessException;
import lappa.smsbanking.IDao.IPersonne;
import lappa.smsbanking.Entities.Autorisation;
import lappa.smsbanking.Entities.Personne;
import IServiceSupport.IServiceAutorisation;
import java.util.List;
import lappa.smsbanking.IDao.IAutorisationDao;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class IServiceAutorisationImpl implements IServiceAutorisation {
    
    private IPersonne personneDao;
    private IAutorisationDao autorisationDao;
    private IAutorisationDao loginDao;

    @Override
        public Personne enregistrerNouveauPersonnel(Personne p) throws DataAccessException {
                Personne pp = personneDao.findById(p.getId());
                if(pp!=null) {
                    return (Personne) personneDao.update(pp);
                }
                else {
                    return (Personne) personneDao.create(p);
                }
            } 
    
    
   
    @Override
    public void enregistrerNouveauLogin(Autorisation p) throws DataAccessException{
       autorisationDao.create(p);
     }
     

    @Override
    public void annulerEnregistrementPersonnel(Personne p) throws DataAccessException {
        Personne pp = personneDao.findById(p.getId());
        personneDao.delete(pp);
    }

    @Override
    public boolean identifierLogin(String login) throws DataAccessException {
        return autorisationDao.Identification(login);
    }

   

    @Override
    public List<Personne> getAllPersonnel() throws DataAccessException {
        return personneDao.findAll();
    }

    public IPersonne getPersonneDao() {
        return personneDao;
    }

    public void setPersonneDao(IPersonne personneDao) {
        this.personneDao = personneDao;
    }


    @Override
    public Personne getPersonnelById(Long id) throws DataAccessException {
        return personneDao.findById(id);
    }

    @Override
    public void annulerEnregistrementLogin(Autorisation p) throws DataAccessException {
        Autorisation pp = loginDao.findById(p.getIdLogin());
        loginDao.delete(pp);
    }

   
    @Override
    public Autorisation getAutorisationByPwd(String pwd) throws DataAccessException{
    return loginDao.findByPwd(pwd);
    }

    @Override
    public Autorisation update(Autorisation p) throws DataAccessException {
        return autorisationDao.update(p);
    }
  
        
    public IAutorisationDao getAutorisationDao() {
        return autorisationDao;
    }

    public void setAutorisationDao(IAutorisationDao autorisationDao) {
        this.autorisationDao = autorisationDao;
    }

    public Autorisation findByLogin(String login) throws DataAccessException {
        return autorisationDao.findByLogin(login);
    }

    public Autorisation findById(Integer id) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

     @Override
    public Autorisation getAutorisationByLogin(String login) throws DataAccessException {
        return autorisationDao.findByLogin(login);
    }
     
    public List<Autorisation> findAll() throws DataAccessException {
       return autorisationDao.findAll();
    }

    public Autorisation create(Autorisation t) throws DataAccessException {
        return autorisationDao.create(t);
    }

    public void delete(Autorisation t) throws DataAccessException {
        autorisationDao.delete(t);
    }

    

    public IAutorisationDao getLoginDao() {
        return loginDao;
    }

    public void setLoginDao(IAutorisationDao loginDao) {
        this.loginDao = loginDao;
    }


}
