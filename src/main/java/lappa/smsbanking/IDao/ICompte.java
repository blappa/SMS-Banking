/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Compte;

/**
 *
 * @author lappa
 */
public interface ICompte extends IDao<Compte, Long>{
   
     public Compte findByIdC(Long id) throws DataAccessException;
    
     public Compte findByNumeroCompte(String numeroCompte) throws DataAccessException;
    
}
