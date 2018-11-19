/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.Emploi;

/**
 *
 * @author lappa
 */
public interface IEmploi extends IDao<Emploi, Long>{
   
     public Emploi findByIdE(Long id) throws DataAccessException;
    
     public Emploi findByTypeEmploi(String typeEmploi) throws DataAccessException;
     
     public Emploi findByProfession(String profession) throws DataAccessException;
    
}
