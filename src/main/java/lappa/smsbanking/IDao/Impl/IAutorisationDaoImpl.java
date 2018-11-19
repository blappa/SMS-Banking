/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import lappa.smsbanking.IDao.IAutorisationDao;
import lappa.smsbanking.Entities.Autorisation;

/**
 *
 * @author lappa
 */
public class IAutorisationDaoImpl extends GenericDao<Autorisation, Long> implements IAutorisationDao{


    @Override
    public boolean Identification(String login) throws DataAccessException {
       Autorisation auth = (Autorisation)getManager().createNamedQuery("Autorisation.findByLogin").
               setParameter("Login", login).getSingleResult();
       if(auth==null){
       return false;
       }
       else {
            return true;
        }
    }

    @Override
    public Autorisation findByLogin(String login) throws DataAccessException {
       return (Autorisation)getManager().createNamedQuery("Autorisation.findByLogin").
               setParameter("Login", login).getSingleResult();
    }
    
    @Override
    public Autorisation findByPwd(String pwd) throws DataAccessException{
    return (Autorisation)getManager().createNamedQuery("Autorisation.findByPwd").
               setParameter("pwd", pwd).getSingleResult();
    }
    
}
