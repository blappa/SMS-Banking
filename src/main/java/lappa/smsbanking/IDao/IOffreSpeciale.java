/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lappa.smsbanking.IDao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import lappa.smsbanking.Entities.OffreSpeciale;

/**
 *
 * @author lappa
 */
public interface IOffreSpeciale extends IDao<OffreSpeciale, Long>{
   
     public OffreSpeciale findByIdO(Long id) throws DataAccessException;
     
}
