/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Agence;

/**
 *
 * @author lappa
 */
public interface IAgence extends IDao<Agence, Long>{
   
     public Agence findByIdA(Long id) throws DataAccessException;
    
     public Agence findByNomAgence(String nomAgence) throws DataAccessException;
    
     public Agence findByCodeAgence(String codeAgence) throws DataAccessException;
}
