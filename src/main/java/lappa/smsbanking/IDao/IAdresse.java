/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Adresse;

/**
 *
 * @author lappa
 */
public interface IAdresse extends IDao<Adresse, Long>{
   
     public Adresse findByIdA(Long id) throws DataAccessException;
    
     public Adresse findByTypeAdresse(String typeAdresse) throws DataAccessException;
    
}
