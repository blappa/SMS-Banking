/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Autorisation;

/**
 *
 * @author lappa
 */
public interface IAutorisationDao extends IDao<Autorisation, Long> {

    public boolean Identification(String Login) throws DataAccessException;

    public Autorisation findByLogin(String login) throws DataAccessException;
    
     public Autorisation findByPwd(String pwd) throws DataAccessException;
}
