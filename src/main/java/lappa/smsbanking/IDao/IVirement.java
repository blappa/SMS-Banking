/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Virement;

/**
 *
 * @author lappa
 */
public interface IVirement extends IDao<Virement, Long> {
    
     public Virement findByNumCompte(String nom) throws DataAccessException;
     
     public Virement findByCodeDocument(String code) throws DataAccessException;
             
      public Virement findByCodeFichier(String codeF) throws DataAccessException;
      
     public Virement findByIdV(Long id) throws DataAccessException;
     
     public Virement findByTypeDocument(String type) throws DataAccessException ;
}
